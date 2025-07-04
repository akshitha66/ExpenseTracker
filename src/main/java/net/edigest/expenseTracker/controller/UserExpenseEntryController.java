package net.edigest.expenseTracker.controller;
import net.edigest.expenseTracker.entity.ExpenseEntry;
import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.service.ExpenseEntryService;
import net.edigest.expenseTracker.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expense")
public class UserExpenseEntryController {

    @Autowired
    private ExpenseEntryService expenseEntryService;

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@PostMapping()
    public ResponseEntity<ExpenseEntry> createEntry(@RequestBody ExpenseEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            expenseEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    @PutMapping()
    public ResponseEntity<?> updateUserEntryById(@RequestBody UserEntry myUserEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userInDB = userEntryService.findByUserName(userName);

        userInDB.setUserName(myUserEntry.getUserName());

        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(myUserEntry.getPassword());
        userInDB.setPassword(encodedPassword);

        userEntryService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> getAllExpenseEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("[GET /expense] Authenticated user: " + userName);
        UserEntry user = userEntryService.findByUserName(userName);
        System.out.println("[GET /expense] User loaded: " + (user != null ? user.getUserName() : "null"));
        List<ExpenseEntry> all = user.getExpenseEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<ExpenseEntry> getExpenseEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userEntryService.findByUserName(userName);
        List<ExpenseEntry> collect = user.getExpenseEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<ExpenseEntry> expenseEntry = expenseEntryService.findById(myId);
            if(expenseEntry.isPresent()){
                return new ResponseEntity<>(expenseEntry.get(), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteExpenseEntryById(@PathVariable ObjectId  myId, @PathVariable String userName) {
        expenseEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public  ResponseEntity<ExpenseEntry> updateExpenseEntryById(@PathVariable ObjectId myId, @RequestBody ExpenseEntry updatedEntry, @PathVariable String userName){
        ExpenseEntry oldEntry = expenseEntryService.findById(myId).orElse(null);
        // condition ? valueIfTrue : valueIfFalse
        //"Update the title only if the new title is valid, otherwise keep the existing one."
        if(oldEntry != null){
            oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setAmount(
                    updatedEntry.getAmount() > 0
                            ? updatedEntry.getAmount()
                            : oldEntry.getAmount()
            );
            oldEntry.setReason(updatedEntry.getReason() != null && !updatedEntry.getReason().equals("") ? updatedEntry.getReason() : oldEntry.getReason());
            expenseEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

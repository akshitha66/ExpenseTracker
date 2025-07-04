/*package net.edigest.expenseTracker.controller;

import net.edigest.expenseTracker.entity.ExpenseEntry;
import net.edigest.expenseTracker.service.ExpenseEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/_expense")
public class ExpenseEntryControllerV2 {

    @Autowired
    private ExpenseEntryService expenseEntryService;

    @PostMapping("/add")
    public ResponseEntity<ExpenseEntry> createEntry(@RequestBody ExpenseEntry myEntry){
        try{
            myEntry.setDate(LocalDateTime.now());
            expenseEntryService.saveEntry(myEntry, user);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<ExpenseEntry> all = expenseEntryService.getAll();
        if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(all, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<ExpenseEntry> getExpenseEntryById(@PathVariable ObjectId myId){
        Optional<ExpenseEntry> expenseEntry = expenseEntryService.findById(myId);
        if(expenseEntry.isPresent()){
            return new ResponseEntity<>(expenseEntry.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteExpenseEntryById(@PathVariable ObjectId  myId) {
        expenseEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public  ResponseEntity<ExpenseEntry> updateExpenseEntryById(@PathVariable ObjectId myId, @RequestBody ExpenseEntry updatedEntry){
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
            expenseEntryService.saveEntry(oldEntry, user);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}*/

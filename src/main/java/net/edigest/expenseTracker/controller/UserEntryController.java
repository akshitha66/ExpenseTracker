package net.edigest.expenseTracker.controller;
import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.repository.UserEntryRepo;
import net.edigest.expenseTracker.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private UserEntryRepo userEntryRepo;

    @GetMapping
    public List<UserEntry> getAll() {
        return userEntryService.getAll();
    }

    @PutMapping()
    public ResponseEntity<?> updateUserEntryById(@RequestBody UserEntry myUserEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userInDB = userEntryService.findByUserName(userName);

        // Update username if provided and different
        if (myUserEntry.getUserName() != null && !myUserEntry.getUserName().isEmpty()) {
            userInDB.setUserName(myUserEntry.getUserName());
        }

        // Update password only if provided and plaintext (not already encoded)
        String newPassword = myUserEntry.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.startsWith("$2a$")) {  // crude check for bcrypt hash prefix
                newPassword = userEntryService.encodePassword(newPassword); // new method to encode password
            }
            userInDB.setPassword(newPassword);
        }

        // Save user without encoding again
        userEntryService.saveUser(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*@PutMapping()
    public ResponseEntity<?> updateUserEntryById(@RequestBody UserEntry myUserEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userInDB = userEntryService.findByUserName(userName);
            userInDB.setUserName(myUserEntry.getUserName());
            userInDB.setPassword((myUserEntry.getPassword()));
            userEntryService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntryRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
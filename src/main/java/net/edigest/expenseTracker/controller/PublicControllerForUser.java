package net.edigest.expenseTracker.controller;

import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicControllerForUser {

    @Autowired
    private UserEntryService userEntryService;

    @PostMapping("/create-user")
    public void createUserEntry(@RequestBody UserEntry myUserEntry) {
        userEntryService.saveNewUser(myUserEntry);
    }
}

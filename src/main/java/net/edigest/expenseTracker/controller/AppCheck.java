package net.edigest.expenseTracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppCheck {
    @GetMapping("/App-check")
    public String checkWorking(){
        return "OK";
    }
}

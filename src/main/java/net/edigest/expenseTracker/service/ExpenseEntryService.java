package net.edigest.expenseTracker.service;

import net.edigest.expenseTracker.entity.ExpenseEntry;
import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.repository.ExpenseEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ExpenseEntryService {

    @Autowired
    private ExpenseEntryRepo expenseEntryRepo;

    @Autowired
    private UserEntryService userEntryService;

    /*@Transactional
    public void saveEntry(ExpenseEntry expenseEntry, String userName){
        try{
            UserEntry user = userEntryService.findByUserName(userName);
            expenseEntry.setDate(LocalDateTime.now());
            ExpenseEntry saved = expenseEntryRepo.save(expenseEntry);
            user.getExpenseEntries().add(saved);
            user.setUserName(null);
            userEntryService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the Entry", e);
        }
    }*/

    @Transactional
    public void saveEntry(ExpenseEntry expenseEntry, String userName){
        try{
            UserEntry user = userEntryService.findByUserName(userName);
            System.out.println("[saveEntry] Found user: " + (user != null ? user.getUserName() : "null"));
            if (user == null) {
                throw new RuntimeException("User not found with username: " + userName);
            }
            expenseEntry.setDate(LocalDateTime.now());
            ExpenseEntry saved = expenseEntryRepo.save(expenseEntry);
            System.out.println("[saveEntry] Expense saved with id: " + saved.getId());
            user.getExpenseEntries().add(saved);
            // Do NOT set userName to null!
            // user.setUserName(null);  <-- REMOVE THIS
            userEntryService.saveNewUser(user);
            System.out.println("[saveEntry] Expense linked to user and user saved.");
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the Entry", e);
        }
    }


    public void saveEntry(ExpenseEntry expenseEntry){
        expenseEntryRepo.save(expenseEntry);
    }


    public List<ExpenseEntry> getAll(){
        return expenseEntryRepo.findAll();
    }

    public Optional<ExpenseEntry> findById(ObjectId id){
        return expenseEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        UserEntry user = userEntryService.findByUserName(userName);
        user.getExpenseEntries().removeIf(x -> x.getId().equals(id));
        userEntryService.saveNewUser(user);
        expenseEntryRepo.deleteById(id);
    }
}

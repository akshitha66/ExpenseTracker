/*package net.edigest.expenseTracker.controller;
import net.edigest.expenseTracker.entity.ExpenseEntry;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/_expense")
public class ExpenseEntryController {
    private Map<Long, ExpenseEntry> expenseEntries = new HashMap<>();

    @GetMapping()
    public List<ExpenseEntry> getAll(){
       return new ArrayList<>(expenseEntries.values());
    }

    @PostMapping()
    public boolean createEntry(@RequestBody ExpenseEntry myEntry){
        expenseEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public ExpenseEntry getExpenseEntryById(@PathVariable Long myId){
        return expenseEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public ExpenseEntry deleteExpenseEntryById(@PathVariable Long myId){
        return expenseEntries.remove(myId);
    }

    @PutMapping("/id/{myId}")
    public ExpenseEntry updateExpenseEntryById(@PathVariable Long myId, @RequestBody ExpenseEntry myEntry){
        return expenseEntries.put(myId, myEntry);
    }
}*/

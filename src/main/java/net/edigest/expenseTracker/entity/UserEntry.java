package net.edigest.expenseTracker.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "user_entries")
public class UserEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
   // @NonNull
    private String userName;
    //@NonNull
    private String password;
    @DBRef
    private List<ExpenseEntry> expenseEntries = new ArrayList<>();
    private List<String> roles;
}

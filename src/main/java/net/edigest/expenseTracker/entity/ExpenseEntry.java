package net.edigest.expenseTracker.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "expense_entries")
//@Getter
//@Setter
// you can also use @Data which replaces @Getter, @Setter, @Equals etc
@Data
@NoArgsConstructor
public class ExpenseEntry {
    @Id
    private ObjectId id;
    private String title;
    private long amount;
    private String reason;
    private LocalDateTime date;
}

package net.edigest.expenseTracker.repository;

import net.edigest.expenseTracker.entity.ExpenseEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseEntryRepo extends MongoRepository<ExpenseEntry, ObjectId> {

}

// Controller --> Service --> Repository
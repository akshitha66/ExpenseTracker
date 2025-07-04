package net.edigest.expenseTracker.repository;

import net.edigest.expenseTracker.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String userName);

    void deleteByUserName(String userName);
}

// Controller --> Service --> Repository
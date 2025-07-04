package net.edigest.expenseTracker.service;

import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USERS"));
        userEntryRepo.save(userEntry);
    }

   public void saveUser(UserEntry userEntry) {
       userEntryRepo.save(userEntry);
   }

    public List<UserEntry> getAll(){
        return userEntryRepo.findAll();
    }

    public Optional<UserEntry> findById(ObjectId id){
        return userEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userEntryRepo.deleteById(id);
    }

    public UserEntry findByUserName(String userName){
        UserEntry user = userEntryRepo.findByUserName(userName);
        System.out.println("[UserEntryService] findByUserName called with: " + userName + ", found user: " + (user != null ? user.getUserName() : "null"));
        return user;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}

package net.edigest.expenseTracker.service;

import net.edigest.expenseTracker.entity.UserEntry;
import net.edigest.expenseTracker.repository.UserEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[UserDetailsService] Loading user: " + username);   // <-- Add this line
        UserEntry user = userEntryRepo.findByUserName(username);
        if (user != null) {
            System.out.println("[UserDetailsService] Found user: " + user.getUserName());  // <-- Add this line
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles()
                            .toArray(new String[0])).build();
        }
        System.out.println("[UserDetailsService] User not found: " + username);  // <-- Add this line
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry user = userEntryRepo.findByUserName(username);
        if(user != null){
            return  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles()
                            .toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}*/

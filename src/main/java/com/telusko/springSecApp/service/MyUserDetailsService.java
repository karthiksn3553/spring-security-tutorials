package com.telusko.springSecApp.service;

import com.telusko.springSecApp.model.UserPrincipal;
import com.telusko.springSecApp.model.Users;
import com.telusko.springSecApp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
//CLERK
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);
        /*
        executes a SQL query on PostgreSQL: SELECT * FROM users WHERE username = 'karthik'.
        It returns a Users object containing { id: 1, username: "karthik", password: "123" }
        */
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}

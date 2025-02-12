package com.jvm.Week10.Service;

import com.jvm.Week10.Entity.User;
import com.jvm.Week10.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByName(username);
        if (user !=null)
        return new UserPrincipal(user);
        throw new UsernameNotFoundException("User not found with the username "+username);
    }
}

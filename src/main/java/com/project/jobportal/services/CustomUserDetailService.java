package com.project.jobportal.services;

import com.project.jobportal.entity.Users;
import com.project.jobportal.repository.IUsersRepo;
import com.project.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final IUsersRepo usersRepo;

    @Autowired
    public CustomUserDetailService(IUsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("could not find user"));
        return new CustomUserDetails(user);
    }
}

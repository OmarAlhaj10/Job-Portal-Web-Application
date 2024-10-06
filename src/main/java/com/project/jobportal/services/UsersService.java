package com.project.jobportal.services;

import com.project.jobportal.entity.JopSeekerProfile;
import com.project.jobportal.entity.RectuiterProfile;
import com.project.jobportal.entity.Users;
import com.project.jobportal.repository.IJopSeekerProfileRepo;
import com.project.jobportal.repository.IRecruiterProfileRepo;
import com.project.jobportal.repository.IUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
    private final IUsersRepo usersRepo;
    private final IJopSeekerProfileRepo jopSeekerProfileRepo;
    private final IRecruiterProfileRepo recruiterProfileRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(IUsersRepo usersRepo, IRecruiterProfileRepo recruiterProfileRepo, IJopSeekerProfileRepo jopSeekerProfileRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.recruiterProfileRepo = recruiterProfileRepo;
        this.jopSeekerProfileRepo = jopSeekerProfileRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addUser(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = usersRepo.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if (userTypeId == 1){
            recruiterProfileRepo.save(new RectuiterProfile(savedUser));
        }else {
            jopSeekerProfileRepo.save(new JopSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<Users> getUserBuEmail(String email){
        return usersRepo.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RectuiterProfile rectuiterProfile = recruiterProfileRepo.findById(userId).orElse(new RectuiterProfile());
                return rectuiterProfile;
            }else {
                JopSeekerProfile jopSeekerProfile = jopSeekerProfileRepo.findById(userId).orElse(new JopSeekerProfile());
                return jopSeekerProfile;
            }
        }
        return null;
    }

    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            return users;
        }
        return null;
    }

    public Users findByEmail(String currentUsername) {
        return usersRepo.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not " + "found"));
    }
}

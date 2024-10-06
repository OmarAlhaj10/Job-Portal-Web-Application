package com.project.jobportal.services;

import com.project.jobportal.entity.RectuiterProfile;
import com.project.jobportal.entity.Users;
import com.project.jobportal.repository.IRecruiterProfileRepo;
import com.project.jobportal.repository.IUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final IRecruiterProfileRepo recruiterProfileRepo;
    private final IUsersRepo usersRepo;

    @Autowired
    public RecruiterProfileService(IRecruiterProfileRepo recruiterProfileRepo, IUsersRepo usersRepo) {
        this.recruiterProfileRepo = recruiterProfileRepo;
        this.usersRepo = usersRepo;
    }

    public Optional<RectuiterProfile> getOne(Integer id) {
        return recruiterProfileRepo.findById(id);
    }

    public RectuiterProfile addNew(RectuiterProfile rectuiterProfile) {
        return recruiterProfileRepo.save(rectuiterProfile);
    }

    public RectuiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepo.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not " + "found"));
            Optional<RectuiterProfile> rectuiterProfile = getOne(users.getUserId());
            return rectuiterProfile.orElse(null);
        }else return null;
    }
}

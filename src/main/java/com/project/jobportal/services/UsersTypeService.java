package com.project.jobportal.services;

import com.project.jobportal.entity.UsersType;
import com.project.jobportal.repository.IUsersTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final IUsersTypeRepo usersTypeRepo;

    @Autowired
    public UsersTypeService(IUsersTypeRepo usersTypeRepo) {
        this.usersTypeRepo = usersTypeRepo;
    }

    public List<UsersType> getAllTypes(){
        return usersTypeRepo.findAll();
    }
}

package com.shylesh.jobportal.services;

import com.shylesh.jobportal.entity.UsersType;
import com.shylesh.jobportal.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository theUsersTypeRepository) {
         usersTypeRepository = theUsersTypeRepository;
    }

    public List<UsersType> getAll() {
        return usersTypeRepository.findAll();
    }

}

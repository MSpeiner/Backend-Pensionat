package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.UserEditDTO;
import com.example.backendpensionat.Models.Role;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Repos.RoleRepo;
import com.example.backendpensionat.Repos.UserRepo;
import com.example.backendpensionat.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public Iterable<User> listAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserById(UUID id){
        return userRepo.findById(id);
    }

    @Override
    public Set<Role> listAllRoles(){
        return new HashSet<>(roleRepo.findAll());
    }

    @Override
    public Set<Role> findAllById(List<UUID> listOfIDs) {
        return new HashSet<>(roleRepo.findAllById(listOfIDs));
    }

    @Transactional
    @Override
    public void changeUser(UserEditDTO user) {
        Optional<User> userFromDB = userRepo.findById(user.getId());
        Set<Role> roles = findAllById(user.getRoleIds());

        if(userFromDB.isPresent()){
            userFromDB.get().setUsername(user.getUsername());
            userFromDB.get().setPassword(user.getPassword() == null ? userFromDB.get().getPassword() : user.getPassword());
            userFromDB.get().getRoles().addAll(roles);
            userRepo.save(userFromDB.get());
        }
    }
}

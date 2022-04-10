package com.example.pki.service;

import com.example.pki.model.Role;
import com.example.pki.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


}

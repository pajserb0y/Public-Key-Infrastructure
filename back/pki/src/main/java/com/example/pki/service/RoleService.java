package com.example.pki.service;

import com.example.pki.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);
    Role findByName(String name);
}

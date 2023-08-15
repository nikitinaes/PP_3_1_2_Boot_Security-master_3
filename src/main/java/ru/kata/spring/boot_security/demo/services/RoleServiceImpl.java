package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

   private RoleRepository roleRepository;

   @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {//
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role addRole (Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public List<Role> findByIdRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public List<Role> getRolesById(List<Integer> ids) {
       return roleRepository.findByIdIn(ids);
    }

    public List<Role> getAllRoles() {
       return roleRepository.findAll();
    }
}

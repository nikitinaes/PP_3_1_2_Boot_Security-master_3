package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {

    Role addRole (Role role);
    List<Role> findByIdRoles();
    List<Role> getRolesById(List<Integer> ids);
    public List<Role> getAllRoles();

}

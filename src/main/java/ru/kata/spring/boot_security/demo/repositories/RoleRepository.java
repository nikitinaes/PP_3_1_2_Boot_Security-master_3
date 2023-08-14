package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository <Role, Integer> {
    List<Role> getAllById(int id);

    List<Role> findByIdIn(List<Integer> ids);

}

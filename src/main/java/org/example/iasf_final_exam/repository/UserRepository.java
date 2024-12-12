package org.example.iasf_final_exam.repository;

import org.example.iasf_final_exam.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void deleteById(Long id);

    List<User> searchByName(String name); // Add search method
    User findByUsername(String username);
}

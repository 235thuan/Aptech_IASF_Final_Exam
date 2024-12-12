package org.example.iasf_final_exam.service;

import jakarta.transaction.Transactional;
import org.example.iasf_final_exam.model.User;
import org.example.iasf_final_exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> searchUsersByName(String name) {
        return userRepository.searchByName(name); // Add search logic
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null; // Check if user exists
    }
}

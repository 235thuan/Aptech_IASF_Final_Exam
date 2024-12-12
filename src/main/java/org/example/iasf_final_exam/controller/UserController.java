package org.example.iasf_final_exam.controller;


import org.example.iasf_final_exam.model.User;
import org.example.iasf_final_exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;  // For @Valid
import org.springframework.web.bind.annotation.ModelAttribute;  // For @ModelAttribute


import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    // Default route (list + create/edit form)
    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers()); // List of users
        model.addAttribute("user", new User()); // Empty user object for the form
        return "index"; // Combined view
    }

    // Save user (create or update)
    @PostMapping
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (user.getId() == null && userService.isUsernameTaken(user.getName())) {
            result.rejectValue("name", "error.name", "Username is already taken");
        }

        if (result.hasErrors()) {
            // Return the form with errors and the list
            model.addAttribute("users", userService.getAllUsers());
            return "index";
        }

        userService.saveUser(user); // Save user (create or update)
        return "redirect:/"; // Redirect to avoid duplicate submissions
    }

    // Edit user (populate the form with existing user data)
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user); // Populate form with existing user
        } else {
            model.addAttribute("user", new User()); // Fallback to empty form
        }
        model.addAttribute("users", userService.getAllUsers()); // Populate the list
        return "index"; // Combined view
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/"; // Redirect to refresh the list
    }

    // Search users
    @GetMapping("/search")
    public String searchUsers(@RequestParam String search, Model model) {
        model.addAttribute("users", userService.searchUsersByName(search)); // Filtered list
        model.addAttribute("user", new User()); // Empty form
        return "index"; // Combined view
    }
}

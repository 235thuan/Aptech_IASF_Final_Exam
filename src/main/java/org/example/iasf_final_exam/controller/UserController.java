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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (userService.isUsernameTaken(user.getName())) {
            result.rejectValue("name", "error.name", "Username is already taken");
            return "add"; // Return to the form with an error message
        }
        if (result.hasErrors()) {
            return "add";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "edit";
        }
        return "redirect:/users";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String search, Model model) {
        model.addAttribute("users", userService.searchUsersByName(search));
        return "list";
    }
}

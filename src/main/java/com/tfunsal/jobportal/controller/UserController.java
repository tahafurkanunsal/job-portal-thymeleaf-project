package com.tfunsal.jobportal.controller;

import com.tfunsal.jobportal.entity.User;
import com.tfunsal.jobportal.entity.UserType;
import com.tfunsal.jobportal.service.UserService;
import com.tfunsal.jobportal.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserTypeService userTypeService;
    private final UserService userService;


    @Autowired
    public UserController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user" , new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(User user){
        userService.create(user);
        System.out.println(user);
        return "dashboard";
    }
}

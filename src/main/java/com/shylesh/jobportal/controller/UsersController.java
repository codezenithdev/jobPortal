package com.shylesh.jobportal.controller;

import com.shylesh.jobportal.entity.Users;
import com.shylesh.jobportal.entity.UsersType;
import com.shylesh.jobportal.services.UsersService;
import com.shylesh.jobportal.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {


    private final UsersService usersService;
    private final UsersTypeService usersTypeService;

    @Autowired
    public UsersController(UsersTypeService theUsersTypeService, UsersService theUsersService) {
        usersTypeService = theUsersTypeService;
        usersService = theUsersService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersType = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersType);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users theUser, Model model) {

        Optional<Users> userByEmail = usersService.getUserByEmail(theUser.getEmail());

        if (userByEmail.isPresent()) {
            model.addAttribute("error", "This email address is already in use");
            List<UsersType> usersType = usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersType);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(theUser);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

}

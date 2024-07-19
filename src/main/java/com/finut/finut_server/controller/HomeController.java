package com.finut.finut_server.controller;

import com.finut.finut_server.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final HttpSession httpSession;

    public HomeController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    @GetMapping("/")
    public String home(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getName());
            model.addAttribute("email", user.getEmail());
        } else {
            System.out.println("User is null");
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

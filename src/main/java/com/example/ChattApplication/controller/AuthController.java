package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.service.GroupService;
import com.example.ChattApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Group;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final GroupService groupService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/web")
    public String web() {
        return "websocket";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam String email,
                               @RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        User user=userService.saveUser(email, username, password);

        if (user==null){
            return "redirect:/register?error=true"; // Redirect to register page with error parameter
        }

        model.addAttribute("groups",groupService.getChatGroups());

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String loginPost(HttpSession session,
                            @RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        User user = userService.findUser(username, password);
        if (user == null) {
            return "redirect:/login?error=true"; // Redirect to login page with error parameter
        }
        session.setAttribute("user", user);
        model.addAttribute("groups",groupService.getChatGroups());
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session,Model model) {
        if (session.getAttribute("user")==null){
            return "redirect:/login";
        }


        User currentUser=(User) session.getAttribute("user");
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("groups",groupService.getChatGroups());
        return "home";
    }

}

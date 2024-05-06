package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.payload.UserPayload;
import com.example.ChattApplication.service.MessageService;
import com.example.ChattApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, originPatterns = "*",origins = "*")
public class UserController {
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/user/{userId}")
    public String userDetails(HttpSession session, @PathVariable Long userId, Model model) {

        if (session.getAttribute("user")==null){
            return "redirect:/login";
        }

        User user = userService.getUserById(userId);
        User currentUser=(User) session.getAttribute("user");

        List<Message> messagesList=messageService.getMessagesBySenderAndReceiverId(currentUser.getId(), user.getId());

        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("messages", messagesList);
        model.addAttribute("id", user.getId());
        return "user-chat";
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getUser() {
        return userService.getUsers();
    }

}

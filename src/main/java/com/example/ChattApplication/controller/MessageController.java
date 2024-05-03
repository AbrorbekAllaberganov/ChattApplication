package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.payload.MessagePayload;
import com.example.ChattApplication.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    //    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/new-session")
    @SendTo("/topic/user-message")
    public Message sendMessages(Message message) {
        return message;
    }

    @PostMapping("/send-message")
    public String saveMessage(HttpSession session,
                              @RequestParam String messageContent,
                              @RequestParam Long recipientId,
                              Model model) {
        User currentUser = (User) session.getAttribute("user");
        Message message = messageService.saveMessage(messageContent, currentUser.getId(), recipientId);
        simpMessagingTemplate.convertAndSend("/topic/user-message", message);
        model.addAttribute("currentUser", currentUser);
        return "redirect:/user/" + recipientId;
    }
}

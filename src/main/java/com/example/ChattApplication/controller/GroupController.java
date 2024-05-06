package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.ChatGroup;
import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/group-session")
    @SendTo("/topic/group-message")
    public Message sendMessages(Message message) {
        return message;
    }


    @PostMapping("/createGroup")
    public String getGroupList(@RequestParam String groupName,
                               HttpSession session,
                               Model model) {
        User user=(User) session.getAttribute("user");
        groupService.saveChatGroup(groupName);


        List<ChatGroup> groups = groupService.getChatGroups();
        model.addAttribute("groups", groups);
        model.addAttribute("currentUser", user);

        return "redirect:/home?group=true";
    }

    @GetMapping("/group/{id}")
    public String getGroup(@PathVariable Long id,
                           HttpSession session,
                           Model model) {
        User user=(User) session.getAttribute("user");

        ChatGroup group = groupService.getChatGroupById(id);
        if(group == null)
            return "redirect:/home?group=true";

        model.addAttribute("group", group);
        model.addAttribute("messages", group.getMessages());
        model.addAttribute("user", user);

        return "group-chat";
    }

    @PostMapping("/group")
    public String sendMessage(@RequestParam String message,
                              @RequestParam Long groupId,
                              HttpSession session,
                              Model model) {

        User user=(User) session.getAttribute("user");
        ChatGroup group = groupService.getChatGroupById(groupId);
        if(group == null)
            return "redirect:/home?group=true";

        Message newMessage = groupService.saveMessage(message, group.getId(), user);
        simpMessagingTemplate.convertAndSend("/topic/group-message", newMessage);

        model.addAttribute("group", group);
        model.addAttribute("messages", group.getMessages());
        model.addAttribute("user", user);

        return "redirect:/group/"+group.getId();
    }



}

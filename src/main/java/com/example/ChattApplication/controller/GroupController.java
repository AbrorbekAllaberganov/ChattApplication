package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.ChatGroup;
import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/createGroup")
    public String getGroupList(@RequestParam String groupName,
                               HttpSession session,
                               Model model) {
        User user=(User) session.getAttribute("currentUser");
        groupService.saveChatGroup(groupName);


        List<ChatGroup> groups = groupService.getChatGroups();
        model.addAttribute("groups", groups);
        model.addAttribute("currentUser", user);

        return "redirect:/home?group=true";
    }

    @GetMapping("/group/{id}")
    public String getGroup(@RequestParam Long id,
                           HttpSession session,
                           Model model) {
        User user=(User) session.getAttribute("currentUser");
        ChatGroup group = groupService.getChatGroupById(id);
        if(group == null)
            return "redirect:/home?group=true";

        model.addAttribute("group", group);
        model.addAttribute("messages", group.getMessages());
        model.addAttribute("currentUser", user);

        return "group-chat";
    }



}

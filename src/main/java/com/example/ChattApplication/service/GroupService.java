package com.example.ChattApplication.service;

import com.example.ChattApplication.entity.ChatGroup;
import com.example.ChattApplication.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public ChatGroup saveChatGroup(String name){
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName(name);
        return groupRepository.save(chatGroup);
    }

    public List<ChatGroup> getChatGroups(){
        return groupRepository.findAll();
    }

    public ChatGroup getChatGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public boolean saveMessage(String message, Long groupId){
        ChatGroup chatGroup = groupRepository.findById(groupId).orElseThrow(null);

        return groupRepository.save(chatGroup)!= null;
    }
}

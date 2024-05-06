package com.example.ChattApplication.service;

import com.example.ChattApplication.entity.ChatGroup;
import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.entity.User;
import com.example.ChattApplication.repository.GroupRepository;
import com.example.ChattApplication.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MessageRepository messageRepository;

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

    public Message saveMessage(String message, Long groupId, User senderUser){
        ChatGroup chatGroup = groupRepository.findById(groupId).orElseThrow(null);
        List<Message> messageList=chatGroup.getMessages();

        Message message1 = new Message();
        message1.setContent(message);
        message1.setSender(senderUser);
        message1.setGroupId(groupId);

        messageRepository.save(message1);

        messageList.add(message1);
        chatGroup.setMessages(messageList);

        groupRepository.save(chatGroup);
        return message1;
    }
}

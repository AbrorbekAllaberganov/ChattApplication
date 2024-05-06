package com.example.ChattApplication.service;

import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.exceptions.ResourceNotFound;
import com.example.ChattApplication.payload.MessagePayload;
import com.example.ChattApplication.repository.MessageRepository;
import com.example.ChattApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Message saveMessage(String content, Long senderId, Long receiverId, Long groupId) {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setSender(userRepository.findById(senderId).orElseThrow(
                    () -> new ResourceNotFound("user", "id", senderId)
            ));
            if(receiverId != null)
                message.setReceiver(userRepository.findById(receiverId).orElseThrow(
                    () -> new ResourceNotFound("user", "id", receiverId)
                ));
            if(groupId != null)
                message.setGroupId(groupId);
            return messageRepository.save(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteById(Long id){
        try {
            messageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public List<Message> getMessagesBySenderAndReceiverId(Long senderId, Long receiverId){
        try {
            return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

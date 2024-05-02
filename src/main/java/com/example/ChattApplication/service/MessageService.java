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

    public Message saveMessage(MessagePayload messagePayload) {
        try {
            Message message = new Message();
            message.setContent(messagePayload.getContent());
            message.setSender(userRepository.findById(messagePayload.getSenderId()).orElseThrow(
                    () -> new ResourceNotFound("user", "id", messagePayload.getSenderId())
            ));
            message.setReceiver(userRepository.findById(messagePayload.getReceiverId()).orElseThrow(
                    () -> new ResourceNotFound("user", "id", messagePayload.getReceiverId())
            ));

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

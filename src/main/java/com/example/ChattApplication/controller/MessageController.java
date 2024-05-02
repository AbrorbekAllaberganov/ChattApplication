package com.example.ChattApplication.controller;

import com.example.ChattApplication.entity.Message;
import com.example.ChattApplication.payload.MessagePayload;
import com.example.ChattApplication.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public Message saveMessage(MessagePayload messagePayload){
        return messageService.saveMessage(messagePayload);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMessageById(@PathVariable Long id){
        return messageService.deleteById(id);
    }

    @GetMapping
    public List<Message> getMessagesBySenderAndReceiver(@RequestParam ("sender") Long sender,
                                                        @RequestParam ("receiver")Long receiver){
        return messageService.getMessagesBySenderAndReceiverId(sender, receiver);
    }

}

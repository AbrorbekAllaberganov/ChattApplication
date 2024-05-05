package com.example.ChattApplication.entity;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "chat_groups")
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Message> messages;
}

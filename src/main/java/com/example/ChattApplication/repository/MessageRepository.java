package com.example.ChattApplication.repository;

import com.example.ChattApplication.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true, value = "select * from messages where sender_id = ?1 and receiver_id =?2")
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}

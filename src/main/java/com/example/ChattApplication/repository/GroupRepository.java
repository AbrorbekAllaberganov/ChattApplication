package com.example.ChattApplication.repository;

import com.example.ChattApplication.entity.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ChatGroup,Long> {
}

package com.example.auction.repo;

import com.example.auction.model.Chat;
import com.example.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {

}

package com.example.auction.repo;

import com.example.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    String findByEmail(String mail);

    User findByName(String username);
}

package com.example.auction.repo;

import com.example.auction.model.Product;
import com.example.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByUserBuy(User user);
}

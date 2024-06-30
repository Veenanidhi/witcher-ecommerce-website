package com.witcher.e_commerce.application.witcher.dao;

import com.witcher.e_commerce.application.witcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByUsername(String userName);

    boolean existsByUsername(String username);
}

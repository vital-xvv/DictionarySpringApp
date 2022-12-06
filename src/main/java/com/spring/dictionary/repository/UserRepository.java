package com.spring.dictionary.repository;

import com.spring.dictionary.actors.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    void deleteUserByUsername(String username);
}

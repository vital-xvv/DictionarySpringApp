package com.spring.dictionary.repository;

import com.spring.dictionary.actors.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username=?1")
    User findByUsername(String username);

    void deleteUserByUsername(String username);

    @Modifying
    void updateUserFirstNameAndLastNameByUsername(String firstName, String lastName, String username);
}

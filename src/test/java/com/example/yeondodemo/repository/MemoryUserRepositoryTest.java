package com.example.yeondodemo.repository;

import com.example.yeondodemo.repository.user.MemoryUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
import com.example.yeondodemo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {
    UserRepository userRepository = new MemoryUserRepository();
    @BeforeEach
    void beforeEach(){
        userRepository.clearStore();
    }
    @AfterEach
    void afterEach(){
        userRepository.clearStore();
    }


    void findById() {
        User user = new User("avc", "1234");
        userRepository.save(user);
        assertThat(user).isEqualTo(userRepository.findById(user.getId()));
    }


    void findAll() {
        User user1 = new User("abc", "334");
        User user2 = new User("abcd", "1234");
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(userRepository.findAll().size()).isEqualTo(2);
        assertThat(userRepository.findAll()).contains(user1, user2);
    }


    void findByName() {
        User user = new User("avc", "1234");
        userRepository.save(user);
        assertThat(user).isEqualTo(userRepository.findByName(user.getUsername()));
    }
}
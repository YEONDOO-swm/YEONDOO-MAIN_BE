package com.example.yeondodemo.repository;

import com.example.yeondodemo.repository.user.MemoryUserRepository;
import com.example.yeondodemo.repository.user.UserRepository;
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
    }


    void findAll() {
    }


    void findByName() {
    }
}
package com.example.yeondodemo.repository.user;


import com.example.yeondodemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 @Slf4j
public class MemoryUserRepository implements UserRepository {
    private static final Map<Long, User> store = new HashMap<>();
    private static final Map<String, Long> name2id = new HashMap<>();
    private static Long sequence = 0L;
    @Override
    public User findById(Long id) {
        log.info("findByID: {}", id);
        return store.get(id);
    }
    @Override
    public User save(User user){
        log.info("save User {}", user);
        store.put(++sequence, user);
        user.setId(sequence);
        name2id.put(user.getUsername(), sequence);
        return user;
    }
    @Override
    public List<User> findAll() {
        log.info("findAll");
        return new ArrayList<User>(store.values());
    }

    @Override
    public User findByName(String name) {
        log.info("findByName name: {}", name);
        return store.get(name2id.get(name));
    }

    @Override
    public void clearStore() {
        store.clear();
        name2id.clear();
    }
    @Override
    public User update(User user){
        return user;
    }
}

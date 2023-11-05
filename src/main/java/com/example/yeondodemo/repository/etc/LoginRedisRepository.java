package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.entity.LoginEntity;
import com.example.yeondodemo.entity.RefreshEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface LoginRedisRepository extends CrudRepository<LoginEntity, String> {
}

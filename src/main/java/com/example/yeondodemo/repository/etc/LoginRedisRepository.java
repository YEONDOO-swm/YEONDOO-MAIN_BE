package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.entity.LoginEntity;
import com.example.yeondodemo.entity.RefreshEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LoginRedisRepository extends CrudRepository<LoginEntity, String> {
}

package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.entity.RefreshEntity;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RefreshRedisRepository extends CrudRepository<RefreshEntity, String> {
}

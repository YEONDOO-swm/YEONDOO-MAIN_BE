package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.entity.RefreshEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRedisRepository extends CrudRepository<RefreshEntity, String> {
}

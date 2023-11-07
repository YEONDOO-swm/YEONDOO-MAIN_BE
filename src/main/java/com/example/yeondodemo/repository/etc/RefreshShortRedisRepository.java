package com.example.yeondodemo.repository.etc;

import com.example.yeondodemo.entity.RefreshEntity;
import com.example.yeondodemo.entity.RefreshShort;
import org.springframework.data.repository.CrudRepository;

public interface RefreshShortRedisRepository extends CrudRepository<RefreshShort, String> {
}

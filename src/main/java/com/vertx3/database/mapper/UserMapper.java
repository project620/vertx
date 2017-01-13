package com.vertx3.database.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jim.huang on 2016/12/23.
 */
@Repository
public interface UserMapper {
    public Map<String, Object> getUserByName(@Param("username") String username);

    public String insert(Map<String, Object> param);
}

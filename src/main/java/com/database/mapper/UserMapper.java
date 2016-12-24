package com.database.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Created by jim.huang on 2016/12/23.
 */
@Repository
public interface UserMapper {
    public HashMap getUserByName(@Param("username")  String username);
}

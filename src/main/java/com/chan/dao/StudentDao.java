package com.chan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao {

    List<Map<String, Object>> queryInfo();

//    @Select("select * from Student where name = #{name}")
    Map<String, Object> login(Map<String, Object> params);

    int insertInfo(Map<String, Object> params);
}

package com.chan.dao;

import com.chan.model.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao {

    List<Student> query(Map<String, Object> params);

    List<Map<String, Object>> queryInfo();

    //    @Select("select * from Student where name = #{name}")
    Map<String, Object> login(Map<String, Object> params);

    int insertInfo(Map<String, Object> params);
}

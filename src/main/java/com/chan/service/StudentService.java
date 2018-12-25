package com.chan.service;


import com.chan.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> query(Map<String, Object> params);

    List<Map<String, Object>> queryInfo();

    Map<String, Object> login(Map<String, Object> params);

    int insertInfo(Map<String, Object> params);
}

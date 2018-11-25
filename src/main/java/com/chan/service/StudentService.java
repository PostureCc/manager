package com.chan.service;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Map<String,Object>> queryInfo();

    Map<String,Object> login(Map<String,Object> params);

    int insertInfo(Map<String, Object> params);
}

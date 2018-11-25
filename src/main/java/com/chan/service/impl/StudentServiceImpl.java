package com.chan.service.impl;

import com.chan.dao.StudentDao;
import com.chan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    @Qualifier("studentDao")
    private StudentDao studentDao;

    @Override
    public List<Map<String, Object>> queryInfo() {
        return studentDao.queryInfo();
    }

    @Override
    public Map<String, Object> login(Map<String, Object> params) {
        return studentDao.login(params);
    }

    @Override
    public int insertInfo(Map<String, Object> params) {
        return studentDao.insertInfo(params);
    }
}

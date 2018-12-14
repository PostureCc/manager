package com.chan.controller;

import com.chan.service.StudentService;
import com.chan.util.BaseService;
import com.chan.util.ResultBean;
import com.chan.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
public class StudentController extends BaseService {

    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;

//    @Autowired
//    @Qualifier("redisService")
//    private RedisService redisService;

    @Autowired
    @Qualifier("redisUtil")
    RedisUtil redisUtil;

//    private JedisUtil jedisUtil;

    @RequestMapping("/index")
    public String index(Model model) {
        int i = 5 / 0;
        return "index";
    }

    @ResponseBody
    @RequestMapping("/queryInfo")
    public ResultBean queryInfo(Model model) {
        System.err.println("value:" + model.asMap().get("name"));
        return super.successSingleResult(studentService.queryInfo());
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(String username, String password) {
        System.out.println(username + "\t" + password);
        Map<String, Object> param = new HashMap<>(3);
        param.put("name", username);
        param.put("password", password);
        String key = "USER_" + username;
        redisUtil.set(key, studentService.login(param), 1000L);
        String res = redisUtil.get(key).toString();
        return super.successSingleResult(res);
    }

    @ResponseBody
    @RequestMapping("/insertInfo")
    /**@RequestParam:用于映射请求的参数，例如这里的age是数值类型，如果不加该参数，则需要使用Integer来接收，否则报错*/
    public ResultBean insertInfo(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("age") int age,
                                 @RequestParam("gender") String gender, @RequestParam("telePhone") String telePhone,
                                 @RequestParam("email") String email, @RequestParam("classId") String classId) {
        Map<String, Object> param = new HashMap<>(9);
        param.put("name", name);
        param.put("password", password);
        param.put("age", age);
        param.put("gender", gender);
        param.put("telephone", telePhone);
        param.put("email", email);
        param.put("classid", classId);
        System.out.println("表单提交 获取值:" + name + age);

//        studentService.insertInfo(param);
        return null;
    }

}

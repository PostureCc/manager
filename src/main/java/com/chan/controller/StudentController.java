package com.chan.controller;

import com.chan.service.StudentService;
import com.chan.util.BaseService;
import com.chan.util.ResultBean;
import com.chan.util.id.SnowflakeIdWorker;
import com.chan.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.concurrent.CountDownLatch;

@Api("Student")
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

    @ApiOperation("返回主页")
    @RequestMapping("/index")
    public String index(Model model) {
        int i = 5 / 0;
        return "index";
    }

    @ApiOperation("Test Method2")
    @ResponseBody
    @RequestMapping("/queryInfo")
    public ResultBean queryInfo(Model model) {
        System.err.println("value:" + model.asMap().get("name"));
        return super.successSingleResult(studentService.queryInfo());
    }

    @ApiOperation("Test Method2")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true)})
    @ResponseBody
    @RequestMapping("/tokenQuery")
    public ResultBean tokenQuery() {
        return super.successSingleResult(null);
    }

    @ResponseBody
    @RequestMapping("/query")
    public ResultBean query() {
        List<Map<String, Object>> list = studentService.queryInfo();
        list = list.subList(0, 10);
        List<String> names = new ArrayList<>();
        list.forEach(item -> names.add(String.valueOf(item.get("name"))));
        Map<String, Object> params = new HashMap<>(3);
        params.put("names", names);
        params.put("password", "111");
        return super.successSingleResult(studentService.query(params));
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(@RequestParam("name") String username, @RequestParam("password") String password) {
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

    @ResponseBody
    @RequestMapping("/threadTest")
    public void threadTest(@RequestParam("name") String username, @RequestParam("password") String password) {
        int count = 100;
        //利用发令枪操作
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread() {
                public void run() {
                    String id = String.valueOf(SnowflakeIdWorker.snowflackIdWorkers.get().nextId());
                    Map<String, Object> param = new HashMap<>(3);
                    param.put("name", username + id);
                    param.put("password", password);
                    param.put("age", 0);
                    param.put("gender", "男");
                    //字符串太长 检查截取
                    int length = id.length();
                    param.put("telephone", id.substring(length - 7, length));
                    param.put("email", id + "@163.com");
                    param.put("classid", "1");
                    studentService.insertInfo(param);
                }
            }.start();
            countDownLatch.countDown();
        }
        try {
            //使线程在锁存器倒计数至零之前一直等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] agrs) {
//        for (int i = 0; i < 10; i++) {
//            String id = String.valueOf(SnowflakeIdWorker.snowflackIdWorkers.get().nextId());
//            log.info("id:{},length:{},substring:{}", id, id.length(), id.substring(id.length() - 2, id.length()));
//        }

//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        String[] mids = new String[list.size()];
//        list.toArray(mids);
//        strDemo(mids);

        Map<String, Object> map = new HashMap<>(3);
        map.put("is", "Y");
        System.out.println(map.containsKey("is"));
    }

    public static void strDemo(String... mids) {
        for (int i = 0; i < mids.length; i++) {
            System.out.println(mids[i].toString());
        }
    }

}

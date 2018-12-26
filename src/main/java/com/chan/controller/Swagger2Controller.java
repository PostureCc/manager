package com.chan.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api("Test Swagger")
@RestController
public class Swagger2Controller {

    //接口功能描述
    @ApiOperation("Test Method")
    //接口需要的参数 可以有多个 这里只写了一个 它个paramType参数有header/path/query/body/form等等
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "name", dataType = "String", required = false, defaultValue = "test")})
    @RequestMapping(value = "/index1/{name}", method = RequestMethod.GET)
    public Map index1(@ApiParam(defaultValue = "11") @PathVariable("name") String name) {
        System.out.println("params:" + name);
        Map<String, Object> map = new HashMap<>(3);
        map.put("key1", "value1");
        return map;
    }

    //接口功能描述
    @ApiOperation("Test Method2")
    //接口需要的参数 可以有多个 这里只写了一个 它个paramType参数有header/path/query/body/form等等
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "name", dataType = "String", required = false, defaultValue = "test")})
    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public Map index2(@ApiParam(defaultValue = "11") @RequestParam("name") String name) {
        System.out.println("params:" +   name);
        Map<String, Object> map = new HashMap<>(3);
        map.put("key2", "value2");
        return map;
    }


}

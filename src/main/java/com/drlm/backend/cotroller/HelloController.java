package com.drlm.backend.cotroller;

import com.drlm.backend.entities.TestObject;
import com.google.common.collect.Maps;
import com.sky.blue.web.JsonResult;
import com.sky.blue.web.JsonResultUtil;
import com.sky.blue.web.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * 如果配置了 blue.web.warp-response-body:true 则会自动将结果包装成JsonResult类型
     * */
    @RequestMapping(value = "/getDate")
    public Date getDate() {
        return new Date();
    }

    /**
     * 演示返回的数据是 Map 格式
     * */
    @RequestMapping(value = "/sayHello",method = RequestMethod.GET)
    public JsonResult sayHello() {
        Map resultMap= Maps.newLinkedHashMap();
        resultMap.put("hello","word");
        return JsonResultUtil.createSuccessResult(resultMap);

    }

    /**
     * 演示返回的数据是一个List<Object> 的格式
     * */
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
    public JsonResult queryList() {
        List<TestObject> list=new ArrayList<>();

        TestObject user=new TestObject();
        user.setName("天空");
        user.setCode("sky");

        TestObject user2=new TestObject();
        user2.setName("白云");
        user2.setCode("cloud");

        list.add(user);
        list.add(user2);
        return JsonResultUtil.createSuccessResult(list);
    }

    /**
     * 演示返回的数据是一个 Object 的格式
     * */
    @RequestMapping(value = "/queryObject",method = RequestMethod.GET)
    public JsonResult queryObject() {
        TestObject testObject=new TestObject();
        testObject.setName("天空");
        testObject.setCode("sky");
        return JsonResultUtil.createSuccessResult(testObject);
    }


    /**
     * 演示返回运行时异常  这类异常是运行时报出的错误
     * */
    @RequestMapping(value = "/testRunTimeException",method = RequestMethod.GET)
    public JsonResult testRunTimeException() {

        int i=8;
        System.out.println(i/0);
        return JsonResultUtil.createSuccessResult("OK");
    }


    /**
     * 演示返回业务异常 这类异常是程序主动抛出的
     * */
    @RequestMapping(value = "/testServiceException",method = RequestMethod.GET)
    public JsonResult testServiceException() {
        Map errMap=Maps.newHashMap();
        errMap.put("err","这是一个错误信息");
        throw new ServiceException("501","服务端自定义异常",errMap);

    }




}

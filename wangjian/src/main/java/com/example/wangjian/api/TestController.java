package com.example.wangjian.api;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
import com.example.wangjian.model.TestMoel;
import com.example.wangjian.service.TestService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;


@RestController
public class TestController {


    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;
    @Autowired(required = false)
    private TestService testService;

    @PostMapping("/test")
    @CrossOrigin
    public HashMap testCon(){
        MybatisMapperRefresh mrh = new MybatisMapperRefresh();
        mrh.refreshMapper(sqlSessionFactory,"classpath:mapper/TeacherMapper.xml");
        HashMap map = testService.selectInfo();
        TestMoel moel = new TestMoel();
        map.put("msg","Ngnix speck:"+"你现在访问的是8082服务器");
        //System.out.println("Ngnix speck:"+"你现在访问的是8081服务器");
        return map;
    }


    @PostMapping("/postTest")
    public HashMap postTest(@RequestBody  String datas){
//        System.out.println(datas);
//        JSONArray jsonarr = JSON.parseArray(datas);
//        if(jsonarr.size() > 0){
//            System.out.println(datas);
//        }
        return null;
    }


}

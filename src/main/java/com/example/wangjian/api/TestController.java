package com.example.wangjian.api;

import com.example.wangjian.model.TestMoel;
import com.example.wangjian.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class TestController {

    @Autowired(required = false)
    private TestService testService;

    @GetMapping("/test")
    public HashMap testCon(){
        HashMap map = testService.selectInfo();
        TestMoel moel = new TestMoel();
        return map;
    }
}

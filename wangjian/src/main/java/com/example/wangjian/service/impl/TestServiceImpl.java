package com.example.wangjian.service.impl;

import com.example.wangjian.mapper.TestMapper;
import com.example.wangjian.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class TestServiceImpl implements TestService {

    @Autowired(required = false)
    private TestMapper testMapper;

    @Override
    public HashMap selectInfo() {
        return testMapper.testselctInfo();
    }
}

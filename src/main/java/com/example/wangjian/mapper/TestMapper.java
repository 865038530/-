package com.example.wangjian.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface TestMapper {
    HashMap testselctInfo();
}

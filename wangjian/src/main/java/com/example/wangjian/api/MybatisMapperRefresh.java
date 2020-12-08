package com.example.wangjian.api;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

public class MybatisMapperRefresh{

//   @Autowired
//   @Qualifier("sqlSessionFactory")
   private SqlSessionFactory sqlSessionFactory;

   private Resource[] mapperLocations;

   private String packageSearchPath; //"classpath:mapper/*.xml";

   private List<String> changeList;

   private HashMap<String, Long> fileMapping = new HashMap<String, Long>();// 记录文件是否变化

 

   public void refreshMapper(SqlSessionFactory sqlSessionFactoryParam,String Path) {
       try {
           this.sqlSessionFactory = sqlSessionFactoryParam;
           this.packageSearchPath = Path;
           Configuration configuration = sqlSessionFactory.getConfiguration();

           try {
               this.scanMapperXml(packageSearchPath);
           } catch (IOException e) {
        	   System.out.println("扫描包路径配置错误");
               return;
           }

           if (this.isChanged()) {
               this.removeConfig(configuration);

               for (Resource configLocation : mapperLocations) {
                   try {
                       XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configLocation.getInputStream(),
                               configuration, configLocation.toString(), configuration.getSqlFragments());
                       xmlMapperBuilder.parse();
                       if (changeList.contains(configLocation.getFilename()))
                           System.out.println("[" + configLocation.getFilename() + "] refresh finished");
                   } catch (IOException e) {
                	  
                       continue;
                   }
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
   }



   /**
    * 扫描xml文件所在的路径
    *
    * @throws IOException
    */
   private void scanMapperXml(String path) throws IOException {
       this.mapperLocations = new PathMatchingResourcePatternResolver().getResources(path);
   }

   /**
    * 清空Configuration中几个重要的缓存
    *
    * @param configuration
    * @throws Exception
    */
   private void removeConfig(Configuration configuration) throws Exception {
       Class<?> classConfig = configuration.getClass();
       clearMap(classConfig, configuration, "mappedStatements");
       clearMap(classConfig, configuration, "caches");
       clearMap(classConfig, configuration, "resultMaps");
       clearMap(classConfig, configuration, "parameterMaps");
       clearMap(classConfig, configuration, "keyGenerators");
       clearMap(classConfig, configuration, "sqlFragments");

       clearSet(classConfig, configuration, "loadedResources");

   }

   private void clearMap(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
       Field field = classConfig.getDeclaredField(fieldName);
       field.setAccessible(true);
       Map mapConfig = (Map) field.get(configuration);
       mapConfig.clear();
   }

   private void clearSet(Class<?> classConfig, Configuration configuration, String fieldName) throws Exception {
       Field field = classConfig.getDeclaredField(fieldName);
       field.setAccessible(true);
       Set setConfig = (Set) field.get(configuration);
       setConfig.clear();
   }

   /**
    * 判断文件是否发生了变化
    *
    * @param
    * @return
    * @throws IOException
    */
   private boolean isChanged() throws IOException {
       boolean flag = false;
       changeList = new ArrayList<String>();
       for (Resource resource : mapperLocations) {
           String resourceName = resource.getFilename();

           boolean addFlag = !fileMapping.containsKey(resourceName);// 此为新增标识

           // 修改文件:判断文件内容是否有变化
           Long compareFrame = fileMapping.get(resourceName);
           long lastFrame = resource.contentLength() + resource.lastModified();
           boolean modifyFlag = null != compareFrame && compareFrame.longValue() != lastFrame;// 此为修改标识

           // 新增或是修改时,存储文件
           if (addFlag || modifyFlag) {
               fileMapping.put(resourceName, Long.valueOf(lastFrame));// 文件内容帧值
               flag = true;
               changeList.add(resource.getFilename());
           }
       }
       return flag;
   }


}

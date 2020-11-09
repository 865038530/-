package com.example.wangjian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.example.wangjian.mapper")
public class WangjianApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WangjianApplication.class, args);
    }

     /*
     * 用阿里连接池druid
    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
    */

    /**
     * Configure the application. Normally all you would need to do it add sources (e.g.
     * config classes) because other settings have sensible defaults. You might choose
     * (for instance) to add default command line arguments, or set an active Spring
     * profile.
     *
     * @param builder a builder for the application context
     * @return the application builder
     * @see SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        //return super.configure(builder); //这样也可行
        return builder.sources(WangjianApplication.class);
    }

}
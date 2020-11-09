package com.example.wangjian.Config;

//import com.github.pagehelper.PageHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wzj on 2018/3/17.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class MyBatisConfiguration
{
    /**
     * url
     */
    private String url;

    /**
     * 驱动名称
     */
    private String driverClassName;

    /**
     * 数据库名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    /*
     *使用JNDI方式连接数据库
    @Bean
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:/comp/env/jdbc/test");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
    */


}

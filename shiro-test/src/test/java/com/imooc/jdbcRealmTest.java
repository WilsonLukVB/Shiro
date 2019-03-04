package com.imooc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Test;

/**
 * @program: imoocshiro
 * @description: jdbc连接数据库测试
 * @author: WilsonLuk
 * @create: 2019-03-01 16:55
 */
public class jdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&SSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }


    @Test
    public void testAuthentication() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql ="select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);



        //1、构建security环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //2、主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
//
        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "654321");

        subject.login(token);

        System.out.println("ispower:" + subject.isAuthenticated());

//        subject.logout();
//        System.out.println("ispower:"+ subject.isAuthenticated());

//        subject.checkRoles("admin", "user");
//
//        subject.checkPermission("user:select");


    }
}

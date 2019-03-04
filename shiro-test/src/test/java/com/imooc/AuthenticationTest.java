package com.imooc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public  void addUser(){
        simpleAccountRealm.addAccount("Mark","123456","admin","user");
    }

    @Test
    public void testAuthentication(){

        //1、构建security环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2、主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject =SecurityUtils.getSubject();
//
         UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");

        subject.login(token);

        System.out.println("ispower:"+ subject.isAuthenticated());

//        subject.logout();
//        System.out.println("ispower:"+ subject.isAuthenticated());

        subject.checkRoles("admin","user");

        subject.checkPermission("admin");

    }

}

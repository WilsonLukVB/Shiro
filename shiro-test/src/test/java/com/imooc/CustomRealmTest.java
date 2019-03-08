package com.imooc;

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.junit.Test;

/**
 * @program: imoocshiro
 * @description:
 * @author: WilsonLuk
 * @create: 2019-03-08 11:04
 */
public class CustomRealmTest {
    @Test
    public void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();

        //1、构建security环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //算法名称和加密次数
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2、主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject =SecurityUtils.getSubject();
//
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","1234567");

        subject.login(token);

        System.out.println("ispower:"+ subject.isAuthenticated());


//        subject.logout();
//        System.out.println("ispower:"+ subject.isAuthenticated());

//        subject.checkRole("admin");
//
//        subject.checkPermissions("user:add","user:delete");

    }
}

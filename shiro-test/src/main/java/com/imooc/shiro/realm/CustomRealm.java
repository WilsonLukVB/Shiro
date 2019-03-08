package com.imooc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: imoocshiro
 * @description:
 * @author: WilsonLuk
 * @create: 2019-03-08 10:41
 */
public class CustomRealm  extends AuthorizingRealm {

    Map<String,String> useMap = new HashMap<String, String>(16);

    {
        useMap.put("Mark","d40fdd323f5322ff34a41f026f35cf20");

        super.setName("customRealm");

    }

    //用来做授权的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

       String username = (String)principalCollection.getPrimaryPrincipal();

       //从数据库中或者缓存中获取角色数据
       Set<String> roles = getRolesByUserName(username);
       Set<String> permissions = getPermissionsByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUsername(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    /**
    *@Description: 模拟数据中获取数据
    *@Param:
    *@Author: luweibin
    *@date: 2019/3/8
    */
    private Set<String> getRolesByUserName(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    //用来做认证的
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        //1、从主体传过来的认证信息，获得用户名
        String username =(String)authenticationToken.getPrincipal();

        //2、通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(username);
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo
                ("Mark",password,"customRealm");
        //加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));

        return authenticationInfo;
    }

    /**
    *@Description: 模拟查证数据库
    *@Param:
    *@Author: luweibin
    *@date: 2019/3/8
    */
    private String getPasswordByUserName(String username) {
        //读数据库
        return useMap.get(username);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1234567","Mark");
        System.out.println(md5Hash.toString());
    }
}

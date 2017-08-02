package chapter3;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.apache.shiro.authz.UnauthorizedException;
import junit.framework.Assert;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日下午2:56:31
 */
public class RoleTest {
	@Test  
	public void testHasRole() {  
		 Subject subject =login("classpath:shiro-permission.ini", "zhang", "123");
	    //判断拥有角色：role1  
	    Assert.assertTrue(subject.hasRole("role1"));  
	    //判断拥有角色：role1 and role2  
	    Assert.assertTrue(subject .hasAllRoles(Arrays.asList("role1", "role2")));  
	    //判断拥有角色：role1 and role2 and !role3  
	    boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));  
	    Assert.assertEquals(true, result[0]);  
	    Assert.assertEquals(true, result[1]);  
	    Assert.assertEquals(false, result[2]);  
	} 
	
	@Test (expected = UnauthorizedException.class)  
	public void testCheckRole() {  
		 Subject subject =login("classpath:shiro-permission.ini", "zhang", "123");
		   
	    //断言拥有角色：role1  
	    subject.checkRole("role1");  
	    //断言拥有角色：role1 and role3 失败抛出异常  
	    subject.checkRoles("role1", "role3");  
	    //如果上方的@Test的后边没有加(expected = UnauthorizedException.class)则会由于没有role3而报错
	} 
	
 @Test
 public void testIePermitted(){
	 Subject subject =login("classpath:shiro-permission.ini", "zhang", "123");
	  //Shiro提供了isPermitted和isPermittedAll用于判断用户是否拥有某个权限或所有权限
	 //判断拥有权限：user:create
     Assert.assertTrue(subject.isPermitted("user:create"));
     //判断拥有权限：user:update and user:delete
     Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));
     //判断没有权限：user:view
     Assert.assertFalse(subject.isPermitted("user:view"));
 }
 
 @Test (expected = UnauthorizedException.class)  
 public void testCheckPermission () {  
	 Subject subject =login("classpath:shiro-permission.ini", "zhang", "123");
	   //断言拥有权限：user:create  
	    subject.checkPermission("user:create");  
	    //断言拥有权限：user:delete and user:update  
	    subject .checkPermissions("user:delete", "user:update");  
	    //断言拥有权限：user:view 失败抛出异常  
	    subject .checkPermissions("user:view");  
	}   
  
 public static Subject login(String configFile,String username,String password){
		Factory<SecurityManager>factory=new IniSecurityManagerFactory(configFile);
		//2、得到SecurityManager实例，并绑定给SecurityUtils
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3、得到Subject以及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token= new UsernamePasswordToken(username,password);
		  //4、登录，即身份验证
		subject.login(token);
		 return subject;
	}
}

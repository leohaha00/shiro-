package chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import junit.framework.Assert;  
/**
 * @Author:  yangpeng
 * @Date:2017年8月2日上午9:19:14
 */
/*
 * 身份验证，即在应用中谁能证明他就是他本人。一般提供如他们的身份ID一些标识信息来表明他就是他本人，如提供身份证，用户名/密码来证明。
	在shiro中，用户需要提供principals （身份）和credentials（证明）给shiro，从而应用能验证用户身份：
	principals：身份，即主体的标识属性，可以是任何东西，如用户名、邮箱等，唯一即可。一个主体可以有多个principals，但只有一个Primary principals，一般是用户名/密码/手机号。
	credentials：证明/凭证，即只有主体知道的安全值，如密码/数字证书等。
	最常见的principals和credentials组合就是用户名/密码了。接下来先进行一个基本的身份认证。
 
	另外两个相关的概念是之前提到的Subject及Realm，分别是主体及验证主体的数据源
 */
public class LoginLogoutTest {
	@Test
	public void testHelloWorld(){
		//1、获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<SecurityManager>factory=new IniSecurityManagerFactory("classpath:shiro.ini");
		//2、得到SecurityManager实例，并绑定给SecurityUtils
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3、得到Subject以及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token= new UsernamePasswordToken("zhang","123");
		try{
			//4、登录，即身份验证
			subject.login(token);
		}catch (AuthenticationException e) {
			//5、身份验证失败
		/*
		 * 常见的错误如下：
		 * DisabledAccountException（禁用的帐号）、
		 * LockedAccountException（锁定的帐号）、
		 * UnknownAccountException（错误的帐号）、
		 * ExcessiveAttemptsException（登录失败次数过多）、
		 * IncorrectCredentialsException （错误的凭证）、
		 * ExpiredCredentialsException（过期的凭证）等，具体请查看其继承关系；
		 * 对于页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库；
		 */
			e.printStackTrace();
			// TODO: handle exception
		}
		Assert.assertEquals(true, subject.isAuthenticated());//断言用户已经登录
		//6、退出
		subject.logout();
	}
	
}

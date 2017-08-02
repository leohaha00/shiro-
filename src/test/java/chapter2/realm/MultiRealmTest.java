package chapter2.realm;

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
 * @Date:2017年8月2日上午10:48:40
 */
public class MultiRealmTest {
	@Test
	public void testHelloWorld(){
		//1、获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<SecurityManager>factory=new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");
		//2、得到SecurityManager实例，并绑定给SecurityUtils
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3、得到Subject以及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token= new UsernamePasswordToken("wang","123");
		try{
			//4、登录，即身份验证
			subject.login(token);
		}catch (AuthenticationException e) {
			//5、身份验证失败
			e.printStackTrace();
			// TODO: handle exception
		}
		Assert.assertEquals(true, subject.isAuthenticated());//断言用户已经登录
		//6、退出
		subject.logout();
	}
}

package chapter2.jdbc;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日上午11:56:59
 */
public class JdbcTest {
	  @Test
	    public void testHelloworld() {
	        Factory<SecurityManager> factory =
	                new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
	        //2、得到SecurityManager实例 并绑定给SecurityUtils,全局设置，设置一次即可
	        SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);//自动绑定当前线程，在多请求的环境下需要
	        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
	        Subject subject = SecurityUtils.getSubject();
	        /*
	         * yangp_39", "123456"对应于数据库中的用户名与密码
	         */
	        UsernamePasswordToken token = new UsernamePasswordToken("yangp_39", "123456");
	        try {
	            //4、登录，即身份验证
	            //4.1 当使用realm时，login调用的时候会调用reaml实例中的getAuthenticationInfo函数,当使用Jdbcrealm时，会调用源码中的doGetAuthenticationInfo()函数
	            subject.login(token);
	        } catch (AuthenticationException e) {
	            //5、身份验证失败
	        }
	        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

	        //6、退出
	        subject.logout();
	    }
}

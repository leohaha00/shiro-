
package common;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日下午2:26:18
 */
public class Authenticator {
	public static void login(String configFile,String username,String password){
		Factory<SecurityManager>factory=new IniSecurityManagerFactory(configFile);
		//2、得到SecurityManager实例，并绑定给SecurityUtils
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3、得到Subject以及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token= new UsernamePasswordToken(username,password);
		  //4、登录，即身份验证
		subject.login(token);
		 
	}
}

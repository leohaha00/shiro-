package chapter2.realm;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * @param <instanceOf>
 * @Author:  yangpeng
 * @Date:2017年8月2日上午9:58:49
 */
/*
 * Realm：域 ，shiro从Realm获取安全数据，比如（用户，角色，权限），就是说
 * 就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
 * 如我们之前的ini配置方式将使用org.apache.shiro.realm.text.IniRealm。
 */
/*
 * org.apache.shiro.realm.Realm接口如下： 
	String getName(); //返回一个唯一的Realm名字
	boolean supports(AuthenticationToken token); //判断此Realm是否支持此Token
	AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
 	throws AuthenticationException;  //根据Token获取认证信息	
 */

/*
 * 单Realm配置
 */
public class MyRealm2  implements Realm {
	/*
	 	根据Token获取认证信息
	 */
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username=(String)token.getPrincipal();//得到用户名
		String password=new String((char[])token.getCredentials());//得到密码
		if(!"wang".equals(username)){
			throw new UnknownAccountException();//如果用户名错误
		}
		if(!"123".equals(password)){
			throw new IncorrectCredentialsException();//如果密码错误
		}
		//如果身份认证验证成功，返回一个AuthenticationInfo实现
		return new SimpleAuthenticationInfo(username,password,getName());
	}

	
	public String getName() {
		 
		 return "myrealm2"; 
	}

	 
	public boolean supports(AuthenticationToken token) {
		 
	//仅支持UserNamePasswordToken类型的token
		return token instanceof UsernamePasswordToken;
	}

}

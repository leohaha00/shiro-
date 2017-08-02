package chapter5;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.Realm;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日下午4:43:27
 */
public class MyRealm implements Realm {
	private PasswordService passwordService;
	public void setPasswordService(PasswordService passwordService){
		this.passwordService=passwordService;
	}
	 public String getName() {
	 	return "myRealm";
	}

	 public boolean supports(AuthenticationToken token) {
		 return false;
	}

	 public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		 return new SimpleAuthenticationInfo(   "wu",  passwordService.encryptPassword("123"), getName());  
	}

}

package chapter2.authenticator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import chapter2.login.Authenticator;
import junit.framework.Assert;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日下午2:29:16
 */
public class AuthenticatorTest {
	@Test  
	public void testAllSuccessfulStrategyWithSuccess() {  
	    Authenticator.login("classpath:shiro-authenticator-all-success.ini");  
	    Subject subject = SecurityUtils.getSubject();  
	  
	    //得到一个身份集合，其包含了Realm验证成功的身份信息  
	    PrincipalCollection principalCollection = subject.getPrincipals();  
	    Assert.assertEquals(2, principalCollection.asList().size());  
	}  
	@Test(expected = UnknownAccountException.class)  
    public void testAllSuccessfulStrategyWithFail() {  
		 Authenticator.login("classpath:shiro-authenticator-all-fail.ini");  
        Subject subject = SecurityUtils.getSubject();  
}   
}

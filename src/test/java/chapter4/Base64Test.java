package chapter4;

import java.util.Random;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import junit.framework.Assert;

/**
 * @Author:  yangpeng
 * @Date:2017年8月2日下午4:10:26
 */
/*
 * Base64编码/解码操作
 */
public class Base64Test {
	@Test
	public void base64Test(){
		String str="hello";
		String base64Encoded=Hex.encodeToString(str.getBytes());//编码
		System.out.println(base64Encoded);
		String str2=new String(Hex.decode(base64Encoded.getBytes()));//解码
		Assert.assertEquals(str, str2);
	}
	//散列算法
	/*
	 * 散列算法一般用于生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，常见的散列算法如MD5、SHA等。
	 * 一般进行散列时最好提供一个salt（盐），比如加密密码“admin”，产生的散列值是“21232f297a57a5a743894a0e4a801fc3”，
	 * 可以到一些md5解密网站很容易的通过散列值得到密码“admin”，即如果直接对密码进行散列相对来说破解更容易，此时我们可
	 * 以加一些只有系统知道的干扰数据，如用户名和ID(常称为salt)（即盐）；这样散列的对象是“密码+用户名+ID”，这样生成
	 * 的散列值相对来说更难破解。
	 */  
	@Test
	public void md5Test(){
		/*
		 * MD5
		 */
		String str="hello";
		String salt="123";
		String md5=new Md5Hash(str,salt).toString();
		System.out.println("md5:"+md5);
		String md5ToBase64=Hex.encodeToString(md5.getBytes());
		System.out.println("md5toBase64:"+md5ToBase64);
		//散列时还可以指定散列次数
		String multiMd5 = new Md5Hash(str, salt,2).toString(); 
		System.out.println("multiMd5:"+multiMd5);
		/*
		 * SHA256, 另外还有如SHA1、SHA512算法
		 */
		String sha1 = new Sha256Hash(str, salt).toString();
		System.out.println("sha256:"+sha1);
		//过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
		 String simpleHash = new SimpleHash("SHA-1", str, salt).toString();   
		 System.out.println("simpleHash:"+simpleHash);
	}
	@Test
	public void hashServiceTest(){
		//Shiro提供了HashService，默认提供了DefaultHashService实现
		DefaultHashService hashService=new DefaultHashService();
		//默认算法是SHA-512
		System.out.println("默认算法："+hashService.getHashAlgorithmName());
		hashService.setHashAlgorithmName("SHA-512");//可以修改算法
		// 私盐，默认无      可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
		hashService.setPrivateSalt(new SimpleByteSource("123")); 
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false  
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个  
		hashService.setHashIterations(1); //生成Hash值的迭代次数  
		  
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))  
		            .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();  
		String hex = hashService.computeHash(request).toHex();  
		System.out.println("hex:"+hex);
		//SecureRandomNumberGenerator用于生成一个随机数
		SecureRandomNumberGenerator generator=new SecureRandomNumberGenerator();
		//System.out.println("随机数："+generator.getDefaultNextBytesSize());
		generator.setSeed("123".getBytes());
		String hex2=generator.nextBytes().toHex();
		System.out.print( "hex2:"+hex2);
	}
	
	/*
	 * 加密与解密  对称式与非对称式
	 */
}

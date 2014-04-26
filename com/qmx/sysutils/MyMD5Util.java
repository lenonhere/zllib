package com.qmx.sysutils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 *
 * @Title: MyMD5Util.java
 * @Package com.qmx.sysutils
 * @Description: TODO(常用加密算法)
 *               <P>
 *               1.目前java6本身只是支持MD2和MD5的消息摘要实现
 *               </P>
 * @author qmx
 * @date 2013/02/19 17:26:55
 * @version V1.0
 */
public class MyMD5Util {

	// 常用的分组密码算法有：DES、AES、Lucifer、Madryga、NewDES、FEAL、REDOC、LOKI、Khufu和Khafre、RC2、IDEA、GOST、CAST、Blowfish、SAFER、RC5、3-WAY、Crab、SXAL8/MBAL等。
	//
	// 常用的流密码算法有：A5/1、A5/2、Chameleon、FISH、Helix、ISAAC、MUGI、Panama、Phelix、Pike、SEAL、SOBER、SOBER-128、WAKE等。
	//

	/* TODO 消息摘要算法包含 MD,SHA,MAC三大系列。经常用来验证数据的完整性。是数字签名的核心算法 */
	public static final String KEY_ALGORITHM_MD2 = "MD2";//
	public static final String KEY_ALGORITHM_MD4 = "MD4";
	public static final String KEY_ALGORITHM_MD5 = "MD5";
	public static final String KEY_ALGORITHM_SHA1 = "SHA-1";//
	public static final String KEY_ALGORITHM_SHA224 = "SHA-224";
	public static final String KEY_ALGORITHM_SHA256 = "SHA-256";
	public static final String KEY_ALGORITHM_SHA384 = "SHA-384";
	public static final String KEY_ALGORITHM_SHA512 = "SHA-512";
	public static final String KEY_ALGORITHM_HMACMD2 = "HmacMD2";//
	public static final String KEY_ALGORITHM_HMACMD4 = "HmacMD4";
	public static final String KEY_ALGORITHM_HMACMD5 = "HmacMD5";
	public static final String KEY_ALGORITHM_HMACSHA1 = "HmacSHA1";
	public static final String KEY_ALGORITHM_HMACSHA224 = "HmacSHA224";
	public static final String KEY_ALGORITHM_HMACSHA256 = "HmacSHA256";
	public static final String KEY_ALGORITHM_HMACSHA384 = "HmacSHA384";
	public static final String KEY_ALGORITHM_HMACSHA512 = "HmacSHA512";
	public static final String KEY_ALGORITHM_RIPEMD128 = "RipeMD128";//
	public static final String KEY_ALGORITHM_RIPEMD160 = "RipeMD160";
	public static final String KEY_ALGORITHM_RIPEMD256 = "RipeMD256";
	public static final String KEY_ALGORITHM_RIPEMD320 = "RipeMD320";
	public static final String KEY_ALGORITHM_HMACRIPEMD128 = "HmacRipeMD128";
	public static final String KEY_ALGORITHM_HMACRIPEMD160 = "HmacRipeMD160";

	/** TODO 以下为对称密钥算法 */
	// 数据加密标准(Data Encryption Standard, DES)不安全
	public static final String KEY_ALGORITHM_DES = "DES";
	// 三重DES算法（DESede）低效
	public static final String KEY_ALGORITHM_DESEDE = "DESede";// 产生单钥加密的密钥
	// AES算法（advanced Encryption Standard）比DES要快，安全性高(RC2,RC4,Blowfish算法参考AES)
	public static final String KEY_ALGORITHM_AES = "AES";
	// AES候选算法(Rijndael,Serpent,Twofish)参考IDEA
	public static final String KEY_ALGORITHM_IDEA = "IDEA";
	// PBE算法（Password Base Encryption，基于口令加密）
	public static final String KEY_ALGORITHM_PBE = "PBE";
	// Java6支持的PBE算法:PBEWITHMD5andDES,PBEWITHMD5ANDTRIPLEDES,PBEWITHSHAANDDESEDE,PBEWITHSHA1ANDRC2_40,PBKDF2WITHHMACSHA1
	public static final String KEY_ALGORITHM_PBEWITHMD5ANDDES = "PBEWITHMD5andDES";

	/** TODO 以下为非对称密钥算法 */
	public static final String KEY_ALGORITHM_DH = "DH";
	// ElGamal算法和ECC算法基于离散对数问题(公钥加密、私钥解密)
	public static final String KEY_ALGORITHM_ELGAMAL = "ElGamal";
	// RSA加密原则：[公钥加密，私钥解密] / [私钥加密，公钥解密]
	public static final String KEY_ALGORITHM_RSA = "RSA";// RSA算法相对于DES/AES等对称加密算法，他的速度要慢的多
	// RSA数字签名: 签名/验证算法 [私钥签名，公钥验证]
	public static final String KEY_ALGORITHM_RSA_MD5WITHRSA = "MD5withRSA";
	// DSA数字签名算法.[私钥签名，公钥验证]
	public static final String KEY_ALGORITHM_DSA = "DSA";// 产生双钥的密钥对(私钥签名，公钥验证)
	// DSA数字签名: 签名/验证算法
	public static final String KEY_ALGORITHM_SHA1WITHDSA = "SHA1withDSA";
	// ECDSA数字签名算法.
	public static final String KEY_ALGORITHM_ECDSA = "ECDSA";

	/* 加密/解密算法/工作模式/填充方式 */
	public static final String CIPHER_ALGORITHM_DES = "DES/ECB/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_DESEDE = "DESede/ECB/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_AES = "AES/ECB/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_IDEA = "IDEA/ECB/ISO10126Padding";
	/*
	 * 密钥长度，DH算法的默认密钥长度是1024, 密钥长度必须是8的倍数，在160到16384位之间
	 *
	 * DSA密钥长度，RSA算法的默认密钥长度是1024 密钥长度必须是64的倍数，在512到1024位之间
	 *
	 * 关于加密算法DES/DESede/Blowfish的密钥位数(注意：一个字符=1个字节=8个字位):
	 *
	 * DES：算法DES要求密钥长度为64位密钥, 有效密钥56位。64bits=8*8*1，即8个ascii字符。
	 * DESede：算法DESede要求的密钥位数为192位，即192bits=64*3=8*8*3，即24个ascii字符。
	 * Blowfish：算法Blowfish要求密钥长度为8--448字位，即8--448(bits)。即：1个到56个ascii字符
	 */
	public static final int KEY_SIZE_56 = 56;
	public static final int KEY_SIZE_64 = 64;
	public static final int KEY_SIZE_128 = 128;
	public static final int KEY_SIZE_168 = 168;
	public static final int KEY_SIZE_192 = 192;
	public static final int KEY_SIZE_256 = 256;
	public static final int KEY_SIZE_512 = 512;
	public static final int KEY_SIZE_1024 = 1024;

	/* 迭代次数 */
	public static final int ITERATION_COUNT_100 = 100;

	// 公钥
	private static final String PUBLIC_KEY = "PublicKey";
	// 私钥
	private static final String PRIVATE_KEY = "PrivateKey";
	// 编码
	public final static String ENCODING_UTF8 = "UTF-8";
	public final static String ENCODING_ISO88591 = "ISO-8859-1";

	/**
	 * 测试循环冗余校验
	 *
	 * @return
	 */
	public static String CRC(String src) {
		CRC32 c32 = new CRC32();
		c32.update(src.getBytes());
		String hex = Long.toHexString(c32.getValue());
		System.out.println("原文：" + src);
		System.out.println("CRC-32处理后：" + hex);
		return hex;
	}

	/**
	 * 字符串加密
	 *
	 * @param ssoToken
	 *            明码
	 * @return String 密码
	 */
	public static String encrypt(String ssoToken) {
		String pwd = "";
		try {
			if (ssoToken != null && !"".equals(ssoToken)) {

				byte[] _ssoToken = ssoToken.getBytes(ENCODING_ISO88591);
				for (int i = 0; i < _ssoToken.length; i++) {
					int asc = _ssoToken[i];
					_ssoToken[i] = (byte) (asc + 27);
					pwd += (asc + 27) + "%";
				}
			}
		} catch (Exception e) {
			pwd = "";
			e.printStackTrace();
		}
		return pwd;
	}

	/**
	 * 字符串解密
	 *
	 * @param ssoToken
	 *            密码
	 * @return String 明码
	 */
	public static String decrypt(String ssoToken) {
		String pwd = "";
		try {
			if (ssoToken != null && !"".equals(ssoToken)) {

				StringTokenizer st = new StringTokenizer(ssoToken, "%");
				while (st.hasMoreElements()) {
					int asc = Integer.parseInt((String) st.nextElement()) - 27;
					pwd += (char) asc;
				}
			}
		} catch (Exception e) {
			pwd = "";
			e.printStackTrace();
		}
		return pwd;
	}

	/**
	 * @param algorithm
	 *            加密算法 {MD2,MD4,MD5}
	 * @param src
	 *            明文
	 * @return String 密码(16进制)
	 * @throws Exception
	 */
	public static String toMDxHex(String algorithm, String src) {
		// 得到该摘要
		byte[] b = null;
		try {
			if (algorithm == null || "".equals(algorithm.trim())) {
				algorithm = KEY_ALGORITHM_MD5;
			} else {
				// 加入对bouncy castle的支持
				Security.addProvider(new BouncyCastleProvider());
			}

			// 初始化MessageDigest
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 添加要进行计算摘要的信息
			md.update(src.getBytes(ENCODING_UTF8));
			b = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		// 将摘要转为字符串
		String pwd = byte2hex(b);
		// String pwd = new String(Hex.encode(b));
		return pwd;
	}

	/**
	 * MD5加密(32位)
	 *
	 * @param src
	 *            明码
	 * @return 密码
	 */
	public static String toMD5(String src) {
		try {
			// 得到一个md5的消息摘要
			MessageDigest md = MessageDigest.getInstance(KEY_ALGORITHM_MD5);
			// 添加要进行计算摘要的信息
			md.update(src.getBytes());
			// 得到该摘要
			byte b[] = md.digest();
			// 将摘要转为字符串
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO Base64编码 Begin

	/***
	 * 进行Base64编码
	 *
	 * @param data
	 *            要编码的数据
	 * @return 编码后的数据
	 * */
	public static String encode(String data) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		byte[] b = Base64.encode(data.getBytes(ENCODING_UTF8));
		return new String(b, ENCODING_UTF8);
	}

	/**
	 * 进行Base64解码
	 *
	 * @param data
	 *            待解码数据
	 * @return 解码后的数据
	 * */
	public static String decode(String data) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		byte[] b = Base64.decode(data.getBytes(ENCODING_UTF8));
		return new String(b, ENCODING_UTF8);
	}

	/**
	 * BASE64 加密
	 *
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public String base64Encoder(String src) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src.getBytes(ENCODING_UTF8));
	}

	/**
	 * BASE64解密
	 *
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public String base64Decoder(String dest) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		return new String(decoder.decodeBuffer(dest), ENCODING_UTF8);
	}

	// Base64编码 End

	// TODO 对称加密算法：基于口令加密-PBE算法实现 Begin
	/**
	 * 盐初始化 盐长度必须为8字节
	 *
	 * @return byte[] 盐
	 * */
	public static byte[] initSalt() throws Exception {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}

	/**
	 * 转换密钥
	 *
	 * @param password
	 *            密码
	 * @return Key 密钥
	 * */
	private static Key toKey(String password) throws Exception {
		// 密钥彩礼转换
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		// 实例化
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM_PBEWITHMD5ANDDES);
		// 生成密钥
		SecretKey secretKey = keyFactory.generateSecret(keySpec);

		return secretKey;
	}

	/**
	 * 加密
	 *
	 * @param data
	 *            待加密数据
	 * @param password
	 *            密码
	 * @param salt
	 *            盐
	 * @return byte[] 加密数据
	 *
	 * */
	public static byte[] encrypt(byte[] data, String password, byte[] salt)
			throws Exception {
		// 转换密钥
		Key key = toKey(password);
		// 实例化PBE参数材料
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt,
				ITERATION_COUNT_100);
		// 实例化
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PBEWITHMD5ANDDES);
		// 初始化
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            待解密数据
	 * @param password
	 *            密码
	 * @param salt
	 *            盐
	 * @return byte[] 解密数据
	 *
	 * */
	public static byte[] decrypt(byte[] data, String password, byte[] salt)
			throws Exception {
		// 转换密钥
		Key key = toKey(password);
		// 实例化PBE参数材料
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt,
				ITERATION_COUNT_100);
		// 实例化
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PBEWITHMD5ANDDES);
		// 初始化
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		// 执行操作
		return cipher.doFinal(data);
	}

	// 对称加密算法：基于口令加密-PBE算法实现 End

	// TODO DES对称加密算法 Begin
	/**
	 * 3DES加密
	 *
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String desedeEncoder(String src, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key),
				KEY_ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DESEDE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(src.getBytes(ENCODING_UTF8));
		return byte2hex(b);
	}

	/**
	 * 3DES解密
	 *
	 * @param dest
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String desedeDecoder(String dest, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key),
				KEY_ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DESEDE);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(str2ByteArray(dest));
		return new String(b, ENCODING_UTF8);
	}

	/**
	 * 构造3DES加解密方法key
	 *
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	private byte[] build3DesKey(String keyStr) throws Exception {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes(ENCODING_UTF8);
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 *
	 * @param algorithm
	 *            加密算法 {DES,DESede,AES,IDEA}
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initkey(String algorithm) throws Exception {
		int keysize;

		if (KEY_ALGORITHM_IDEA.equalsIgnoreCase(algorithm)) {
			// 加入bouncyCastle支持
			Security.addProvider(new BouncyCastleProvider());
			keysize = KEY_SIZE_128;
		} else if (KEY_ALGORITHM_AES.equalsIgnoreCase(algorithm)) {
			keysize = KEY_SIZE_256;// AES要求密钥长度为128位、192位、256位
		} else if (KEY_ALGORITHM_DESEDE.equalsIgnoreCase(algorithm)) {
			keysize = KEY_SIZE_168;
		} else {
			algorithm = KEY_ALGORITHM_DES;
			keysize = KEY_SIZE_56;
		}
		// 实例化密钥生成器
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		// 初始化密钥生成器
		kg.init(keysize);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获取二进制密钥编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 *
	 * @param algorithm
	 *            加密算法 {DES,DESede,AES,IDEA}
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * */
	public static Key toKey(String algorithm, byte[] key) throws Exception {

		if (KEY_ALGORITHM_AES.equalsIgnoreCase(algorithm)
				|| KEY_ALGORITHM_IDEA.equalsIgnoreCase(algorithm)) {
			// 生成密钥
			SecretKey secretKey = new SecretKeySpec(key, algorithm);
			return secretKey;
		} else if (KEY_ALGORITHM_DESEDE.equalsIgnoreCase(algorithm)) {
			algorithm = KEY_ALGORITHM_DESEDE;
		} else {
			algorithm = KEY_ALGORITHM_DES;
		}

		// 实例化Des密钥
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		// 生成密钥
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * 加密数据
	 *
	 * @param algorithm
	 *            加密算法 {DES,DESede,AES,IDEA}
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encrypt(String algorithm, byte[] data, byte[] key)
			throws Exception {
		String transformation;
		if (KEY_ALGORITHM_IDEA.equalsIgnoreCase(algorithm)) {
			// 加入bouncyCastle支持
			Security.addProvider(new BouncyCastleProvider());
			transformation = CIPHER_ALGORITHM_IDEA;
		} else if (KEY_ALGORITHM_AES.equalsIgnoreCase(algorithm)) {
			algorithm = KEY_ALGORITHM_AES;
			transformation = CIPHER_ALGORITHM_AES;
		} else if (KEY_ALGORITHM_DESEDE.equalsIgnoreCase(algorithm)) {
			algorithm = KEY_ALGORITHM_DESEDE;
			transformation = CIPHER_ALGORITHM_DESEDE;
		} else {
			algorithm = KEY_ALGORITHM_DES;
			transformation = CIPHER_ALGORITHM_DES;
		}
		// 还原密钥
		Key k = toKey(algorithm, key);
		// 实例化
		Cipher cipher = Cipher.getInstance(transformation);
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密数据
	 *
	 * @param algorithm
	 *            加密算法 {DES,DESede,AES}
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * */
	public static byte[] decrypt(String algorithm, byte[] data, byte[] key)
			throws Exception {
		String transformation;
		if (KEY_ALGORITHM_AES.equalsIgnoreCase(algorithm)) {
			algorithm = KEY_ALGORITHM_AES;
			transformation = CIPHER_ALGORITHM_AES;
		} else if (KEY_ALGORITHM_DESEDE.equalsIgnoreCase(algorithm)) {
			algorithm = KEY_ALGORITHM_DESEDE;
			transformation = CIPHER_ALGORITHM_DESEDE;
		} else {
			algorithm = KEY_ALGORITHM_DES;
			transformation = CIPHER_ALGORITHM_DES;
		}
		// 欢迎密钥
		Key k = toKey(algorithm, key);
		// 实例化
		Cipher cipher = Cipher.getInstance(transformation);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	// DES对称加密算法 End

	// TODO MAC消息摘要组件 Begin
	/**
	 * 初始化HmacMD5的密钥
	 *
	 * @param algorithm
	 *            加密算法 {}
	 * @return byte[] 密钥
	 *
	 * */
	public static byte[] initHmacKey(String algorithm) throws Exception {
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_HMACMD5;
		} else if ("HmacSHA224".equalsIgnoreCase(algorithm)) {
			// 加入对bouncy castle的支持
			Security.addProvider(new BouncyCastleProvider());
		}
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		// 产生密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取密钥
		return secretKey.getEncoded();
	}

	/**
	 * HmacMD5消息摘要
	 *
	 * @param algorithm
	 *            加密算法 {}
	 *
	 * @param data
	 *            待做摘要处理的数据
	 * @param key
	 *            密钥
	 * @return byte[] 消息摘要
	 * */
	public static byte[] encodeHmac(String algorithm, byte[] data, byte[] key)
			throws Exception {
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_HMACMD5;
		} else if ("HmacSHA224".equalsIgnoreCase(algorithm)) {
			// 加入对bouncy castle的支持
			Security.addProvider(new BouncyCastleProvider());
		}
		// 还原密钥，因为密钥是以byte形式为消息传递算法所拥有
		SecretKey secretKey = new SecretKeySpec(key, algorithm);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要处理
		return mac.doFinal(data);
	}

	// MAC消息摘要组件 End

	// RipeMD128消息摘要处理 Begin
	/**
	 * RipeMD128消息摘要
	 *
	 * @param data
	 *            待处理的消息摘要数据
	 * @return byte[] 消息摘要
	 * */
	public static String encodeRipeMD128(String algorithm, byte[] data)
			throws Exception {
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_RIPEMD128;
		}
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance(algorithm);
		// 执行消息摘要
		byte[] b = md.digest(data);
		// 做十六进制的编码处理
		return new String(Hex.encode(b));

	}

	// RipeMD128消息摘要处理 End
	// HmacRipeMD128消息摘要 Begin
	/**
	 * 初始化HmacRipeMD128的密钥
	 *
	 * @return byte[] 密钥
	 * */
	public static byte[] initHmacRipeMD128Key(String algorithm)
			throws Exception {
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_HMACRIPEMD128;
		}
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		// 产生密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取密钥
		return secretKey.getEncoded();
	}

	/**
	 * HmacRipeMD128消息摘要
	 *
	 * @param data
	 *            待做摘要处理的数据
	 * @param key
	 *            密钥
	 * @return byte[] 消息摘要
	 * */
	public static String encodeHmacRipeMD128(String algorithm, byte[] data,
			byte[] key) throws Exception {
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_HMACRIPEMD128;
		}
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 还原密钥，因为密钥是以byte形式为消息传递算法所拥有
		SecretKey secretKey = new SecretKeySpec(key, algorithm);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要处理
		byte[] b = mac.doFinal(data);
		// 做十六进制转换
		return new String(Hex.encode(b));
	}

	// HmacRipeMD128消息摘要 End

	// Begin

	/**
	 * 进行SHA加密( DigestUtils.java )
	 *
	 * @param algorithm
	 *            加密算法 {}
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的字符串
	 */
	public static String encryptToSHA(String algorithm, String info) {
		byte[] digesta = null;
		try {
			if (algorithm == null || "".equals(algorithm.trim())) {
				algorithm = KEY_ALGORITHM_SHA1;
			} else if ("SHA-224".equalsIgnoreCase(algorithm)) {
				// 加入对bouncy castle的支持
				Security.addProvider(new BouncyCastleProvider());
			}
			// 得到一个SHA-1的消息摘要
			MessageDigest alga = MessageDigest.getInstance(algorithm);
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		// rs = new String (Hex.encode(b));
		return rs;
	}

	// //////////////////////////////////////////////////////////////////////////
	/**
	 * 创建密匙
	 *
	 * @param algorithm
	 *            加密算法,可用 DES,DESede,Blowfish
	 * @return SecretKey 秘密（对称）密钥
	 */
	public static SecretKey createSecretKey(String algorithm) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 返回密匙
		return deskey;
	}

	/**
	 * 根据密匙进行DES加密
	 *
	 * @param key
	 *            密匙
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encryptToDES(SecretKey key, String info) {

		// 加密随机数生成器 (RNG),(可以不写)
		SecureRandom sr = new SecureRandom();
		// 定义要生成的密文
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(KEY_ALGORITHM_DES);
			// 用指定的密钥和模式初始化Cipher对象
			// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key, sr);
			// 对要加密的内容进行编码处理,
			cipherByte = c1.doFinal(info.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回密文的十六进制形式
		return byte2hex(cipherByte);
	}

	/**
	 * 根据密匙进行DES解密
	 *
	 * @param key
	 *            密匙
	 * @param sInfo
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decryptByDES(SecretKey key, String sInfo) {

		// 加密随机数生成器 (RNG)
		SecureRandom sr = new SecureRandom();
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(KEY_ALGORITHM_DES);
			// 用指定的密钥和模式初始化Cipher对象
			c1.init(Cipher.DECRYPT_MODE, key, sr);
			// 对要解密的内容进行编码处理
			cipherByte = c1.doFinal(hex2byte(sInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return byte2hex(cipherByte);
		return new String(cipherByte);
	}

	// /////////////////////////////////////////////////////////////////////////////
	/**
	 * 创建密匙组，并将公匙，私匙放入到指定文件中
	 *
	 * 默认放入publicKey.dat文件中
	 */
	public static void createPairKey() {
		try {
			// 根据特定的算法一个密钥对生成器
			KeyPairGenerator keygen = KeyPairGenerator
					.getInstance(KEY_ALGORITHM_DSA);
			// 加密随机数生成器 (RNG)
			SecureRandom random = new SecureRandom();
			// 重新设置此随机对象的种子
			random.setSeed(1000);
			// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
			keygen.initialize(512, random);// keygen.initialize(512);
			// 生成密钥组
			KeyPair keys = keygen.generateKeyPair();
			// 得到公匙
			PublicKey pubkey = keys.getPublic();
			// 得到私匙
			PrivateKey prikey = keys.getPrivate();
			// 将公匙私匙写入到文件当中
			doObjToFile("publicKey.dat", new Object[] { prikey, pubkey });
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用私匙对信息进行签名 把签名后的信息放入到指定的文件中
	 *
	 * @param info
	 *            要签名的信息
	 * @param signfile
	 *            存入的文件
	 */
	public static void signToInfo(String info, String signfile) {
		// 从文件当中读取私匙
		PrivateKey myprikey = (PrivateKey) getObjFromFile("publicKey.dat", 1);
		// 从文件中读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile("publicKey.dat", 2);
		try {
			// Signature 对象可用来生成和验证数字签名
			Signature signet = Signature.getInstance(KEY_ALGORITHM_DSA);
			// 初始化签署签名的私钥
			signet.initSign(myprikey);
			// 更新要由字节签名或验证的数据
			signet.update(info.getBytes());
			// 签署或验证所有更新字节的签名，返回签名
			byte[] signed = signet.sign();
			// 将数字签名,公匙,信息放入文件中
			doObjToFile(signfile, new Object[] { signed, mypubkey, info });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取数字签名文件 根据公匙，签名，信息验证信息的合法性
	 *
	 * @return true 验证成功 false 验证失败
	 */
	public static boolean validateSign(String signfile) {
		// 读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
		// 读取签名
		byte[] signed = (byte[]) getObjFromFile(signfile, 1);
		// 读取信息
		String info = (String) getObjFromFile(signfile, 3);
		try {
			// 初始一个Signature对象,并用公钥和签名进行验证
			Signature signetcheck = Signature.getInstance(KEY_ALGORITHM_DSA);
			// 初始化验证签名的公钥
			signetcheck.initVerify(mypubkey);
			// 使用指定的 byte 数组更新要签名或验证的数据
			signetcheck.update(info.getBytes());
			// 验证传入的签名
			return signetcheck.verify(signed);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 字符串转字节数组
	 *
	 * @param s
	 * @return
	 */
	private byte[] str2ByteArray(String s) {
		int byteArrayLength = s.length() / 2;
		byte[] b = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
					.intValue();
			b[i] = b0;
		}

		return b;
	}

	/**
	 * 将二进制转化为16进制字符串
	 *
	 * @param b
	 *            二进制字节数组
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder hex = new StringBuilder();
		for (int n = 0; n < b.length; n++) {
			String stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hex.append("0");
			}
			hex.append(stmp.toUpperCase());
		}
		return hex.toString();
	}

	/**
	 * 十六进制字符串转化为2进制
	 *
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 *
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定的对象写入指定的文件
	 *
	 * @param file
	 *            指定写入的文件
	 * @param objs
	 *            要写入的对象
	 */
	public static void doObjToFile(String file, Object[] objs) {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			for (int i = 0; i < objs.length; i++) {
				oos.writeObject(objs[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返回在文件中指定位置的对象
	 *
	 * @param file
	 *            指定的文件
	 * @param i
	 *            从1开始
	 * @return
	 */
	public static Object getObjFromFile(String file, int i) {
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			for (int j = 0; j < i; j++) {
				obj = ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	// End

	// TODO 非对称加密算法DH算法组件 Begin
	/**
	 * 初始化甲方密钥
	 *
	 * @param algorithm
	 *            加密算法 {DH,RSA,DSA}
	 * @return Map 甲方密钥的Map
	 * */
	public static Map<String, Object> initKey(String algorithm)
			throws Exception {
		int keysize = KEY_SIZE_512;
		if (algorithm == null || "".equals(algorithm.trim())) {
			algorithm = KEY_ALGORITHM_RSA;
		}

		if (KEY_ALGORITHM_DSA.equalsIgnoreCase(algorithm)) {
			keysize = KEY_SIZE_1024;
		}

		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(algorithm);
		// 初始化密钥生成器
		keyPairGenerator.initialize(keysize);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 甲方公钥
		PublicKey publicKey = keyPair.getPublic();
		// 甲方私钥
		PrivateKey privateKey = keyPair.getPrivate();
		// 将密钥存储在map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;

	}

	/**
	 * 初始化乙方密钥
	 *
	 * @param key
	 *            甲方密钥（这个密钥是通过第三方途径传递的）
	 * @return Map 乙方密钥的Map
	 * */
	public static Map<String, Object> initKey(byte[] key) throws Exception {
		// 解析甲方的公钥
		// 转换公钥的材料
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_DH);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 由甲方的公钥构造乙方密钥
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(keyFactory.getAlgorithm());
		// 初始化密钥生成器
		keyPairGenerator.initialize(dhParamSpec);
		// 产生密钥对
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		// 乙方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		// 乙方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
		// 将密钥存储在Map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 加密
	 *
	 * @param data待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成本地密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM_AES);
		// 数据加密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成本地密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM_AES);
		// 数据解密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	/**
	 * 构建密钥
	 *
	 * @param publicKey
	 *            公钥
	 * @param privateKey
	 *            私钥
	 * @return byte[] 本地密钥
	 * */
	public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey)
			throws Exception {
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_DH);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 初始化私钥
		// 密钥材料转换
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		// 产生私钥
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory
				.getAlgorithm());
		// 初始化
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		// 生成本地密钥
		SecretKey secretKey = keyAgree.generateSecret(KEY_ALGORITHM_AES);
		return secretKey.getEncoded();
	}

	// TODO RSA Begin
	/**
	 * 私钥加密
	 *
	 * @param data待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key)
			throws Exception {

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 *
	 * @param data待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * */
	public static byte[] encryptByPublicKeyRSA(byte[] data, byte[] key)
			throws Exception {

		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * */
	public static byte[] decryptByPrivateKeyRSA(byte[] data, byte[] key)
			throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key)
			throws Exception {

		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	// RSA End
	// 非对称加密算法DH算法组件 End

	// TODO 非对称加密算法ElGamal算法组件 Begin

	/**
	 * 初始化密钥对
	 *
	 * @return Map 甲方密钥的Map
	 * */
	public static Map<String, Object> initKey() throws Exception {
		// 加入对BouncyCastle支持
		Security.addProvider(new BouncyCastleProvider());
		AlgorithmParameterGenerator apg = AlgorithmParameterGenerator
				.getInstance(KEY_ALGORITHM_ELGAMAL);
		// 初始化参数生成器
		apg.init(KEY_SIZE_256);
		// 生成算法参数
		AlgorithmParameters params = apg.generateParameters();
		// 构建参数材料
		DHParameterSpec elParams = (DHParameterSpec) params
				.getParameterSpec(DHParameterSpec.class);

		// 实例化密钥生成器
		KeyPairGenerator kpg = KeyPairGenerator
				.getInstance(KEY_ALGORITHM_ELGAMAL);
		// 初始化密钥对生成器
		kpg.initialize(elParams, new SecureRandom());
		// 生成密钥对
		KeyPair keyPair = kpg.generateKeyPair();
		// 甲方公钥
		PublicKey publicKey = keyPair.getPublic();
		// 甲方私钥
		PrivateKey privateKey = keyPair.getPrivate();
		// 将密钥存储在map中
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;

	}

	/**
	 * 公钥加密
	 *
	 * @param data待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key)
			throws Exception {

		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_ELGAMAL);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key)
			throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_ELGAMAL);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 私钥
	 * */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 *            密钥map
	 * @return byte[] 公钥
	 * */
	public static byte[] getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	// 非对称加密算法ElGamal算法组件 End

	// TODO 经典的数字签名算法RSA,DSA Begin
	/**
	 * 签名
	 *
	 * @param algorithm
	 *            加密算法 {RSA,DSA}
	 * @param data待签名数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 数字签名
	 * */
	public static byte[] sign(String algorithm, byte[] data, byte[] privateKey)
			throws Exception {

		String transformation;
		if (KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
			transformation = KEY_ALGORITHM_RSA_MD5WITHRSA;
		} else {// if (KEY_ALGORITHM_DSA.equalsIgnoreCase(algorithm)){
			transformation = KEY_ALGORITHM_SHA1WITHDSA;
		}

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		// 生成私钥
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(transformation);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 校验数字签名
	 *
	 * @param algorithm
	 *            加密算法 {RSA,DSA}
	 * @param data
	 *            待校验数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return boolean 校验成功返回true，失败返回false
	 * */
	public static boolean verify(String algorithm, byte[] data,
			byte[] publicKey, byte[] sign) throws Exception {
		String transformation;
		if (KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
			transformation = KEY_ALGORITHM_RSA_MD5WITHRSA;
		} else {// if (KEY_ALGORITHM_DSA.equalsIgnoreCase(algorithm)){
			transformation = KEY_ALGORITHM_SHA1WITHDSA;
		}

		// 转换公钥材料
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(transformation);
		// 初始化Signature
		signature.initVerify(pubKey);
		// 更新
		signature.update(data);
		// 验证
		return signature.verify(sign);
	}
	// 经典的数字签名算法RSA,DSA End
}

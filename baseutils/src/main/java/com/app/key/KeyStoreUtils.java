package com.app.key;

import android.util.Base64;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * The type Key store utils.
 *
 * @author Noah.冯 QQ:1066537317
 * @name： FingerprintLoader
 * @package： com.evil.fingerprintloader
 * @time 2017 /8/9 0009
 * @desc： 一个加密相关工具类
 * @since 1.1
 */
public final class KeyStoreUtils{

    private KeyStoreUtils(){
    }

    /**
     * 获取加密解密key
     *
     * @param keys
     *         秘钥
     * @param opmode
     *         the opmode
     * @param ProType
     *         加密算法
     * @param input
     *         原文文本
     *
     * @return the byte [ ]
     *
     * @author: Noah QQ:1066537317
     * @version: 1.0
     * @desc: 加密算法随机加密解密
     */
    public static byte[] key(Key keys,@CipherType int opmode,
                             @ProviderType String ProType,byte[] input){
        if(input == null){
            return new byte[0];
        }
        try{
            //1,得到cipher 对象（可翻译为密码器或密码系统）
            Cipher cipher = Cipher.getInstance(ProType);
            //2,设置操作模式（加密/解密）
            cipher.init(opmode,keys);
            //3,执行操作
            byte[] result = cipher.doFinal(input);
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 加密算法
     *
     * @param keys
     *         秘钥
     * @param ProType
     *         加密算法
     * @param input
     *         原文文本
     *
     * @return the byte [ ]
     *
     * @author: Noah QQ:1066537317
     * @version: 1.0
     * @desc: 加密
     */
    public static byte[] encrypt(Key keys,@ProviderType String ProType,
                                 byte[] input){
        return key(keys,Cipher.ENCRYPT_MODE,ProType,input);
    }


    /**
     * 加密算法
     *
     * @param keys
     *         the keys
     * @param algorithm
     *         the algorithm e.g:AES/DES/RSA
     * @param ProType
     *         the pro type
     * @param input
     *         the input
     *
     * @return the byte [ ]
     */
    public static byte[] encrypt(String keys,String algorithm,
                                 @ProviderType String ProType,byte[] input){
        SecretKey keySpec = new SecretKeySpec(keys.getBytes(),algorithm);
        byte[] bytes = encrypt(keySpec,ProType,input);
        return bytes;
    }


    /**
     * 加密算法
     *
     * @param keys
     *         the keys
     * @param algorithm
     *         the algorithm e.g:AES/DES/RSA
     * @param ProType
     *         the pro type
     * @param input
     *         the input
     *
     * @return the string
     */
    public static String encrypt(String keys,String algorithm,
                                 @ProviderType String ProType,String input){
        SecretKey keySpec = new SecretKeySpec(keys.getBytes(),algorithm);
        byte[] bytes = encrypt(keySpec,ProType,input.getBytes());
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }


    /**
     * 解密算法
     *
     * @param key
     *         the keys
     * @param ProType
     *         the pro type
     * @param input
     *         the input
     *
     * @return the byte [ ]
     */
    public static byte[] decrypt(Key key,@ProviderType String ProType,
                                 byte[] input){
        return key(key,Cipher.DECRYPT_MODE,ProType,input);
    }

    /**
     * 解密算法
     *
     * @param keys
     *         the keys
     * @param algorithm
     *         the algorithm e.g:AES/DES/RSA
     * @param ProType
     *         the pro type
     * @param input
     *         the input
     *
     * @return the byte [ ]
     */
    public static byte[] decrypt(String keys,String algorithm,
                                 @ProviderType String ProType,byte[] input){
        //2,创建秘钥
        SecretKey keySpec = new SecretKeySpec(keys.getBytes(),algorithm);
        //设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
        byte[] bytes = decrypt(keySpec,ProType,input);
        return bytes;
    }


    /**
     * 解密算法
     *
     * @param keys
     *         the keys
     * @param algorithm
     *         the algorithm e.g:AES/DES/RSA
     * @param ProType
     *         the pro type
     * @param input
     *         the input
     *
     * @return the string
     */
    public static String decrypt(String keys,String algorithm,
                                 @ProviderType String ProType,String input){
        byte[] decode = Base64.decode(input,Base64.DEFAULT);
        //2,创建秘钥
        SecretKey keySpec = new SecretKeySpec(keys.getBytes(),algorithm);
        //设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
        byte[] bytes = decrypt(keySpec,ProType,decode);
        return new String(bytes);
    }

    /**
     * 获取随机秘钥
     *
     * @param algorithm
     *         e.g:AES/DES/RSA
     *
     * @return 秘钥 key
     */
    public static Key getKey(String algorithm){
        //生成随机秘钥
        SecretKey secretKey = null;
        try{
            secretKey = KeyGenerator.getInstance(algorithm).generateKey();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return secretKey;
    }

    /**
     * 创建Key
     *
     * @param algorithm
     *         e.g.AES
     * @param keysize
     *         设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
     *
     * @return secret key
     *
     * @throws Exception
     *         the exception
     */
    public static SecretKey createKey(String algorithm,int keysize)
            throws Exception{
        //对称key即SecretKey创建和导入，假设双方约定使用DES算法来生成对称密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        //设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
        keyGenerator.init(keysize);
        //生成SecretKey对象，即创建一个对称密钥，并获取二进制的书面表达
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    /**
     * 创建Key
     *
     * @param algorithm
     *         the algorithm
     * @param keysize
     *         the keysize
     *
     * @return byte [ ]
     *
     * @throws Exception
     *         the exception
     */
    public static byte[] createKey1(String algorithm,int keysize)
            throws Exception{
        SecretKey secretKey = createKey(algorithm,keysize);
        byte[] keyData = secretKey.getEncoded();
        return keyData;
    }

    /**
     * 创建Key
     *
     * @param algorithm
     *         the algorithm
     * @param keysize
     *         the keysize
     *
     * @return string string
     *
     * @throws Exception
     *         the exception
     */
    public static String createKey2(String algorithm,int keysize)
            throws Exception{
        byte[] key1 = createKey1(algorithm,keysize);
        //日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
        String keyInBase64 = Base64.encodeToString(key1,Base64.DEFAULT);
        return keyInBase64;
    }

    /**
     * 解密key
     *
     * @param keyInBase64
     *         the key in base 64
     * @param algorithm
     *         the algorithm
     *
     * @return secret key
     *
     * @throws Exception
     *         the exception
     */
    public static SecretKey decodeKey(byte[] keyInBase64,String algorithm)
            throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(keyInBase64,algorithm);
        //创建对称Key导入用的SecretKeyFactory
        SecretKeyFactory secretKeyFactory = SecretKeyFactory
                .getInstance(algorithm);
        //根据KeySpec还原Key对象，即把key的书面表达式转换成了Key对象
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey;
    }

    /**
     * 解密key
     *
     * @param keyInBase64
     *         the key in base 64
     * @param algorithm
     *         the algorithm
     *
     * @return secret key
     *
     * @throws Exception
     *         the exception
     */
    public static SecretKey decodeKey(String keyInBase64,String algorithm)
            throws Exception{
        //假设对方收到了base64编码后的密钥，首先要得到其二进制表达式，用二进制数组构造KeySpec对象。对称key
        // 使用SecretKeySpec类
        byte[] receivedKeyData = Base64.decode(keyInBase64,Base64.DEFAULT);
        return decodeKey(receivedKeyData,algorithm);
    }

    /**
     * Aes encrypt string.
     * AES 加密
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the string
     */
    public static String AES_Encrypt(String key,String text){
        return encrypt(key,"AES",ProviderType.AES,text);
    }

    /**
     * Aes encrypt 1 byte [ ].
     * AES 加密
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the byte [ ]
     */
    public static byte[] AES_Encrypt1(String key,String text){
        return encrypt(key,"AES",ProviderType.AES,text.getBytes());
    }


    /**
     * Aes decrypt string.
     *  AES 解密
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the string
     */
    public static String AES_Decrypt(String key,String text){
        return decrypt(key,"AES",ProviderType.AES,text);
    }

    /**
     * Aes decrypt 1 byte [ ].
     *AES 解密
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the byte [ ]
     */
    public static byte[] AES_Decrypt1(String key,String text){
        return decrypt(key,"AES",ProviderType.AES,text.getBytes());
    }

    /**
     * Des encrypt string.
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the string
     */
    public static String DES_Encrypt(String key,String text){
        return encrypt(key,"DES",ProviderType.DES,text);
    }

    /**
     * Des encrypt 1 byte [ ].
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the byte [ ]
     */
    public static byte[] DES_Encrypt1(String key,String text){
        return encrypt(key,"DES",ProviderType.DES,text.getBytes());
    }


    /**
     * Des decrypt string.
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the string
     */
    public static String DES_Decrypt(String key,String text){
        return decrypt(key,"DES",ProviderType.DES,text);
    }

    /**
     * Des decrypt 1 byte [ ].
     *
     * @param key
     *         the key
     * @param text
     *         the text
     *
     * @return the byte [ ]
     */
    public static byte[] DES_Decrypt1(String key,String text){
        return decrypt(key,"DES",ProviderType.DES,text.getBytes());
    }


    /**
     * 初始化密钥
     *
     * @param keySize
     *         the key size
     *
     * @return map map
     *
     * @throws Exception
     *         the exception
     */
    public static Map<String,Object> initRSAKey(int keySize) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String,Object> keyMap = new HashMap<>(2);
        keyMap.put("PublicKey",publicKey);
        keyMap.put("PrivateKey",privateKey);
        return keyMap;
    }

    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap
     *         the key map
     *
     * @return 公钥 public key
     *
     * @throws Exception
     *         the exception
     */
    public static String getPublicKey(Map<String,Object> keyMap)
            throws Exception{
        Key key = (Key) keyMap.get("PublicKey");
        return Base64.encodeToString(key.getEncoded(),Base64.DEFAULT);
    }

    /**
     * 取得私钥，并转化为String类型
     *
     * @param keyMap
     *         the key map
     *
     * @return 私钥 private key
     *
     * @throws Exception
     *         the exception
     */
    public static String getPrivateKey(Map<String,Object> keyMap)
            throws Exception{
        Key key = (Key) keyMap.get("PrivateKey");
        return Base64.encodeToString(key.getEncoded(),Base64.DEFAULT);
    }

    /**
     * 用私钥加密
     *
     * @param data
     *         加密数据
     * @param privateKey
     *         密钥
     *
     * @return string byte [ ]
     *
     * @throws Exception
     *         the exception
     */
    public static byte[] encryptByPrivateKey(String data,byte[] privateKey)
            throws Exception{
        // 解密密钥
        //        byte[] keyBytes = Base64.decode(privateKey.getBytes(),
        // Base64.DEFAULT);
        // 取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] resultBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return resultBytes;
    }

    /**
     * 用私钥加密
     *
     * @param data
     *         加密数据
     * @param privateKey
     *         密钥
     *
     * @return string string
     *
     * @throws Exception
     *         the exception
     */
    public static String encryptByPrivateKey(String data,String privateKey)
            throws Exception{
        // 解密密钥
        byte[] keyBytes = Base64.decode(privateKey.getBytes(),Base64.DEFAULT);
        byte[] bytes = encryptByPrivateKey(data,keyBytes);
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    /**
     * 用私钥解密
     *
     * @param data
     *         加密数据
     * @param privateKey
     *         密钥
     *
     * @return string byte [ ]
     *
     * @throws Exception
     *         the exception
     */
    public static byte[] decryptByPrivateKey(String data,byte[] privateKey)
            throws Exception{
        // 对私钥解密
        //        byte[] keyBytes = Base64.decode(privateKey.getBytes(),
        // Base64.DEFAULT);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] dataBytes = Base64.decode(data.getBytes(),Base64.DEFAULT);
        byte[] resultBytes = cipher.doFinal(dataBytes);
        return resultBytes;
    }

    /**
     * 用私钥解密
     *
     * @param data
     *         加密数据
     * @param privateKey
     *         密钥
     *
     * @return string string
     *
     * @throws Exception
     *         the exception
     */
    public static String decryptByPrivateKey(String data,String privateKey)
            throws Exception{
        // 对私钥解密
        byte[] keyBytes = Base64.decode(privateKey.getBytes(),Base64.DEFAULT);
        byte[] bytes = decryptByPrivateKey(data,keyBytes);
        return new String(bytes,"UTF-8");
    }

    /**
     * 用公钥加密
     *
     * @param data
     *         加密数据
     * @param publicKey
     *         密钥
     *
     * @return string byte [ ]
     *
     * @throws Exception
     *         the exception
     */
    public static byte[] encryptByPublicKey(String data,byte[] publicKey)
            throws Exception{
        //对公钥解密
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] resultBytes = cipher.doFinal(data.getBytes());
        return resultBytes;
    }

    /**
     * 用公钥加密
     *
     * @param data
     *         加密数据
     * @param publicKey
     *         密钥
     *
     * @return string string
     *
     * @throws Exception
     *         the exception
     */
    public static String encryptByPublicKey(String data,String publicKey)
            throws Exception{
        //对公钥解密
        byte[] keyBytes = Base64.decode(publicKey.getBytes(),Base64.DEFAULT);
        //取公钥
        byte[] bytes = encryptByPublicKey(data,keyBytes);
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }


    /**
     * 用公钥解密
     *
     * @param data
     *         加密数据
     * @param publicKey
     *         密钥
     *
     * @return string byte [ ]
     *
     * @throws Exception
     *         the exception
     */
    public static byte[] decryptByPublicKey(String data,byte[] publicKey)
            throws Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] dataBytes = Base64.decode(data.getBytes(),Base64.DEFAULT);
        byte[] resultBytes = cipher.doFinal(dataBytes);
        return resultBytes;
    }

    /**
     * 用公钥解密
     *
     * @param data
     *         加密数据
     * @param publicKey
     *         密钥
     *
     * @return string string
     *
     * @throws Exception
     *         the exception
     */
    public static String decryptByPublicKey(String data,String publicKey)
            throws Exception{
        byte[] keyBytes = Base64.decode(publicKey.getBytes(),Base64.DEFAULT);
        byte[] resultBytes = decryptByPublicKey(data,keyBytes);
        return new String(resultBytes,"UTF-8");
    }

    /**
     * The interface Cipher type.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Cipher.ENCRYPT_MODE,Cipher.DECRYPT_MODE,Cipher.WRAP_MODE,Cipher
            .UNWRAP_MODE})
    public @interface CipherType{}
}

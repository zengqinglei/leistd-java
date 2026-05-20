package com.mysoft.permission.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.MessageFormat;
import java.util.Base64;


@Slf4j
public class AuthUtils {
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 密钥算法
     */
    private static final String DES_ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String DES_CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";


    /**
     * DES加密字符串
     *
     * @param encryptKey 加密密码，长度不能够小于8位
     * @param content    待加密字符串
     * @return 加密后内容
     */
    public static String encryptDES(String content, String encryptKey) {
        if (!StringUtils.hasLength(encryptKey)
                || !StringUtils.hasLength(content)
                || encryptKey.length() < 8) {
            log.error("加密失败，key不能小于8位或者传入加密字符串为空");
            return content;
        }
        try {
            Key secretKey = generateKey(encryptKey);
            Cipher cipher = Cipher.getInstance(DES_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(encryptKey.getBytes(CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(content.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(bytes);

        } catch (Exception e) {
            String error = MessageFormat.format("加密失败：{0}", e.getMessage());
            log.error(error, e);
            return content;
        }
    }

    /**
     * DES解密字符串
     *
     * @param encryptKey 解密密码，长度不能够小于8位
     * @param content    待解密字符串
     * @return 解密后内容
     */
    public static String decryptDES(String encryptKey, String content) {
        if (!StringUtils.hasLength(encryptKey)
                || !StringUtils.hasLength(content)
                || encryptKey.length() < 8) {
            log.error("解密失败，key不能小于8位或者传入加密字符串为空");
            return content;
        }
        try {
            Key secretKey = generateKey(encryptKey);
            Cipher cipher = Cipher.getInstance(DES_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(encryptKey.getBytes(CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(content.getBytes(CHARSET))), CHARSET);
        } catch (Exception e) {
            String error = MessageFormat.format("加密失败：{0}", e.getMessage());
            log.error(error, e);
            return content;
        }
    }

    /**
     * 生成key
     */
    private static Key generateKey(String password) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(dks);
    }


    public static String generateTempPassword() {
        return RandomStringUtils.randomAlphabetic(8);
    }

}

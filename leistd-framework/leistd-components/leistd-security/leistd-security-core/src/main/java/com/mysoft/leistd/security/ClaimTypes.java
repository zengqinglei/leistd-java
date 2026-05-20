package com.mysoft.leistd.security;

public class ClaimTypes {
    /**
     * ClientId
     */
    public static String ClientId = "client_id";
    /**
     * Scope
     */
    public static String Scope = "scope";

    /**
     * 授权时间
     */
    public static String IssuedAt = "iat";

    /**
     * 在此之前不能被处理的时间
     */
    public static String NotBefore = "nbf";

    /**
     * 过期时间
     */
    public static String Expiration = "exp";

    /**
     * 用户名
     */
    public static String UserName = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";

    /**
     * 用户Id
     */
    public static String UserId = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";

    /**
     * 用户姓名
     */
    public static String Name = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname";

    /**
     * 邮箱地址
     */
    public static String Email = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress";

    /**
     * 电话号码
     */
    public static String PhoneNumber = "phone_number";

    /**
     * 角色
     */
    public static String Role = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";

}

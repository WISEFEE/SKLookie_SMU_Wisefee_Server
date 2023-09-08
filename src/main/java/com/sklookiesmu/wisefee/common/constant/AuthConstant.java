package com.sklookiesmu.wisefee.common.constant;

/**
 * 인증 관련 Constant
 */
public class AuthConstant {

    /**
     * Member AUTH Type : 손님
     */
    public static final String ACCOUNT_TYPE_CONSUMER = "CONSUMER";

    /**
     * Member AUTH Type : 매장
     */
    public static final String ACCOUNT_TYPE_SELLER = "SELLER";

    /**
     * Member Account Type : 일반 로그인
     */
    public static final String AUTH_TYPE_COMMON = "Common";

    /**
     * Member Account Type : 소셜 로그인 (구글)
     */
    public static final String AUTH_TYPE_GOOGLE = "Google";



    /**
     * API Auth Role : 공통
     */
    public static final String AUTH_ROLE_COMMON_USER = "hasRole('ROLE_CONSUMER') or hasRole('ROLE_SELLER')";

    /**
     * API Auth Role : 손님
     */
    public static final String AUTH_ROLE_CONSUMER = "hasRole('ROLE_CONSUMER')";

    /**
     * API Auth Role : 매장
     */
    public static final String AUTH_ROLE_SELLER = "hasRole('ROLE_SELLER')";

    /**
     * Access Token 만료시간 : 3일
     */
    public static final int ACCESS_TOKEN_EXPIRE_TIME = 86400000 * 3;


    /**
     * OAuth Password : 소셜 로그인의 경우 패스워드를 사용하지 않으므로 임의의 값
     */
    public static final String OAUTH_PASSWORD = "qaqhcbhbwjbq13s23!";

    public static final String OAUTH_GOOGLE_AUTHENTICATION_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo";



}

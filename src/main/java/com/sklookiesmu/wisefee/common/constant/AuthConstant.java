package com.sklookiesmu.wisefee.common.constant;

/**
 * 인증 관련 Constant
 */
public class AuthConstant {

    /**
     * Member Account Type : 손님
     */
    public static final String ACCOUNT_TYPE_CONSUMER = "CONSUMER";

    /**
     * Member Account Type : 매장
     */
    public static final String ACCOUNT_TYPE_SELLER = "SELLER";

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



}

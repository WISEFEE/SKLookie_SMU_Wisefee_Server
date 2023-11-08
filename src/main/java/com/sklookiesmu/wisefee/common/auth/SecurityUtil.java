package com.sklookiesmu.wisefee.common.auth;

import com.sklookiesmu.wisefee.common.auth.custom.CustomUserDetail;
import com.sklookiesmu.wisefee.common.exception.global.AuthForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * [인증 유저의 이메일]
     * 현재 로그인한 유저의 이메일 정보를 얻어옴
     * @return [String] Email
     */
    public static String getCurrentMemberEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new AuthForbiddenException("인증정보를 확인할 수 없습니다.");
        }
        return authentication.getName();
    }


    /**
     * [인증 유저의 PK]
     * 현재 로그인한 유저의 PK를 얻어옴
     * @return [String] PK
     */
    public static Long getCurrentMemberPk() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new AuthForbiddenException("인증정보를 확인할 수 없습니다.");
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Long userPk = userDetails.getMemberId();
        return userPk;
    }
}
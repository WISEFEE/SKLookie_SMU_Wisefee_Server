package com.sklookiesmu.wisefee.common.auth.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * [커스텀 User 클래스]
 * 인증에서 사용되는 User 클래스에서 일부 속성을 추가하여 재정의한 CustomUser
 */
public class CustomUserDetail extends User {

    //username(Email), password, authorities
    private final String nickname;  //닉네임
    private final Long memberId;    //PK

    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities,
                            String nickname, long memberId) {
        super(
                username, password, authorities
        );
        this.nickname = nickname;
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getUserId() {
        return memberId;
    }
}

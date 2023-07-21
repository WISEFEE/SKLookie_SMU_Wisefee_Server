package com.sklookiesmu.wisefee.common.auth.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetail extends User {
    private final String nickname;
    private final Long memberId;

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

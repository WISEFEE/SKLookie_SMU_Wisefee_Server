package com.sklookiesmu.wisefee.common.auth.custom;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * [커스텀 User 클래스]
 * 인증에서 사용되는 User 클래스에서 일부 속성을 추가하여 재정의한 CustomUser
 */
@Getter
public class CustomUserDetail extends User {

    //username(Email), password, authorities
    private final String nickname;  //닉네임

    private final String authType;  //계정타입
    private final Long memberId;    //PK

    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities,
                            String nickname, long memberId, String authType) {
        super(
                username, password, authorities
        );
        this.nickname = nickname;
        this.memberId = memberId;
        this.authType = authType;
    }


}

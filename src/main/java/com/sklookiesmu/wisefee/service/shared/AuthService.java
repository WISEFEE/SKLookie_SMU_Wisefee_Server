package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    /**
     * [디폴트 회원 로그인]
     * Email과 Password 기반 로그인
     * @param [id 회원 엔티티 PK]
     * @return [TokenInfo]
     */
    public abstract TokenInfoDto login(String email, String password, String firebaseToken);

    public abstract TokenInfoDto refresh(Authentication auth, String jwt);

}

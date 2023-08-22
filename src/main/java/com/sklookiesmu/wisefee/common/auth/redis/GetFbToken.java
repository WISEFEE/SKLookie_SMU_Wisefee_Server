package com.sklookiesmu.wisefee.common.auth.redis;

import com.sklookiesmu.wisefee.domain.FbToken;
import com.sklookiesmu.wisefee.repository.redis.AuthRepositoryWithRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetFbToken {
    private final AuthRepositoryWithRedis authRepositoryWithRedis;

    @Autowired
    public GetFbToken(AuthRepositoryWithRedis authRepositoryWithRedis) {
        this.authRepositoryWithRedis = authRepositoryWithRedis;
    }


    public List<FbToken> getFbTokenByMember(Long memberPK) {
        Optional<List<FbToken>> fbTokens = authRepositoryWithRedis.findAllBymemberPK(memberPK);
        if (fbTokens.isPresent()) {
            return fbTokens.get();
        } else {
            throw new RuntimeException("현재 서버에서 memberPK : " + memberPK + "의 토큰을 찾을 수 없습니다.");
        }

    }


}

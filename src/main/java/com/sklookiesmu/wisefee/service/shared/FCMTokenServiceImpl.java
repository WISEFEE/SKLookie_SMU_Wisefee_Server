package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import com.sklookiesmu.wisefee.repository.redis.AuthRepositoryWithRedis;
import com.sklookiesmu.wisefee.service.shared.interfaces.FCMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCMTokenServiceImpl implements FCMTokenService {
    private final AuthRepositoryWithRedis authRepositoryWithRedis;


    /**
     * [해당 유저의 FCM 토큰 리스트 얻어오기]
     * 회원 PK를 통하여, 현재 로그인 된 기기들의 FCM 토큰 리스트를 얻어온다. 없다면 Null을 반환한다.
     * @param [memberPK 회원 PK]
     * @return [FCM 토큰 리스트 || Null]
     */
    public List<String> getFbTokenByMember(Long memberPK) {
        Optional<List<FCMToken>> fbTokens = authRepositoryWithRedis.findAllBymemberPK(memberPK);
        // fbTokens의 요소가 적어도 1개 이상 있는지 검사
        if (fbTokens.isPresent() && !fbTokens.get().isEmpty()) {
            // fbTokens가 존재하고 비어있지 않으면 해당 요소들을 반환
            List<String> fireBaseTokens = new ArrayList<>();

            for (FCMToken token : fbTokens.get()) {
                String fireBaseToken = token.getFireBaseToken();
                if (fireBaseToken != null && !fireBaseToken.isEmpty()) {
                    fireBaseTokens.add(fireBaseToken);
                }
            }

            if (!fireBaseTokens.isEmpty()) {
                return fireBaseTokens;
            } else {
                return null;
            }
        } else {
            // throw new RuntimeException("현재 서버에서 memberPK : " + memberPK + "의 토큰을 찾을 수 없습니다.");
            // 없으면 없는대로 알림 보내지 않고 처리하기
            return null;
        }
    }


}

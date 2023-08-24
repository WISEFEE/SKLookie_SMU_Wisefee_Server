package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;

import java.util.List;


public interface FCMTokenService {


    /**
     * [해당 유저의 FCM 토큰 리스트 얻어오기]
     * 회원 PK를 통하여, 현재 로그인 된 기기들의 FCM 토큰 리스트를 얻어온다. 없다면 Null을 반환한다.
     * @param [memberPK 회원 PK]
     * @return [FCM 토큰 리스트 || Null]
     */
    public abstract List<FCMToken> getFbTokenByMember(Long memberPK);

}

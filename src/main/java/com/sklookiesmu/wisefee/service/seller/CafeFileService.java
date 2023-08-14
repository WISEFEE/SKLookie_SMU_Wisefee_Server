package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.File;
import com.sklookiesmu.wisefee.domain.Subscribe;
import com.sklookiesmu.wisefee.dto.seller.UpdateCafeRequestDto;

import java.util.List;

public interface CafeFileService {

    /**
     * [매장 사진 추가]
     * 매장에 새로운 사진을 추가한다.
     * @param cafeId 매장 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     */
    public abstract Long addCafeImage(Long cafeId, Long fileId);


    /**
     * [매장 사진 삭제]
     * 매장에 기존 사진을 삭제한다
     * @param cafeId 매장 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     */
    public abstract Long removeCafeImage(Long cafeId, Long fileId);





}

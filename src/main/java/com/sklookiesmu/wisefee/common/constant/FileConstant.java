package com.sklookiesmu.wisefee.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 인증 관련 Constant
 */
public class FileConstant {


    /**
     * 최대 파일 용량 : 10MB
     */
    public static final int MAX_FILE_SIZE = 10 * 1024 * 1024;


    /**
     * 파일 확장자 허용 리스트 : 이미지
     */
    public static final List<String> FILE_MIME_TYPE_LIST_IMG = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/bmp",
            "image/tiff", "image/webp", "image/x-icon", "image/svg+xml"
    );



    /**
     * 파일 세부 인포 : 사용X
     */
    public static final String FILE_INFO_NO_USE = "NOT_USE";

    /**
     * 파일 세부 인포 : 매장 이미지
     */
    public static final String FILE_INFO_CAFE_IMAGE = "CAFE_IMG";

    /**
     * 파일 세부 인포 : 상품 이미지
     */
    public static final String FILE_INFO_PRODUCT_IMAGE = "PRODUCT_IMG";

    /**
     * 파일 세부 인포 : 삭제
     */
    public static final String FILE_INFO_DELETE = "DELETE";


    /**
     * 카페 이미지 등록 최대 개수
     */
    public static final int MAX_CAFE_IMAGE_CNT = 10;

    /**
     * 상품 이미지 등록 최대 개수
     */
    public static final int MAX_PRODUCT_IMAGE_CNT = 5;


}

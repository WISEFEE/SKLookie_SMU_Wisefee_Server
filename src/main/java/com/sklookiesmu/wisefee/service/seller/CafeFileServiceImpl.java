package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.constant.FileConstant;
import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.common.exception.global.PreconditionFailException;
import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.File;
import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.dto.seller.CafeAndImageCountDto;
import com.sklookiesmu.wisefee.dto.seller.ProductAndImageCountDto;
import com.sklookiesmu.wisefee.repository.FileRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeFileServiceImpl implements CafeFileService {

    private final CafeRepository cafeRepository;

    private final ProductRepository productRepository;
    private final FileRepository fileRepository;

    /**
     * [매장 사진 추가]
     * 매장에 새로운 사진을 추가한다.
     * @param cafeId 매장 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     * @throws IllegalStateException 매장에 최대 등록 가능한 이미지 개수를 초과하는 경우
     */
    @Transactional()
    public Long addCafeImage(Long cafeId, Long fileId){
        CafeAndImageCountDto cafeAndImgCnt = cafeRepository.findCafeAndImageCountById(cafeId);
        Cafe cafe = cafeAndImgCnt.getCafe();
        int count = cafeAndImgCnt.getImageCount();
        File file = fileRepository.findById(fileId);

        if(count >= FileConstant.MAX_CAFE_IMAGE_CNT){
            throw new PreconditionFailException(String.format("매장에 최대 등록 가능한 이미지 개수는 %d개입니다.", FileConstant.MAX_CAFE_IMAGE_CNT));
        }
        if (cafe != null && file != null) {
            if(!file.getFileInfo().equals(FileConstant.FILE_INFO_NO_USE)){
                throw new NoSuchElementFoundException("이미 사용중이거나 삭제된 이미지입니다.");
            }
            file.setFileInfo(FileConstant.FILE_INFO_CAFE_IMAGE);
            file.setCafe(cafe);
        }
        else{
            throw new NoSuchElementFoundException("존재하지 않는 매장이거나 이미지입니다.");
        }
        return 1L;
    }


    /**
     * [매장 사진 삭제]
     * 매장에 기존 사진을 삭제한다
     * @param cafeId 매장 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     */
    @Transactional()
    public Long removeCafeImage(Long cafeId, Long fileId){
        Cafe cafe = cafeRepository.findById(cafeId);
        File file = fileRepository.findById(fileId);

        if (cafe != null && file != null) {
            file.setFileInfo(FileConstant.FILE_INFO_DELETE);
            file.setDeleted(true);
            file.setCafe(null);
        }
        else{
            throw new NoSuchElementFoundException("존재하지 않는 매장이거나 이미지입니다.");
        }
        return 1L;
    }


    /**
     * [상품 사진 추가/변경]
     * 상품사진을 신규추가하거나 대체한다.
     * @param productId 상품 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 상품이거나 이미지일 경우 예외처리
     */
    @Transactional()
    public Long addProductImage(Long productId, Long fileId){
        ProductAndImageCountDto productAndImgCnt = productRepository.findProductAndImageCountById(productId);
        Product product = productAndImgCnt.getProduct();
        int count = productAndImgCnt.getImageCount();
        File file = fileRepository.findById(fileId);

        if(count >= FileConstant.MAX_PRODUCT_IMAGE_CNT){
            throw new PreconditionFailException(String.format("상품에 최대 등록 가능한 이미지 개수는 %d개입니다.", FileConstant.MAX_CAFE_IMAGE_CNT));
        }
        if (product != null && file != null) {
            if(!file.getFileInfo().equals(FileConstant.FILE_INFO_NO_USE)){
                throw new NoSuchElementFoundException("이미 사용중이거나 삭제된 이미지입니다.");
            }
            file.setFileInfo(FileConstant.FILE_INFO_PRODUCT_IMAGE);
            file.setProduct(product);
        }
        else{
            throw new NoSuchElementFoundException("존재하지 않는 상품이거나 이미지입니다.");
        }
        return 1L;
    }



    /**
     * [상품 사진 삭제]
     * 상품의 기존 사진을 삭제한다
     * @param productId 상품 ID
     * @param fileId 파일 ID
     * @throws IllegalArgumentException 존재하지 않는 매장이나 이미지일 경우 예외처리
     */
    @Transactional()
    public Long removeProductImage(Long productId, Long fileId){
        Product product = productRepository.findById(productId);
        File file = fileRepository.findById(fileId);

        if (product != null && file != null) {
            file.setFileInfo(FileConstant.FILE_INFO_DELETE);
            file.setDeleted(true);
            file.setProduct(null);
        }
        else{
            throw new NoSuchElementFoundException("존재하지 않는 상품이거나 이미지입니다.");
        }
        return 1L;
    }
}

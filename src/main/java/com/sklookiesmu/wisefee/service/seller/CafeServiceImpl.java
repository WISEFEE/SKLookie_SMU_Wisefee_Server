package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.order.OrderOptionRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptChoiceRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptionRepository;
import com.sklookiesmu.wisefee.repository.product.ProductRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService{

    private final CafeRepository cafeRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final ProductRepository productRepository;
    private final SubscribeRepository subscribeRepository;

    @Override
    @Transactional
    public Long create(Cafe cafe, Long addrId) {

        validateDuplicateCafe(cafe); // 중복 카페 검증

        Address address = new Address();
        address.setAddrId(addrId);
        cafe.setAddress(address);

        Long pk = SecurityUtil.getCurrentMemberPk();
        if (pk == null) {
            throw new RuntimeException("현재 인증된 회원 정보를 가져올 수 없습니다.");
        }

        Member member = new Member();
        member.setMemberId(pk);
        cafe.setMember(member);

        cafeRepository.create(cafe);
        return cafe.getCafeId();
    }

    private void validateDuplicateCafe(Cafe cafe) {
        List<Cafe> findCafe = cafeRepository.findByTitle(cafe.getTitle());
        if (!findCafe.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 카페입니다.");
        }
    }



    @Override
    @Transactional
    public void update(Long cafeId, UpdateCafeRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        String newTitle = requestDto.getTitle();
        String newContent = requestDto.getContent();
        String newCafePhone = requestDto.getCafePhone();

        if (newTitle != null) {
            cafe.setTitle(newTitle);
        }

        if (newContent != null) {
            cafe.setContent(newContent);
        }

        if (newCafePhone != null) {
            cafe.setCafePhone(newCafePhone);
        }
    }



    @Override
    @Transactional
    public void delete(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        // 매장 삭제 시 매장의 주문 옵션 삭제
        List<OrderOption> orderOptions = orderOptionRepository.findByCafe(cafe);

        // 매장의 주문 옵션 소프트 삭제
        orderOptions.forEach(orderOption -> {
            orderOptionRepository.softDelete(orderOption);
        });

        // 매장 삭제 시 매장의 상품 삭제
        List<Product> products = productRepository.findByCafe(cafe);

        // 매장의 상품 소프트 삭제
        products.forEach(product -> {
            productRepository.softDelete(product);
        });

        // 매장 소프트 삭제
        cafeRepository.softDelete(cafe);
    }


    @Override
    public Cafe findCafe(Long cafeId) {
        return cafeRepository.findById(cafeId);
    }


    @Override
    public List<Cafe> getAllNotDeletedCafes() {
        return cafeRepository.findAllNotDeleted();
    }


    @Override
    public List<Subscribe> getSubscribersByCafeId(Long cafeId) {
        return subscribeRepository.findAllByCafeId(cafeId);
    }
}

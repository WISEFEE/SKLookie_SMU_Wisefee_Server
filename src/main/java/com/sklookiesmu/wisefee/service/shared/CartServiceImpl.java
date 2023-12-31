package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.exception.global.AlreadyExistElementException;
import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.CartResponseDto;
import com.sklookiesmu.wisefee.repository.CartRepository;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.SubTicketTypeRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptChoiceRepository;
import com.sklookiesmu.wisefee.repository.product.ProductRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import com.sklookiesmu.wisefee.service.shared.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductOptChoiceRepository productOptChoiceRepository;
    private final CafeRepository cafeRepository;

    private final SubscribeJpaRepository subscribeRepository;
    private final SubTicketTypeRepository subTicketTypeRepository;

    @Override
    public Long findCartId(Long memberId) {
        return cartRepository.findCartByMemberId(memberId).getCartId();
    }

    @Override
    public Long addCart(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found"));
        Cart cart = new Cart().addCart(member);

        if (member.getCart() == null) {
            cartRepository.createCart(cart);
            member.setCart(cart);
        }
        return cart.getCartId();
    }

    @Override
    public Long addCartProduct(Long memberId, CartRequestDto.CartProductRequestDto cartRequestDto) {
        Cafe cafe = cafeRepository.findById(cartRequestDto.getCafeId());
        if (cafe == null) {
            throw new NoSuchElementFoundException("카페가 존재하지 않습니다.");
        }
        Product product = productRepository.findById(cartRequestDto.getProductId());

        if (product == null) {
            throw new NoSuchElementFoundException("상품이 존재하지 않습니다.");
        }

        if (!Objects.equals(cafe.getCafeId(), product.getCafe().getCafeId())) {
            throw new NoSuchElementFoundException("카페에 주문하신 상품이 존재하지 않습니다.");
        }


        Cart cart = cartRepository.findCartByMemberId(memberId);

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
        if (cartProducts.size() != 0 &&
                !Objects.equals(cartProducts.get(0).getProduct().getCafe().getCafeId(), product.getCafe().getCafeId())) {
            // cleaning cartProduct in cart
            cartProducts.forEach(cartRepository::deleteCartProduct);
        }

        List<Long> cartProductDtoList = new ArrayList<>();
        for (int i = 0; i < cartRequestDto.getProductOptChoices().size(); i++) {
            cartProductDtoList.add(cartRequestDto.getProductOptChoices().get(i).getOptionChoiceId());
        }

        for (CartProduct cartProduct : cartProducts) {
            List<Long> cartProductList = new ArrayList<>();
            for (int i = 0; i < cartProduct.getCartProductChoiceOptions().size(); i++) {
                cartProductList.add(cartProduct.getCartProductChoiceOptions().get(i).getProductOptChoice().getProductOptionChoiceId());
            }
            if (cartProductDtoList.equals(cartProductList)) {
                throw new AlreadyExistElementException("이미 장바구니가 존재합니다.");
            }
        }

        CartProduct cartProduct = new CartProduct();

        cartRepository.createCartProduct(cartProduct.addCartProduct(cartRequestDto.getProductQuantity(), cart, product));

        addCartProductOptionChoice(cartRequestDto, product, cartProduct);

        return cartProduct.getCartProductId();
    }

    public void addCartProductOptionChoice(CartRequestDto.CartProductRequestDto cartRequestDto, Product product, CartProduct cartProduct) {
        List<CartProductChoiceOption> cartProductChoiceOptions = new ArrayList<>();
        for (CartRequestDto.ProductOptionChoiceAsCartRequestDto productOptionChoice : cartRequestDto.getProductOptChoices()
        ) {
            ProductOptChoice productOptChoice = productOptChoiceRepository.findById(productOptionChoice.getOptionChoiceId());
            if (productOptChoice == null) {
                throw new NoSuchElementFoundException("선택하신 상품 옵션이 존재하지 않습니다.");
            }

            if (!product.getProductId().equals(productOptChoice.getProductOption().getProduct().getProductId())) {
                throw new NoSuchElementFoundException("선택하신 상품 옵션이 제품의 상품 옵션에 존재하지 않습니다.");
            }
            CartProductChoiceOption cartProductChoiceOption = new CartProductChoiceOption(cartProduct, productOptChoice);
            cartRepository.createCartProductChoicesOption(cartProductChoiceOption);

            cartProductChoiceOptions.add(cartProductChoiceOption);
        }
        cartProduct.setCartProductChoiceOptions(cartProductChoiceOptions);

    }

    @Override
    public List<CartResponseDto.CartProductResponseDto> findAllCartProduct(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found"));
        if (member.getCart() == null) {
            throw new NoSuchElementFoundException("장바구니가 존재하지 않습니다.");
        }
        Cart cart = cartRepository.findCartByCartId(member.getCart().getCartId());
        if (cart == null) {
            throw new NoSuchElementFoundException("장바구니가 존재하지 않습니다.");
        }

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
        List<Product> products = new ArrayList<>();

        List<CartResponseDto.CartProductResponseDto> cartProductResponseDtos = new ArrayList<>();

        for (int i = 0; i < cartProducts.size(); i++) {
            products.add(productRepository.findById(cartProducts.get(i).getProduct().getProductId()));

            List<CartResponseDto.ProductOptChoiceResponseDTO> productOptChoiceResponseDTOS = new ArrayList<>();

            for (CartProductChoiceOption productOptChoice : cartProducts.get(i).getCartProductChoiceOptions()
            ) {
                productOptChoiceResponseDTOS.add(new CartResponseDto.ProductOptChoiceResponseDTO(
                        productOptChoice.getProductOptChoice().getProductOption().getProductOptionId(),
                        productOptChoice.getProductOptChoice().getProductOption().getProductOptionName(),
                        productOptChoice.getProductOptChoice().getProductOptionChoiceId(),
                        productOptChoice.getProductOptChoice().getProductOptionChoiceName(),
                        productOptChoice.getProductOptChoice().getProductOptionChoicePrice()
                ));
            }

            cartProductResponseDtos.add(new CartResponseDto.CartProductResponseDto(
                    products.get(i).getCafe().getCafeId(),
                    products.get(i).getCafe().getTitle(),
                    products.get(i).getProductId(),
                    products.get(i).getProductName(),
                    products.get(i).getProductInfo(),
                    products.get(i).getProductPrice(),
                    cartProducts.get(i).getProductQuantity(),
                    productOptChoiceResponseDTOS,
                    cart.getCartStatus(),
                    cartProducts.get(i).getCreatedAt(),
                    cartProducts.get(i).getUpdatedAt()
            ));
        }
        return cartProductResponseDtos;
    }

    @Override
    public Long deleteCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartRepository.findCartProduct(cartProductId);
        if (cartProduct.getDeletedAt() != null) {
            throw new NoSuchElementFoundException("장바구니가 존재하지 않습니다.");
        }
        cartRepository.deleteCartProduct(cartProduct);
        return 1L;
    }

    @Override
    public Long updateCartProduct(Long cartProductId, CartRequestDto.CartProductUpdateRequestDTO cartProductUpdateRequestDto) {
        CartProduct cartProduct = cartRepository.findCartProduct(cartProductId);
        if (cartProduct == null) {
            throw new NoSuchElementFoundException("장바구니 상품이 존재하지 않습니다.");
        }
        if (cartProduct.getProductQuantity() + cartProductUpdateRequestDto.getAddProductQuantity() <= 0) {
            return deleteCartProduct(cartProductId);
//            throw new RuntimeException("Negative Quantity : This ProductQuantity being negative value When update cartProduct.");
        }

        return cartProduct.updateQuantity(cartProductUpdateRequestDto.getAddProductQuantity());
    }


    @Override
    public Long calculateCart(Long memberId) {

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cartRepository.findCartByMemberId(memberId).getCartId());
        if (cartProducts.size() == 0) {
            throw new NoSuchElementFoundException("장바구니가 비어 있습니다.");
        }
        long result = 0L;
        for (CartProduct cartProduct : cartProducts) {
            Product product = productRepository.findById(cartProduct.getProduct().getProductId());
            result += product.getProductPrice() * cartProduct.getProductQuantity();
            for (CartProductChoiceOption productOptChoice :
                    cartProduct.getCartProductChoiceOptions()) {
                result += productOptChoice.getProductOptChoice().getProductOptionChoicePrice() * cartProduct.getProductQuantity();
            }
        }
        return result;
    }

    @Override
    public Long calculateCartWithSubTicket(Long memberId, Long subscribeId) {
        // get discount rate value
        Optional<Subscribe> subscribe = subscribeRepository.findById(subscribeId);
        if(subscribe.isEmpty()){
            throw new NoSuchElementFoundException("해당 구독 ID를 찾을 수 없습니다.");
        }
        if(subscribe.get().getMember().getMemberId() != memberId){
            throw new NoSuchElementFoundException("해당 구독을 맺지 않았습니다.");
        }
        SubTicketType subTicketType = subscribe.get().getSubTicketType();
        double subPeople = subscribe.get().getSubPeople();
        double subTicketAdditionalDiscountRate = subTicketType.getSubTicketAdditionalDiscountRate() * subPeople; // 인원당 추가 할인율
        double subTicketDeposit = subTicketType.getSubTicketDeposit() * subPeople; //  텀블러 보증금
        double subTicketBaseDiscountRate = subTicketType.getSubTicketBaseDiscountRate(); // 기본 할인율
        double subTicketMaxDiscountRate = subTicketType.getSubTicketMaxDiscountRate(); // 최대 할인율
        double totalDiscountRate = ((subTicketBaseDiscountRate + subTicketAdditionalDiscountRate) / 100);
        double currentDiscountRate = Math.min(totalDiscountRate, subTicketMaxDiscountRate / 100);

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cartRepository.findCartByMemberId(memberId).getCartId());

        if (cartProducts.size() == 0) {
            throw new NoSuchElementFoundException("장바구니가 비어있습니다.");
        }
//        long result = (long) subTicketDeposit;
        long result = 0L;
        for (CartProduct cartProduct : cartProducts) {
            int productPrice = 0;
            Product product = productRepository.findById(cartProduct.getProduct().getProductId());
            productPrice += product.getProductPrice() * cartProduct.getProductQuantity();
            for (CartProductChoiceOption productOptChoice :
                    cartProduct.getCartProductChoiceOptions()) {
                productPrice += productOptChoice.getProductOptChoice().getProductOptionChoicePrice() * cartProduct.getProductQuantity();
            }

            System.out.println("Service: " + currentDiscountRate);
            // calculate discount rate
            result += (long) productPrice - (productPrice * currentDiscountRate);

        }
        return result;
    }
}

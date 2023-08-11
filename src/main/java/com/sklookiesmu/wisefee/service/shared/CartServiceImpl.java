package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.error.AlreadyhadCartException;
import com.sklookiesmu.wisefee.common.error.CafeNotFoundException;
import com.sklookiesmu.wisefee.common.error.CartNotFoundException;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.CartResponseDto;
import com.sklookiesmu.wisefee.repository.CartRepository;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptChoiceRepository;
import com.sklookiesmu.wisefee.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductOptChoiceRepository productOptChoiceRepository;
    private final CafeRepository cafeRepository;
    @Override
    public Long addCart(Long memberId, boolean deleteFlag) {
        Member member = memberRepository.find(memberId);
        Cart cart = new Cart();
        cart.addCart(member);

        if(member.getCart() == null || deleteFlag) {
            cartRepository.createCart(cart);
        } else {
            Cart carts = cartRepository.findCartByCartId(member.getCart().getCartId());
            if(carts != null){
                if(carts.getMember().getMemberId().equals(memberId)) {
                    throw new AlreadyhadCartException("Invalid Value : This Member is Already had with cartId : "+member.getCart().getCartId());
                }
            }
        }
        return cart.getCartId();
    }

    @Override
    public Long[] addCartProduct(Long memberId, CartRequestDto.CartProductRequestDto cartRequestDto) {
        Cafe cafe = cafeRepository.findById(cartRequestDto.getCafeId());
        if(cafe == null) {
            throw new CafeNotFoundException("Invalid Value : Not found cafe with " + cartRequestDto.getCafeId());
        }
        Product product = productRepository.findById(cartRequestDto.getProductId());

        if(product == null) {
            throw new RuntimeException("Invalid Value : This product is not exist. :" + cartRequestDto.getProductId());
        }

        if(!Objects.equals(cafe.getCafeId(), product.getCafe().getCafeId())) {
            throw new RuntimeException("Invalid Value : This product is not match with cafe.");
        }

        Cart cart = cartRepository.findCartByCartId(cartRequestDto.getCartId());
        if(cartRequestDto.getCartId() == 0 || cart == null) {
            Long newCartId = addCart(memberId, false);
            cart = cartRepository.findCartByCartId(newCartId);
        } else if (!Objects.equals(cart.getCartId(), cartRequestDto.getCartId())) {
            throw new RuntimeException("Invalid Value: This cartId does not match your cartId: " + cartRequestDto.getCartId());
        }
        List<CartProduct> cps = cartRepository.findCartProductByCartId(cartRequestDto.getCartId());
        if(cps.size() != 0) {
            if (!Objects.equals(cps.get(0).getProduct().getCafe().getCafeId(), product.getCafe().getCafeId())) {

                Cart oldCart = cart;
                Long newCartId = addCart(memberId, true);
                System.out.println("Service : newCartId : " + newCartId);

                cart = cartRepository.findCartByCartId(newCartId);
                cartRepository.deleteCart(oldCart);
            }
        }

        if (!cart.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("Invalid Value: This cartId does not match with the member: " + cart.getCartId());
        }

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
        List<Long> cartProductDtoList = new ArrayList<>();
        for(int i = 0; i < cartRequestDto.getProductOptChoices().size(); i++) {
            cartProductDtoList.add(cartRequestDto.getProductOptChoices().get(i).getOptionChoiceId());
        }
        for (CartProduct cartProduct : cartProducts) {
            List<Long> cartProductList = new ArrayList<>();
            for(int i = 0; i < cartProduct.getCartProductChoiceOptions().size(); i++) {
                cartProductList.add(cartProduct.getCartProductChoiceOptions().get(i).getProductOptChoice().getProductOptionChoiceId());
            }
            if(cartProductDtoList.equals(cartProductList)) {
                throw new RuntimeException("Invalid Value : Already exist CartProduct.");
            }
        }

        CartProduct cartProduct = new CartProduct();

        cartProduct.addCartProduct(cartRequestDto.getProductQuantity(), cart, product);

        cartRepository.createCartProduct(cartProduct);

        addCartProductOptionChoice(cartRequestDto, product, cartProduct);

        return new Long[]{cart.getCartId(), cartProduct.getCartProductId()};
    }

    public void addCartProductOptionChoice(CartRequestDto.CartProductRequestDto cartRequestDto, Product product, CartProduct cartProduct) {
        List<CartProductChoiceOption> cartProductChoiceOptions = new ArrayList<>();
        for (CartRequestDto.ProductOptionChoiceAsCartRequestDto pdoc: cartRequestDto.getProductOptChoices()
        ) {
            ProductOptChoice productOptChoice = productOptChoiceRepository.findById(pdoc.getOptionChoiceId());
            if(productOptChoice == null) {
                throw new RuntimeException("Invalid Value : This productOptChoice is not exist. : " + pdoc.getOptionChoiceId());
            }

            if(!product.getProductId().equals(productOptChoice.getProductOption().getProduct().getProductId())) {
                throw new RuntimeException("Invalid Value : This productOptChoice is not match with productOption of productId. :"
                        + productOptChoice.getProductOptionChoiceId());
            }
            CartProductChoiceOption cartProductChoiceOption = new CartProductChoiceOption(cartProduct, productOptChoice);
            cartRepository.createCartProductChoicesOption(cartProductChoiceOption);

            cartProductChoiceOptions.add(cartProductChoiceOption);
        }
        cartProduct.setCartProductChoiceOptions(cartProductChoiceOptions);

    }
    @Override
    public List<CartResponseDto.CartProductResponseDto> findAllCartProduct(Long memberId) {
        Member member = memberRepository.find(memberId);
        if (member.getCart() == null) {
            throw new CartNotFoundException("CartService Error : Not Exist Cart with " + memberId );
        }
        Cart cart = cartRepository.findCartByCartId(member.getCart().getCartId());
        if(cart == null) {
            throw new CartNotFoundException("CartService Error : Not Found Cart with " + memberId );
        }

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
        List<Product> products = new ArrayList<>();

        List<CartResponseDto.CartProductResponseDto> cartProductResponseDtos = new ArrayList<>();

        for(int i = 0; i < cartProducts.size(); i++) {
            products.add(productRepository.findById(cartProducts.get(i).getProduct().getProductId()));

            List<CartResponseDto.ProductOptChoiceResponseDTO> productOptChoiceResponseDTOS  = new ArrayList<>();

            for (CartProductChoiceOption productOptChoice: cartProducts.get(i).getCartProductChoiceOptions()
            ) {
                productOptChoiceResponseDTOS.add(new CartResponseDto.ProductOptChoiceResponseDTO(
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
        if(cartProduct.getDeletedAt() != null) {
            throw new RuntimeException("Invalid Value : This cartProduct is already deleted. :" + cartProductId);
        }
        cartRepository.deleteCartProduct(cartProduct);
        return 1L;
    }

    @Override
    public Long updateCartProduct(Long cartProductId, CartRequestDto.CartProductUpdateRequestDTO cartProductUpdateRequestDto) {
        CartProduct cartProduct = cartRepository.findCartProduct(cartProductId);
        if(cartProduct == null) {
            throw new RuntimeException("Invalid Value : This cartProduct is not exist. :" + cartProductId);
        }
        if(cartProduct.getProductQuantity() + cartProductUpdateRequestDto.getAddProductQuantity() <= 0) {
            return deleteCartProduct(cartProductId);
//            throw new RuntimeException("Negative Quantity : This ProductQuantity being negative value When update cartProduct.");
        }

        return cartProduct.updateQuantity(cartProductUpdateRequestDto.getAddProductQuantity());
    }


    @Override
    public Long calculateCart(Long cartId) {
        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cartId);
        if(cartProducts.size() == 0) {
            throw new RuntimeException("Invalid Value : This cart is null");
        }
        long result = 0L;
        for (CartProduct cartProduct : cartProducts) {
            Product product = productRepository.findById(cartProduct.getProduct().getProductId());
            result += product.getProductPrice() * cartProduct.getProductQuantity();
            for (CartProductChoiceOption productOptChoice:
            cartProduct.getCartProductChoiceOptions()) {
                result += productOptChoice.getProductOptChoice().getProductOptionChoicePrice() * cartProduct.getProductQuantity();
            }
        }
        return result;
    }
}

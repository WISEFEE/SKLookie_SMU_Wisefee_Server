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
    public Long addCart(Long memberId) {
        Member member = memberRepository.find(memberId);
        Cart cart = new Cart();
        cart.addCart(member);
        if(member.getCart() == null) {
            cartRepository.createCart(cart);
        } else {
            Optional<Cart> carts = cartRepository.findCartByCartId(member.getCart().getCartId());
            if(carts.isPresent()){
                if(carts.get().getMember().getMemberId().equals(memberId)) {
                    throw new AlreadyhadCartException("Invalid Value : This Member is Already had with cartId : "+member.getCart().getCartId());
                }
            }
        }
        return cart.getCartId();
    }

    @Override
    public Long addCartProduct(Long memberId, CartRequestDto.CartProductRequestDto cartRequestDto) {
        Cafe cafe = cafeRepository.findById(cartRequestDto.getCafeId());
        if(cafe == null) {
            throw new CafeNotFoundException("Invalid Value : Not found cafe with " + cartRequestDto.getCafeId());
        }
        Cart cart = cartRepository.findCartByCartId(cartRequestDto.getCartId()).orElse(null);
        if (cart == null) {
            Long newCartId = addCart(memberId);
            Optional<Cart> cartOptional = cartRepository.findCartByCartId(newCartId);
            cart = cartOptional.orElseThrow(() -> new RuntimeException("Failed to create a new cart."));
        }
        if(!cart.getMember().getMemberId().equals(memberId)) {
            throw new RuntimeException("Invalid Value : This cartId is not match with member :" + cart.getCartId());
        }
        if(!Objects.equals(cart.getCartId(), cartRequestDto.getCartId())) {
            throw new RuntimeException("Invalid Value : This cartId is not math your cartId. : " + cartRequestDto.getCartId());
        }


        Product product = productRepository.findById(cartRequestDto.getProductId());

        if(product == null) {
            throw new RuntimeException("Invalid Value : This product is not exist. :" + cartRequestDto.getProductId());
        }

        CartProduct cartProduct = new CartProduct();

        List<ProductOptChoice> productOptChoices = new ArrayList<>();
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

            List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
            for (CartProduct cartPdt :cartProducts
                 ) {
                for (ProductOptChoice optChoice : cartPdt.getProductOptChoices()
                     ) {
                    if(optChoice.getProductOptionChoiceId().equals(productOptChoice.getProductOptionChoiceId())) {
                        throw new RuntimeException("Invalid Value : This productOptChoice is Already Exist in your cartProduct. : "
                                + productOptChoice.getProductOptionChoiceId());
                    }
                }

            }
            productOptChoices.add(productOptChoice);
        }
        cartProduct.addCartProduct(cartRequestDto.getCartProductQuantity(), cart, product, productOptChoices);
        cartRepository.createCartProduct(cartProduct);
        for (ProductOptChoice productOptChoice: productOptChoices
        ) {
            productOptChoice.setCartProduct(cartProduct);
        }
        return cartProduct.getCartProductId();
    }

    @Override
    public List<CartProduct> findCartProductByCart(Long cartId) {
        return null;
    }

    @Override
    public List<CartResponseDto.CartProductResponseDto> findAllCartProduct(Long memberId) {
        Member member = memberRepository.find(memberId);
        if (member.getCart() == null) {
            throw new CartNotFoundException("CartService Error : Not Exist Cart with " + memberId );
        }
        Optional<Cart> optionalCart = cartRepository.findCartByCartId(member.getCart().getCartId());
        Cart cart = optionalCart.orElse(null);
        if(cart == null) {
            throw new CartNotFoundException("CartService Error : Not Found Cart with " + memberId );
        }

        List<CartProduct> cartProducts = cartRepository.findCartProductByCartId(cart.getCartId());
        List<Product> products = new ArrayList<>();

        List<CartResponseDto.CartProductResponseDto> cartProductResponseDtos = new ArrayList<>();

        for(int i = 0; i < cartProducts.size(); i++) {
            products.add(productRepository.findById(cartProducts.get(i).getProduct().getProductId()));

            List<CartResponseDto.ProductOptChoiceResponseDTO> productOptChoiceResponseDTOS  = new ArrayList<>();

            for (ProductOptChoice productOptChoice: cartProducts.get(i).getProductOptChoices()
            ) {
                productOptChoiceResponseDTOS.add(new CartResponseDto.ProductOptChoiceResponseDTO(
                        productOptChoice.getProductOptionChoiceId(),
                        productOptChoice.getProductOptionChoiceName(),
                        productOptChoice.getProductOptionChoicePrice()
                ));
            }

            cartProductResponseDtos.add(new CartResponseDto.CartProductResponseDto(
                    products.get(i).getCafe().getCafeId(),
                    products.get(i).getCafe().getTitle(),
                    products.get(i).getProductId(),
                    products.get(i).getProductName(),
                    products.get(i).getProductInfo(),
                    products.get(i).getProductPrice(),
                    cartProducts.get(i).getCartProductQuantity(),
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
        Long result = cartRepository.deleteCartProduct(cartProductId);
        return result;
    }

    @Override
    public Long updateCartProduct(Long cartProductId, CartRequestDto.CartProductUpdateRequestDTO cartProductUpdateRequestDto) {
        CartProduct cartProduct = cartRepository.findCartProduct(cartProductId);
        if(cartProduct == null) {
            throw new RuntimeException("Invalid Value : This cartProduct is not exist. :" + cartProductId);
        }
        if(cartProduct.getCartProductQuantity() + cartProductUpdateRequestDto.getAddCartProductQuantity() < 0) {
            return deleteCartProduct(cartProductId);
//            throw new RuntimeException("Negative Quantity : This cartProductQuantity being negative value When update cartProduct.");
        }

        return cartProduct.updateQuantity(cartProductUpdateRequestDto.getAddCartProductQuantity());
    }

}

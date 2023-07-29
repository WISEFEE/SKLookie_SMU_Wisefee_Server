package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.order.OrderOptionRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptChoiceRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptionRepository;
import com.sklookiesmu.wisefee.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService{

    private final CafeRepository cafeRepository;
    private final OrderOptionRepository orderOptionRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptChoiceRepository productOptChoiceRepository;


    @Override
    @Transactional
    public Long create(Cafe cafe) {

        validateDuplicateCafe(cafe); // 중복 카페 검증
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

        cafe.setTitle(requestDto.getTitle());
        cafe.setContent(requestDto.getContent());
        cafe.setCafePhone(requestDto.getCafePhone());
    }



    @Override
    @Transactional
    public void delete(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        //매장 삭제 시 매장의 주문 옵션 삭제
        List<OrderOption> orderOptions = orderOptionRepository.findByCafe(cafe);
        orderOptions.forEach(orderOption -> {
            orderOption.setCafe(null);
            orderOptionRepository.delete(orderOption);
        });

        //매장 삭제 시 매장의 상품 삭제
        List<Product> products = productRepository.findByCafe(cafe);
        products.forEach(product -> {
            product.setCafe(null);
            productRepository.delete(product);
        });

        cafeRepository.delete(cafe);
    }



    @Override
    @Transactional
    public Long addOrderOption(Long cafeId, CreateOrderOptionRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = new OrderOption();
        orderOption.setOrderOptionName(requestDto.getOrderOptionName());
        orderOption.setOrderOptionPrice(requestDto.getOrderOptionPrice());
        orderOption.setCafe(cafe);
        orderOptionRepository.create(orderOption);

        return orderOption.getOrderOptionId();
    }



    @Override
    @Transactional
    public void updateOrderOption(Long cafeId, Long orderOptionId, UpdateOrderOptionRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = orderOptionRepository.findById(orderOptionId);

        if (orderOption == null) {
            throw new IllegalArgumentException("존재하지 않는 주문 옵션입니다.");
        }
        if (!orderOption.getCafe().getCafeId().equals(cafeId)) {
            throw new IllegalArgumentException("해당 매장에 속하지 않는 주문 옵션입니다.");
        }

        orderOption.setOrderOptionName(requestDto.getOrderOptionName());
        orderOption.setOrderOptionPrice(requestDto.getOrderOptionPrice());
    }



    @Override
    @Transactional
    public void deleteOrderOption(Long cafeId, Long orderOptionId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = orderOptionRepository.findById(orderOptionId);

        if (orderOption == null) {
            throw new IllegalArgumentException("존재하지 않는 주문 옵션입니다.");
        }

        if (!orderOption.getCafe().getCafeId().equals(cafeId)) {
            throw new IllegalArgumentException("해당 매장에 속하지 않는 주문 옵션입니다.");
        }

        orderOption.setCafe(null);
        orderOptionRepository.delete(orderOption);
    }



    @Override
    @Transactional
    public Long addProduct(Long cafeId, CreateProductRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        Product product = new Product();
        product.setProductName(requestDto.getProductName());
        product.setProductPrice(requestDto.getProductPrice());
        product.setProductInfo(requestDto.getProductInfo());
        product.setCafe(cafe);

        productRepository.create(product);

        return product.getProductId();
    }



    @Override
    @Transactional
    public void updateProduct(Long cafeId, Long productId, UpdateProductRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        if (!product.getCafe().getCafeId().equals(cafeId)) {
            throw new IllegalArgumentException("해당 매장에 속하지 않는 상품입니다.");
        }

        product.setProductName(requestDto.getProductName());
        product.setProductPrice(requestDto.getProductPrice());
        product.setProductInfo(requestDto.getProductInfo());
    }



    @Override
    @Transactional
    public void deleteProduct(Long cafeId, Long productId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new IllegalArgumentException("존재하지 않는 매장입니다.");
        }

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        if (!product.getCafe().getCafeId().equals(cafeId)) {
            throw new IllegalArgumentException("해당 매장에 속하지 않는 상품입니다.");
        }

        //상품 삭제 시 상품 옵션 삭제
        List<ProductOption> ProductOptions = productOptionRepository.findByProduct(product);
        ProductOptions.forEach(ProductOption -> {
            ProductOption.setProduct(null);
            productOptionRepository.delete(ProductOption);
        });

        product.setCafe(null);
        productRepository.delete(product);
    }



    @Override
    @Transactional
    public Long addProductOption(Long productId, CreateProductOptionRequestDto requestDto) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = new ProductOption();
        productOption.setProductOptionName(requestDto.getProductOptionName());
        productOption.setProduct(product);

        productOptionRepository.create(productOption);

        return productOption.getProductOptionId();
    }



    @Override
    @Transactional
    public void updateProductOption(Long productId, Long productOptionId, UpdateProductOptionRequestDto requestDto) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

        if (!productOption.getProduct().getProductId().equals(productId)) {
            throw new IllegalArgumentException("해당 상품에 속하지 않는 상품 옵션입니다.");
        }

        productOption.setProductOptionName(requestDto.getProductOptionName());
    }



    @Override
    @Transactional
    public void deleteProductOption(Long productId, Long productOptionId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

        if (!productOption.getProduct().getProductId().equals(productId)) {
            throw new IllegalArgumentException("해당 상품에 속하지 않는 상품 옵션입니다.");
        }

        //상품 옵션 삭제 시 상품 옵션 선택지 삭제
        List<ProductOptChoice> ProductOptChoices = productOptChoiceRepository.findByProductOption(productOption);
        ProductOptChoices.forEach(ProductOptChoice -> {
            ProductOptChoice.setProductOption(null);
            productOptChoiceRepository.delete(ProductOptChoice);
        });

        productOption.setProduct(null);
        productOptionRepository.delete(productOption);
    }



    @Override
    @Transactional
    public Long addProductOptionChoice(Long productOptionId, CreateProductOptionChoiceRequestDto requestDto) {
       ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

        ProductOptChoice productOptChoice = new ProductOptChoice();
        productOptChoice.setProductOptionChoicePrice(requestDto.getProductOptionChoicePrice());
        productOptChoice.setProductOptionChoiceName(requestDto.getProductOptionChoiceName());
        productOptChoice.setProductOption(productOption);

        productOptChoiceRepository.create(productOptChoice);

        return productOptChoice.getProductOptionChoiceId();
    }



    @Override
    @Transactional
    public void updateProductOptionChoice(Long productId, Long productOptionId, Long productOptionChoiceId, UpdateProductOptionChoiceRequestDto requestDto) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);
        if (productOption == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

        ProductOptChoice productOptionChoice = productOptChoiceRepository.findById(productOptionChoiceId);
        if (productOptionChoice == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션 선택지입니다.");
        }

        if (!productOptionChoice.getProductOption().getProductOptionId().equals(productOptionId)) {
            throw new IllegalArgumentException("해당 상품 옵션에 속하지 않는 상품 옵션 선택지 입니다.");
        }

        productOptionChoice.setProductOptionChoiceName(requestDto.getProductOptionChoiceName());
        productOptionChoice.setProductOptionChoicePrice(requestDto.getProductOptionChoicePrice());
    }



    @Override
    @Transactional
    public void deleteProductOptionChoice(Long productId, Long productOptionId, Long productOptionChoiceId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션입니다.");
        }

        ProductOptChoice productOptionChoice = productOptChoiceRepository.findById(productOptionChoiceId);

        if (productOptionChoice == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 옵션 선택지입니다.");
        }

        if (!productOptionChoice.getProductOption().getProductOptionId().equals(productOptionId)) {
            throw new IllegalArgumentException("해당 상품 옵션에 속하지 않는 상품 옵션 선택지입니다.");
        }

        productOptionChoice.setProductOption(null);
        productOptChoiceRepository.delete(productOptionChoice);
    }


    @Override
    public Cafe findCafe(Long cafeId) {
        return cafeRepository.findById(cafeId);
    }

    @Override
    public OrderOption findOrderOption(Long orderOptionId) {
        return orderOptionRepository.findById(orderOptionId);
    }

    @Override
    public Product findProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductOption findProductOption(Long productOptionId) {
        return productOptionRepository.findById(productOptionId);
    }

    @Override
    public ProductOptChoice findProductOptionChoice(Long productOptionChoiceId) {
        return productOptChoiceRepository.findById(productOptionChoiceId);
    }


}

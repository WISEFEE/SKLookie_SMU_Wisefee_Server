package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.repository.*;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
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
public class CafeProductServiceImpl implements CafeProductService {

    private final CafeRepository cafeRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptChoiceRepository productOptChoiceRepository;

    @Override
    @Transactional
    public Long addProduct(Long cafeId, CreateProductRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
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
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        if (!product.getCafe().getCafeId().equals(cafeId)) {
            throw new NoSuchElementFoundException("해당 매장에 속하지 않는 상품입니다.");
        }

        String newProductName = requestDto.getProductName();
        Integer newProductPrice = requestDto.getProductPrice();
        String newProductInfo = requestDto.getProductInfo();

        if (newProductName != null) {
            product.setProductName(newProductName);
        }

        if (newProductPrice != null) {
            product.setProductPrice(newProductPrice);
        }

        if (newProductInfo != null) {
            product.setProductInfo(newProductInfo);
        }
    }



    @Override
    @Transactional
    public void deleteProduct(Long cafeId, Long productId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        if (!product.getCafe().getCafeId().equals(cafeId)) {
            throw new NoSuchElementFoundException("해당 매장에 속하지 않는 상품입니다.");
        }

        //상품 삭제 시 상품 옵션 삭제
        List<ProductOption> ProductOptions = productOptionRepository.findByProduct(product);
        ProductOptions.forEach(ProductOption -> {
            // 상품의 상품 옵션 소프트 삭제
            productOptionRepository.softDelete(ProductOption);
        });

        // 매장의 상품 소프트 삭제
        productRepository.softDelete(product);
    }



    @Override
    @Transactional
    public Long addProductOption(Long productId, CreateProductOptionRequestDto requestDto) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
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
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션입니다.");
        }

        if (!productOption.getProduct().getProductId().equals(productId)) {
            throw new NoSuchElementFoundException("해당 상품에 속하지 않는 상품 옵션입니다.");
        }

        String newProductOptionName = requestDto.getProductOptionName();

        if (newProductOptionName != null) {
            productOption.setProductOptionName(newProductOptionName);
        }
    }



    @Override
    @Transactional
    public void deleteProductOption(Long productId, Long productOptionId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션입니다.");
        }

        if (!productOption.getProduct().getProductId().equals(productId)) {
            throw new NoSuchElementFoundException("해당 상품에 속하지 않는 상품 옵션입니다.");
        }

        //상품 옵션 삭제 시 상품 옵션 선택지 삭제
        List<ProductOptChoice> ProductOptChoices = productOptChoiceRepository.findByProductOption(productOption);
        ProductOptChoices.forEach(ProductOptChoice -> {
            // 상품 옵션의 상품 옵션 선택지 소프트 삭제
            productOptChoiceRepository.softDelete(ProductOptChoice);
        });

        // 상품 옵션 소프트 삭제
        productOptionRepository.softDelete(productOption);
    }



    @Override
    @Transactional
    public Long addProductOptionChoice(Long productOptionId, CreateProductOptionChoiceRequestDto requestDto) {
        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션입니다.");
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
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);
        if (productOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션입니다.");
        }

        ProductOptChoice productOptionChoice = productOptChoiceRepository.findById(productOptionChoiceId);
        if (productOptionChoice == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션 선택지입니다.");
        }

        if (!productOptionChoice.getProductOption().getProductOptionId().equals(productOptionId)) {
            throw new NoSuchElementFoundException("해당 상품 옵션에 속하지 않는 상품 옵션 선택지 입니다.");
        }

        String newProductOptionChoiceName = requestDto.getProductOptionChoiceName();
        Integer newProductOptionChoicePrice = requestDto.getProductOptionChoicePrice();

        if (newProductOptionChoiceName != null) {
            productOptionChoice.setProductOptionChoiceName(newProductOptionChoiceName);
        }

        if (newProductOptionChoicePrice != null) {
            productOptionChoice.setProductOptionChoicePrice(newProductOptionChoicePrice);
        }
    }



    @Override
    @Transactional
    public void deleteProductOptionChoice(Long productId, Long productOptionId, Long productOptionChoiceId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품입니다.");
        }

        ProductOption productOption = productOptionRepository.findById(productOptionId);

        if (productOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션입니다.");
        }

        ProductOptChoice productOptionChoice = productOptChoiceRepository.findById(productOptionChoiceId);

        if (productOptionChoice == null) {
            throw new NoSuchElementFoundException("존재하지 않는 상품 옵션 선택지입니다.");
        }

        if (!productOptionChoice.getProductOption().getProductOptionId().equals(productOptionId)) {
            throw new NoSuchElementFoundException("해당 상품 옵션에 속하지 않는 상품 옵션 선택지입니다.");
        }

        // 상품 옵션 선택지 소프트 삭제
        productOptChoiceRepository.softDelete(productOptionChoice);
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


    @Override
    public List<Product> getProductsByCafeId(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        return productRepository.findByCafe(cafe);
    }
}

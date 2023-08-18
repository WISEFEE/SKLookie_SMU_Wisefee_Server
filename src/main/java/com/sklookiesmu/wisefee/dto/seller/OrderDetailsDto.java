package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "주문 상세 정보 DTO")
public class OrderDetailsDto {

    @ApiModelProperty(value = "주문 일시")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수령 시간")
    private LocalDateTime productReceiveTime;

    @ApiModelProperty(value = "고객 닉네임")
    private String nickname;

    @ApiModelProperty(value = "주문 상태")
    private ProductStatus productStatus;

    @ApiModelProperty(value = "주문한 상품 정보 리스트")
    private List<ProductInfo> products;

    @ApiModelProperty(value = "주문 옵션 정보")
    private List<String> orderOptionNames;

    @Data
    @AllArgsConstructor
    public static class ProductInfo {
        private String productName;
        private List<ProductOptionInfo> productOptions;
    }

    @Data
    @AllArgsConstructor
    private static class ProductOptionInfo {
        private String productOptionName;
        private List<String> productOptionChoiceNames;
    }

    public static OrderDetailsDto fromOrder(Order order) {
        List<ProductInfo> products = order.getOrderProducts().stream()
                .map(product -> {
                    List<ProductOptionInfo> productOptions = product.getProduct().getProductOptions().stream()
                            .map(option -> {
                                List<String> productOptionChoiceNames = option.getProductOptChoices().stream()
                                        .map(ProductOptChoice::getProductOptionChoiceName)
                                        .collect(Collectors.toList());
                                return new ProductOptionInfo(option.getProductOptionName(), productOptionChoiceNames);
                            })
                            .collect(Collectors.toList());

                    return new ProductInfo(product.getProduct().getProductName(), productOptions);
                })
                .collect(Collectors.toList());

        List<String> orderOptionNames = order.getOrderOptions().stream()
                .map(OrderOption::getOrderOptionName)
                .collect(Collectors.toList());

        return new OrderDetailsDto(
                order.getCreatedAt(),
                order.getProductReceiveTime(),
                order.getSubscribe().getMember().getNickname(),
                order.getProductStatus(),
                products,
                orderOptionNames
        );
    }
}

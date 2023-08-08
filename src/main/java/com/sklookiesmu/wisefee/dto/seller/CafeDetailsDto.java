package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.domain.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장 세부 조회 DTO")
public class CafeDetailsDto {

    @ApiModelProperty(value = "매장 ID")
    private Long cafeId;

    @ApiModelProperty(value = "매장명")
    private String title;

    @ApiModelProperty(value = "매장 설명")
    private String content;

    @ApiModelProperty(value = "매장 전화번호")
    private String cafePhone;

    @ApiModelProperty(value = "매장 상품 리스트")
    private List<Product> products;

    @ApiModelProperty(value = "매장 주문 옵션 리스트")
    private List<OrderOption> orderOptions;
}

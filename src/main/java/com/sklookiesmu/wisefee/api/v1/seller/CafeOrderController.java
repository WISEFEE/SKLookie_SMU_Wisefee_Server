package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.service.seller.CafeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "매장 주문 API")
@RestController
@RequiredArgsConstructor
public class CafeOrderController {

    private final CafeService cafeService;

}

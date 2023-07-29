package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Api(tags = "고객 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerController {

    private final ConsumerServiceImpl consumerService;

    @ApiOperation(value = "정기 구독하기")
    @PostMapping("{cafeId}/subscribe/{subTicketTypeId}")
    public ResponseEntity<?> createSubscribe(@PathVariable("cafeId") Long cafeId,
                                             @PathVariable("subTicketTypeId") Long subTicketTypeId,
                                             @RequestBody SubscribeDto.SubscribeRequestDto request){
        consumerService.createSubscribe(request, cafeId, subTicketTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

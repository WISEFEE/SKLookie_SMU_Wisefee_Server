package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.dto.consumer.SubTicketTypeDto;
import com.sklookiesmu.wisefee.dto.shared.address.AddressRequestDto;
import com.sklookiesmu.wisefee.dto.shared.address.AddressResponseDto;
import com.sklookiesmu.wisefee.dto.shared.member.MemberResponseDto;
import com.sklookiesmu.wisefee.dto.shared.subTicket.SubTicketTypeRequestDto;
import com.sklookiesmu.wisefee.dto.shared.subTicket.SubTicketTypeResponseDto;
import com.sklookiesmu.wisefee.service.shared.MemberService;
import com.sklookiesmu.wisefee.service.shared.SubTicketTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "COMM-D :: 구독권 유형 API")
@RestController
@RequestMapping("/api/v1/subTicketType")
@RequiredArgsConstructor
public class SubscribeTicketController {
    private final SubTicketTypeService subTicketTypeService;
    private final ModelMapper modelMapper;


    @ApiOperation(
            value = "COMM-D-X1 :: 구독권 유형 추가(관리자용)",
            notes = "BM 제작 시 결정했던 방식대로의 구독권 유형을 추가하는 로직입니다. <br>" +
                    "해당 API를 일반 사용자들이 쓸 일은 없고, " +
                    "운영자인 저희가 해당 API를 이용하여 구독권 유형을 추가하면 됩니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping("")
    public ResponseEntity<Long> addSubTicketType(@Valid @RequestBody SubTicketTypeRequestDto ticket){
        SubTicketType entity = modelMapper.map(ticket, SubTicketType.class);
        Long id = subTicketTypeService.addTicketType(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    @ApiOperation(
            value = "COMM-D-U1 :: 구독권 ID로 검색",
            notes = "구독권을 ID(PK)로 검색하는 API입니다. 정기구독 체결이나, 매장 정보 등을 보여주기 전 등에, <br>" +
                    " 구독권 유형에 대한 정보가 필요할 때 사용하시면 됩니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{id}")
    public ResponseEntity<SubTicketTypeResponseDto> findSubTicketType(
            @ApiParam(value = "구독권 유형 ID", required = true)
            @PathVariable("id") Long id
    ){
        SubTicketType ticket = subTicketTypeService.selectById(id);
        if(ticket == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        SubTicketTypeResponseDto result = modelMapper.map(ticket, SubTicketTypeResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(
            value = "COMM-D-U2 :: 구독권 전체 검색",
            notes = "구독권 유형 전체 리스트를 검색하는 API입니다. 정기구독 체결이나, 매장 정보 등을 보여주기 전 등에, <br>" +
                    " 구독권 유형에 대한 정보가 필요할 때 사용하시면 됩니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("")
    public ResponseEntity<List<SubTicketTypeResponseDto>> findSubTicketTypeList(){
        List<SubTicketType> tickets = subTicketTypeService.selectList();
        if(tickets == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<SubTicketTypeResponseDto> result = tickets.stream()
                .map(e -> modelMapper.map(e, SubTicketTypeResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
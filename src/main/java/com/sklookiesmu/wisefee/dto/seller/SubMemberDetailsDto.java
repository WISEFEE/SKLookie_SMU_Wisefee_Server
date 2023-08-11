//package com.sklookiesmu.wisefee.dto.seller;
//
//import com.sklookiesmu.wisefee.domain.*;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiModel(description = "매장 구독 멤버 세부 정보 DTO")
//public class SubMemberDetailsDto {
//
//    @ApiModelProperty(value = "멤버 닉네임")
//    private String nickname;
//
//    @ApiModelProperty(value = "멤버 연락처")
//    private String phone;
//
//    @ApiModelProperty(value = "구독권 유형")
//    private String subTicketName;
//
//    @ApiModelProperty(value = "구독 시작일")
//    private LocalDateTime createdAt;
//
//    @ApiModelProperty(value = "구독 만료일")
//    private LocalDateTime expiredAt;
//
//    @ApiModelProperty(value = "구독권 사용 횟수")
//    private Integer subCnt;
//
//    @ApiModelProperty(value = "주문 상태")
//    private String productStatus;
//
//    @ApiModelProperty(value = "매장명")
//    private String title;
//
//    public static SubMemberDetailsDto fromSubscriptionAndMember(Subscribe subscribe, SubTicketType subTicketType, Member member, Order order, Cafe cafe) {
//        return new SubMemberDetailsDto(
//                member.getNickname(),
//                member.getPhone(),
//                subTicketType.getSubTicketName(),
//                subscribe.getCreatedAt(),
//                subscribe.getExpiredAt,
//                subscribe.getSubCnt(),
//                order.getProductStatus(),
//                cafe.getTitle()
//        );
//    }
//}

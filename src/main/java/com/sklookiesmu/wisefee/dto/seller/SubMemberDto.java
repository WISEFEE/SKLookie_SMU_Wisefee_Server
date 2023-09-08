package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.domain.Subscribe;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "멤버 DTO")
public class SubMemberDto {

    @ApiModelProperty(value = "멤버 ID")
    private Long memberId;

    @ApiModelProperty(value = "멤버 이름")
    private String nickname;

    @ApiModelProperty(value = "구독 ID")
    private Long subId;

    @ApiModelProperty(value = "구독권 유형")
    private String subTicketName;


    public static SubMemberDto fromMemberAndSubscribe(Member member, Subscribe subscribe, SubTicketType subTicketType) {
        return new SubMemberDto(
                member.getMemberId(),
                member.getNickname(),
                subscribe.getSubId(),
                subTicketType.getSubTicketName()
        );

    }
}

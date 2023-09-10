package com.sklookiesmu.wisefee.common.mapper;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.member.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member joinFromDto(MemberRequestDto dto);

    Member updateFromDto(MemberUpdateRequestDto dto);

    Member updatePasswordFromDto(MemberUpdatePasswordRequestDTO dto);

    Member joinFromGoogleOAuth(OAuthGoogleMemberRequestDto dto);

    MemberResponseDto toDto(Member domain);

}

package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.exception.global.OAuthLoginException;
import com.sklookiesmu.wisefee.common.mapper.MemberMapper;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.member.*;
import com.sklookiesmu.wisefee.service.shared.interfaces.MemberService;
import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "COMM-C :: 회원 API")
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    // COMM-C-X1 :: 회원 전체 조회(관리자)
    @ApiOperation(
            value = "COMM-C-X1 :: 회원 전체 조회(관리자)",
            notes = "본 Api는 관리자 전용으로서 현재까지 DB에 저장된 모든 회원 정보를 조회합니다.<br>" +
                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
                    "조회 데이터:\n" +
                    "```json\n"+
                    "[\n" +
                    "  {\n" +
                    "    \"회원 아이디\": \"memberId\",\n" +
                    "    \"회원 닉네임\": \"nickname\",\n" +
                    "    \"회원 이메일\": \"email\",\n" +
                    "    \"회원 연락처\": \"phone\",\n" +
                    "    \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "    \"회원 생년월일\": \"birth\",\n" +
                    "    \"회원 계정 타입\": \"accountType\",\n" +
                    "    \"회원 인증 타입\": \"authType\",\n" +
                    "    \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "    \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "    \"푸시 알림 토큰\": \"pushMsgToken\",\n" +
                    "    \"회원 상태\": \"memberStatus\",\n" +
                    "    \"생성일\": \"createdAt\",\n" +
                    "    \"수정일\": \"updatedAt\"\n" +
                    "  }\n" +
                    "]\n"+
                    "```\n"
    )
    @GetMapping("/api/v1/member/manager")
    public ResponseEntity<List<MemberResponseDto>> findMembers(
            @ApiParam(value = "정렬 순서 (asc 또는 desc)", defaultValue = "asc")
            @RequestParam(value = "order", defaultValue = "asc") String order
    ){
        return ResponseEntity.ok(memberService.getMembers(order).stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList()));
    }


    // COMM-C-X2 :: 해당 ID의 회원 조회(관리자)
    @ApiOperation(
            value = "COMM-C-X2 :: 해당 ID의 회원 조회(관리자)",
            notes = "본 Api는 관리자 전용으로 회원 id를 통해 한 회원의 정보를 조회합니다.<br>" +
                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
                    "조회 데이터:\n" +
                    "```json\n"+
                    "[\n" +
                    "  {\n" +
                    "    \"회원 아이디\": \"memberId\",\n" +
                    "    \"회원 닉네임\": \"nickname\",\n" +
                    "    \"회원 이메일\": \"email\",\n" +
                    "    \"회원 연락처\": \"phone\",\n" +
                    "    \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "    \"회원 생년월일\": \"birth\",\n" +
                    "    \"회원 계정 타입\": \"accountType\",\n" +
                    "    \"회원 인증 타입\": \"authType\",\n" +
                    "    \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "    \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "    \"푸시 알림 토큰\": \"pushMsgToken\",\n" +
                    "    \"회원 상태\": \"memberStatus\",\n" +
                    "    \"생성일\": \"createdAt\",\n" +
                    "    \"수정일\": \"updatedAt\"\n" +
                    "  }\n" +
                    "]\n"+
                    "```\n"
    )
    @GetMapping("/api/v1/member/manager/{id}")
    public ResponseEntity<MemberResponseDto> findMemberByIdAsManager(
            @ApiParam(value = "회원 PK", required = true)
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(memberMapper.toDto(memberService.getMember(id)));
    }

    // COMM-C-U1 :: 해당 ID의 회원 조회(회원)
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "COMM-C-U1 :: 해당 ID의 회원 조회(회원)",
            notes = "본 Api는 회원 전용으로 id를 통해 자신의 정보를 전부 조회합니다.<br>" +
                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
                    "조회 데이터:\n" +
                    "```json\n"+
                    "[\n" +
                    "  {\n" +
                    "    \"회원 아이디\": \"memberId\",\n" +
                    "    \"회원 닉네임\": \"nickname\",\n" +
                    "    \"회원 이메일\": \"email\",\n" +
                    "    \"회원 연락처\": \"phone\",\n" +
                    "    \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "    \"회원 생년월일\": \"birth\",\n" +
                    "    \"회원 계정 타입\": \"accountType\",\n" +
                    "    \"회원 인증 타입\": \"authType\",\n" +
                    "    \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "    \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "    \"푸시 알림 토큰\": \"pushMsgToken\",\n" +
                    "    \"회원 상태\": \"memberStatus\",\n" +
                    "    \"생성일\": \"createdAt\",\n" +
                    "    \"수정일\": \"updatedAt\"\n" +
                    "  }\n" +
                    "]\n"+
                    "```\n"
    )
    @GetMapping("/api/v1/member/{id}")
    public ResponseEntity<MemberResponseDto> findMemberByIdAsUser(
            @ApiParam(value = "회원 PK", required = true)
            @PathVariable("id") Long id
    ){
        memberService.validateCurrentUser(id);
        return ResponseEntity.ok(memberMapper.toDto(memberService.getMember(id)));
    }

    // COMM-C-U2 :: 회원 신규 추가(가입)
    @ApiOperation(
            value = "COMM-C-U2 :: 회원 신규 추가(가입)",
            notes = "본 API는 새로운 회원을 데이터베이스에 추가하는 기능입니다.\n\n" +
                    "이메일 인증 여부나, 알림 수신 여부는 일단 미개발 상태이시므로, TRUE/FALSE로 넘겨주시면 됩니다." +
                    "입력 데이터:\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",               // 닉네임은 필수 입력 값입니다.\n" +
                    "  \"회원 이메일\": \"email\",                  // 이메일은 aaa@aaa.com 형식을 지켜야 합니다.\n" +
                    "  \"회원 연락처\": \"phone\",                  // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",       // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 생년월일\": \"birth\",                 // 생년월일은 yyyyMMdd 형식으로 입력해야 합니다.\n" +
                    "  \"회원 비밀번호\": \"password\",              // 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.\n" +
                    "  \"회원 계정 타입\": \"accountType\",          // 계정 타입은 CONSUMER, SELLER(고객계정/매장계정) 중 하나여야 합니다.\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",         // 이메일 인증 여부는 TURE, FALSE 중 하나여야 합니다.\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\"        // 알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.\n" +
                    "}\n" +
                    "```\n\n<hr>" +
                    "2023-08-30 : API 스펙 변경 : 더 이상 authType(회원 인증 타입)을 요청으로 받지 않습니다."
    )
    @PostMapping("/api/v1/member")
    public ResponseEntity<Long> addMember(@Valid @RequestBody MemberRequestDto member){
        return ResponseEntity.ok(memberService.join(memberMapper.joinFromDto(member)));
    }

//    // COMM-C-U3 :: 해당 Email 의 회원 조회(회원)
//    // TODO 프론트에서 사용하지 않으면 삭제, 사용할 시 validateCurrentUserByEmail 추가해야 함. 그리고 ResponseDto도 체크해야함.
//    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
//    @ApiOperation(
//            value = "COMM-C-U3 :: 해당 Email의 회원 조회(회원)",
//            notes = "본 Api는 회원 전용으로 Email을 통해 회원의 정보를 조회합니다.<br>" +
//                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
//                    "조회 데이터:\n" +
//                    "```json\n"+
//                    "{\n" +
//                    "  \"회원 닉네임\": \"nickname\",\n" +
//                    "  \"회원 연락처\": \"phone\",\n" +
//                    "  \"회원 사무용 연락처\": \"phoneOffice\",\n" +
//                    "  \"회원 생년월일\": \"birth\",\n" +
//                    "  \"회원 계정 타입\": \"accountType\",\n" +
//                    "  \"회원 인증 타입\": \"authType\",\n" +
//                    "  \"이메일 인증 여부\": \"isAuthEmail\",\n" +
//                    "  \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
//                    "  }\n" +
//                    "```\n"
//    )
//    @GetMapping("/api/v1/member/find/{email}")
//    public ResponseEntity<MemberEmailResponseDto> findMemberByEmail(
//            @ApiParam(value = "회원 Email", required = true)
//            @PathVariable("email") String email
//    ){
//        MemberEmailResponseDto result = memberMapper.toDto(memberService.getMemberByEmail(email));
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    // COMM-C-U4 :: 회원 수정(회원)
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "COMM-C-U4 :: 회원 수정(회원)",
            notes = "본 Api는 회원 전용으로 회원 id를 통해 회원의 정보를 수정합니다.<br>" +
                    "id를 통해 회원 정보를 json파일에 맞춰 수정합니다. <br><br>" +
                    "입력 데이터:\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",               // 닉네임은 필수 입력 값입니다.\n" +
                    "  \"회원 연락처\": \"phone\",                  // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",       // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 생년월일\": \"birth\",                 // 생년월일은 yyyyMMdd 형식으로 입력해야 합니다.\n" +
                    "  \"회원 계정 타입\": \"accountType\",          // 계정 타입은 CONSUMER, SELLER 중 하나여야 합니다.\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",         // 이메일 인증 여부는 TURE, FALSE 중 하나여야 합니다.\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\"        // 알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.\n" +
                    "}\n" +
                    "```"
    )
    @PutMapping("/api/v1/member/{id}")
    public ResponseEntity<Long> updateMember(
            @ApiParam(value = "회원 Id", required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberUpdateRequestDto member){
        memberService.validateCurrentUser(id);
        return ResponseEntity.ok(memberService.updateMember(id, memberMapper.updateFromDto(member)));
    }

    // COMM-C-U5 :: 회원 비밀번호 수정(회원)
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "COMM-C-U5 :: 회원 비밀번호 수정(회원)",
            notes = "본 Api는 회원 전용으로 회원 id를 통해 회원의 비밀번호를 수정합니다.<br>" +
                    "id를 통해 변경할 비밀번호 정보를 json파일에 맞춰 수정합니다. <br><br>" +
                    "입력 데이터:\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"회원 비밀번호\": \"password\",              // 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.\n" +
                    "}\n" +
                    "```"
    )
    @PutMapping("/api/v1/member/updatePW/{id}")
    public ResponseEntity<Long> updatePasswordAsMember(
            @ApiParam(value = "회원 Id", required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberUpdatePasswordRequestDTO member){
        memberService.validateCurrentUser(id);
        return ResponseEntity.ok(memberService.updatePasswordAsMember(id, memberMapper.updatePasswordFromDto(member)));
    }


    // COMM-C-G1 :: 소셜 회원 가입 : Google
    @ApiOperation(
            value = "COMM-C-G1 :: 소셜 회원 가입 : Google",
            notes = "해당 API는 Google OAuth를 통해 회원가입을 진행합니다.\n" +
                    "Google API를 이용하여 OAuth 인증을 완료하면, 응답으로 AccessToken을 포함한 생년월일 등을 얻을 수도 있을 것입니다. \n" +
                    "자동으로 얻을 수 있는 값은 채워주시고, 없는 값은 유저가 수동으로 입력하게 하여 AccessToken과 함께 회원 가입 요청을 날려주시면 됩니다. \n\n" +
                    "입력 데이터:\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"구글 OAuth Access Token\": \"googleAccessToken\",               // 구글에서 소셜 로그인의 결과로 반환받은 accessToken\n" +
                    "  \"회원 닉네임\": \"nickname\",               // 닉네임은 필수 입력 값입니다.\n" +
                    "  \"회원 연락처\": \"phone\",                  // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",       // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 생년월일\": \"birth\",                 // 생년월일은 yyyyMMdd 형식으로 입력해야 합니다.\n" +
                    "  \"회원 계정 타입\": \"accountType\",          // 계정 타입은 CONSUMER, SELLER(고객계정/매장계정) 중 하나여야 합니다.\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\"        // 알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.\n" +
                    "}\n" +
                    "```"
    )
    @PostMapping("/api/v1/member/google")
    public ResponseEntity<Long> addMemberOAuthGoogle(@Valid @RequestBody OAuthGoogleMemberRequestDto member) {
        Member entity = memberMapper.joinFromGoogleOAuth(member);
        entity.setDefaultAuthType(AuthConstant.AUTH_TYPE_COMMON);
        try {
            return ResponseEntity.ok(memberService.joinGoogle(entity, member.getGoogleAccessToken()));
        } catch (IOException e) {
            throw new OAuthLoginException("Google 로그인 중 오류가 발생했습니다.");
        }
    }

}

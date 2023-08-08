package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.error.ValidateMemberException;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.member.*;
import com.sklookiesmu.wisefee.service.shared.MemberService;
import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "COMM-C :: 회원 API")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    private final ModelMapper modelMapper;

    private final EntityManager em;

    // 관리자 권한.
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
        List<Member> members = memberService.getMembers(order);
        List<MemberResponseDto> result = members.stream()
                .map(e -> modelMapper.map(e, MemberResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 관리자 권한.
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
        Member member = memberService.getMember(id);
        if(member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        MemberResponseDto result = modelMapper.map(member, MemberResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

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
        if(!(id.equals(SecurityUtil.getCurrentMemberPk())))
            throw new ValidateMemberException("invalid ID : The provided ID does not match your current logged-in ID"+id);
        Member member = memberService.getMember(id);
        if(member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        MemberResponseDto result = modelMapper.map(member, MemberResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(
            value = "COMM-C-U2 :: 회원 신규 추가(가입)",
            notes = "본 API는 새로운 회원을 데이터베이스에 추가하는 기능입니다.\n\n" +
                    "입력 데이터:\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",               // 닉네임은 필수 입력 값입니다.\n" +
                    "  \"회원 이메일\": \"email\",                  // 이메일은 aaa@aaa.com 형식을 지켜야 합니다.\n" +
                    "  \"회원 연락처\": \"phone\",                  // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",       // 전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.\n" +
                    "  \"회원 생년월일\": \"birth\",                 // 생년월일은 yyyyMMdd 형식으로 입력해야 합니다.\n" +
                    "  \"회원 비밀번호\": \"password\",              // 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.\n" +
                    "  \"회원 계정 타입\": \"accountType\",          // 계정 타입은 CONSUMER, SELLER 중 하나여야 합니다.\n" +
                    "  \"회원 인증 타입\": \"authType\",             // 회원 인증 타입은 Kakao, Google, Naver 중 하나여야 합니다.\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",         // 이메일 인증 여부는 TURE, FALSE 중 하나여야 합니다.\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\"        // 알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.\n" +
                    "}\n" +
                    "```"
    )
    @PostMapping("/api/v1/member")
    public ResponseEntity<Long> addMember(@Valid @RequestBody MemberRequestDto member){
        Member entity = modelMapper.map(member, Member.class);
        Long id = memberService.join(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "COMM-C-U3 :: 해당 Email의 회원 조회(회원)",
            notes = "본 Api는 회원 전용으로 Email을 통해 회원의 정보를 조회합니다.<br>" +
                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
                    "조회 데이터:\n" +
                    "```json\n"+
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",\n" +
                    "  \"회원 연락처\": \"phone\",\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "  \"회원 생년월일\": \"birth\",\n" +
                    "  \"회원 계정 타입\": \"accountType\",\n" +
                    "  \"회원 인증 타입\": \"authType\",\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "  }\n" +
                    "```\n"
    )
    @GetMapping("/api/v1/member/find/{email}")
    public ResponseEntity<MemberEmailResponseDto> findMemberByEmail(
            @ApiParam(value = "회원 Email", required = true)
            @PathVariable("email") String email
    ){
        Optional<Member> optionalMember = memberService.getMemberByEmail(email);
        if(optionalMember.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Member member = optionalMember.get();
        MemberEmailResponseDto result = modelMapper.map(member, MemberEmailResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

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

        if(!(id.equals(SecurityUtil.getCurrentMemberPk())))
            throw new ValidateMemberException("invalid ID : The provided ID does not match your current logged-in ID"+id);

        Member entity = modelMapper.map(member, Member.class);
        Long result =  memberService.updateMember(id, entity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

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
    @PutMapping("/api/v1/member/updatePW{id}")
    public ResponseEntity<Long> updatePasswordAsMember(
            @ApiParam(value = "회원 Id", required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberUpdatePasswordRequestDTO member){

        if(!(id.equals(SecurityUtil.getCurrentMemberPk())))
            throw new ValidateMemberException("invalid ID : The provided ID does not match your current logged-in ID"+id);

        Member entity = modelMapper.map(member, Member.class);
        Long result =  memberService.updatePasswordAsMember(id, entity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

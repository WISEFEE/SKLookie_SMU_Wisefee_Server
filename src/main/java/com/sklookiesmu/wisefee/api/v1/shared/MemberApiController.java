package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.member.MemberEmailRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.MemberRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.MemberResponseDto;
import com.sklookiesmu.wisefee.dto.shared.member.MemberUpdateRequestDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.service.shared.MemberService;
import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "회원 API")
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    private final ModelMapper modelMapper;

    private final EntityManager em;

    // 관리자 권한.
    @ApiOperation(
            value = "회원 전체 조회(관리자)",
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
                    "    \"회원 비밀번호\": \"password\",\n" +
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
    @GetMapping("/api/v1/member")
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
            value = "해당 ID의 회원 조회(관리자)",
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
                    "    \"회원 비밀번호\": \"password\",\n" +
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
    public ResponseEntity<MemberResponseDto> findMemberById(
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

    @ApiOperation(
            value = "회원 신규 추가",
            notes = "본 Api는 새로운 회원을 데이터베이스에 추가하는 기능입니다.<br>" +
                    "json 형식으로 회원 정보를 입력받아 DB에 회원을 등록합니다.<br><br>" +
                    "입력 데이터:\n" +
                    "```json\n"+
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",\n" +
                    "  \"회원 이메일\": \"email\",\n" +
                    "  \"회원 연락처\": \"phone\",\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "  \"회원 생년월일\": \"birth\",\n" +
                    "  \"회원 비밀번호\": \"password\",\n" +
                    "  \"회원 계정 타입\": \"accountType\",\n" +
                    "  \"회원 인증 타입\": \"authType\",\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "  }\n" +
                    "```\n"+
                    "새로운 회원이 성공적으로 추가되면 새로운 회원의 아이디를 반환합니다.\n"
    )
    @PostMapping("/api/v1/member")
    public ResponseEntity<Long> addMember(@RequestBody MemberRequestDto member){
        Member entity = modelMapper.map(member, Member.class);
        Long id = memberService.join(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "해당 Email의 회원 조회(회원)",
            notes = "본 Api는 회원 전용으로 Email을 통해 회원의 정보를 조회합니다.<br>" +
                    "회원 정보는 다음과 같이 json 형식으로 반환됩니다. <br><br>" +
                    "조회 데이터:\n" +
                    "```json\n"+
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",\n" +
                    "  \"회원 연락처\": \"phone\",\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "  \"회원 생년월일\": \"birth\",\n" +
                    "  \"회원 비밀번호\": \"password\",\n" +
                    "  \"회원 계정 타입\": \"accountType\",\n" +
                    "  \"회원 인증 타입\": \"authType\",\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "  }\n" +
                    "```\n"
    )
    @GetMapping("/api/v1/member/find/{email}")
    public ResponseEntity<MemberEmailRequestDto> findMemberByEmail(
            @ApiParam(value = "회원 Email", required = true)
            @PathVariable("email") String email
    ){
        Optional<Member> optionalMember = memberService.getMemberByEmail(email);
        if(optionalMember.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Member member = optionalMember.get();
        MemberEmailRequestDto result = modelMapper.map(member, MemberEmailRequestDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @ApiOperation(
            value = "회원 수정(회원)",
            notes = "본 Api는 회원 전용으로 Email을 통해 회원의 정보를 수정합니다.<br>" +
                    "Email을 통해 회원 정보를 json파일에 맞춰 수정합니다. <br><br>" +
                    "입력 데이터:\n" +
                    "```json\n"+
                    "{\n" +
                    "  \"회원 닉네임\": \"nickname\",\n" +
                    "  \"회원 연락처\": \"phone\",\n" +
                    "  \"회원 사무용 연락처\": \"phoneOffice\",\n" +
                    "  \"회원 생년월일\": \"birth\",\n" +
                    "  \"회원 비밀번호\": \"password\",\n" +
                    "  \"회원 계정 타입\": \"accountType\",\n" +
                    "  \"회원 인증 타입\": \"authType\",\n" +
                    "  \"이메일 인증 여부\": \"isAuthEmail\",\n" +
                    "  \"알림 수신 여부\": \"isAllowPushMsg\",\n" +
                    "  }\n" +
                    "```\n"
    )
    @PostMapping("/api/v1/member/{email}")
    public ResponseEntity<Long> updateMember(
            @ApiParam(value = "회원 Email", required = true)
            @PathVariable("email") String email,
            @RequestBody MemberUpdateRequestDto member){
        Member entity = modelMapper.map(member, Member.class);
        Long result =  memberService.updateMember(email, entity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

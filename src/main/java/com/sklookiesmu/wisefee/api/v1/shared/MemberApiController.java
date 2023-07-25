package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.domain.Member;
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

    @ApiOperation(value = "회원 전체 조회")
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


    @ApiOperation(value = "해당 ID의 회원 조회")
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

    @ApiOperation(value = "해당 Email의 회원 조회")
    @GetMapping("/api/v1/member/find/{email}")
    public ResponseEntity<MemberRequestDto> findMemberByEmail(
            @ApiParam(value = "회원 Email", required = true)
            @PathVariable("email") String email
    ){
        Optional<Member> optionalMember = memberService.getMemberByEmail(email);
        if(optionalMember.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Member member = optionalMember.get();
        MemberRequestDto result = modelMapper.map(member, MemberRequestDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "회원 신규 추가")
    @PostMapping("/api/v1/member")
    public ResponseEntity<Long> addMember(@RequestBody MemberRequestDto member){
        Member entity = modelMapper.map(member, Member.class);
        Long id = memberService.join(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @ApiOperation(value = "회원 수정")
    @PostMapping("/api/v1/member/{id}")
    public ResponseEntity<Long> updateMember(
            @ApiParam(value = "회원 PK", required = true)
            @PathVariable("id") Long id,
            @RequestBody MemberUpdateRequestDto member){
        Member entity = modelMapper.map(member, Member.class);
        Long result =  memberService.updateMember(id, entity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}

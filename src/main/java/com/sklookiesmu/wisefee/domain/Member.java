package com.sklookiesmu.wisefee.domain;
import com.sklookiesmu.wisefee.common.enums.member.AccountType;
import com.sklookiesmu.wisefee.common.enums.member.AuthType;
import com.sklookiesmu.wisefee.common.enums.member.MemberStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "PHONE_OFFICE")
    private String phoneOffice;

    @Column(name = "BIRTH", nullable = false)
    private String birth;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTH_TYPE", nullable = false)
    private AuthType authType;

    @Column(name = "IS_AUTH_EMAIL", nullable = false)
    private Boolean isAuthEmail;

    @Column(name = "IS_ALLOW_PUSH_MSG", nullable = false)
    private Boolean isAllowPushMsg;

    @Column(name = "PUSH_MSG_TOKEN")
    private String pushMsgToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_STATUS")
    private MemberStatus memberStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Builder
    public Member(String nickname, String email, String phone, String phoneOffice, String birth, String password, AccountType accountType, AuthType authType, Boolean isAuthEmail, Boolean isAllowPushMsg, String pushMsgToken, MemberStatus memberStatus) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.phoneOffice = phoneOffice;
        this.birth = birth;
        this.password = password;
        this.accountType = accountType;
        this.authType = authType;
        this.isAuthEmail = isAuthEmail;
        this.isAllowPushMsg = isAllowPushMsg;
        this.pushMsgToken = pushMsgToken;
        this.memberStatus = memberStatus;
    }

    /**
     * 생성일, 수정일 값 세팅
     */
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 연관관계 매핑
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<Cafe> cafes = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<File> files = new ArrayList<>();
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Cart cart;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDR_ID")
    private Address address;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<Subscribe> subscribes = new ArrayList<>();


    /**
     * 비즈니스 로직
     */
    public Long updateMember(Member member){
        this.nickname = member.nickname;
        this.phone = member.phone;
        this.phoneOffice = member.phoneOffice;
        this.birth = member.birth;
        this.isAuthEmail = member.isAuthEmail;
        this.isAllowPushMsg = member.isAllowPushMsg;
        return memberId;
    }

    public Long encodePassword(String encodePassword) {
        this.password = encodePassword;
        return memberId;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setDefaultAuthType(AuthType authType) {
        this.authType = authType;
    }

    public void joinOAuth(String email, String password, boolean isAuthEmail, AuthType authType) {
        this.email = email;
        this.password = password;
        this.isAuthEmail = isAuthEmail;
        this.authType = authType;
    }
}
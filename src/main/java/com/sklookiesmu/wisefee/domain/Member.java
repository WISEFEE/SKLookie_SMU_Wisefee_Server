package com.sklookiesmu.wisefee.domain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "PHONE_OFFICE")
    private String phoneOffice;

    @Column(name = "BIRTH", nullable = false)
    private String birth;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @Column(name = "AUTH_TYPE", nullable = false)
    private String authType;

    @Column(name = "IS_AUTH_EMAIL", nullable = false)
    private String isAuthEmail;

    @Column(name = "IS_ALLOW_PUSH_MSG", nullable = false)
    private String isAllowPushMsg;

    @Column(name = "PUSH_MSG_TOKEN")
    private String pushMsgToken;

    @Column(name = "MEMBER_STATUS")
    private String memberStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

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
    private List<Cafe> cafes = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Cart cart;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDR_ID")
    private Address address;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Subscribe> subscribes = new ArrayList<>();


    /**
     * 비즈니스 로직
     */
    public Long updateMember(Member member){
        this.nickname = member.nickname;
        this.email = member.email;
        this.phone = member.phone;
        this.phoneOffice = member.phoneOffice;
        this.birth = member.birth;
        this.password = member.password;
        this.accountType = member.accountType;
        this.authType = member.authType;
        this.isAuthEmail = member.isAuthEmail;
        this.isAllowPushMsg = member.isAllowPushMsg;
        return 1L;
    }
}
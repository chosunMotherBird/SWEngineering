package com.swteam6.EVCharger.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Author: Song Hak Hyeon
 * DB에 들어가는 User Table에 대한 Domain 객체
 * ORM 프레임워크인 JPA의 기능을 활용하여 객체의 틀을 가지고 DB에 해당 table을 자동 생성합니다.
 */
@Entity
@NoArgsConstructor // Lombok 라이브러리에서 제공하는 기본 생성자 자동 생성 기능
@Getter // Lombok 라이브러리에서 제공하는 getter 메소드
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Email // hibernate에서 제공하는 유효성 검사를 위한 애노테이션 -> @Email, @NotNull
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String userPass;

    @NotNull
    @Column(nullable = false)
    private String userName;

    @NotNull
    @Column(nullable = false)
    private String phoneNum;

    // @Builder 패턴으로 생성자 대신 객체를 유연하게 생성함
    @Builder
    public UserEntity(String email, String userName, String userPass, String phoneNum) {
        this.email = email;
        this.userName = userName;
        this.userPass = userPass;
        this.phoneNum = phoneNum;
    }

}

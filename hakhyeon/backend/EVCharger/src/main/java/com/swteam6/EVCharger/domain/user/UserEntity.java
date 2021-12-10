package com.swteam6.EVCharger.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Email // 유효성 검사를 위한 애노테이션 -> @Email, @NotNull
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

    @Builder
    public UserEntity(String email, String userName, String userPass, String phoneNum) {
        this.email = email;
        this.userName = userName;
        this.userPass = userPass;
        this.phoneNum = phoneNum;
    }

}

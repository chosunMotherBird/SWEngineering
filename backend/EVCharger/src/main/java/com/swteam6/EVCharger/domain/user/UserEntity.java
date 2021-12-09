package com.swteam6.EVCharger.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "User")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userPass;

    @Column(nullable = false)
    private String userName;

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

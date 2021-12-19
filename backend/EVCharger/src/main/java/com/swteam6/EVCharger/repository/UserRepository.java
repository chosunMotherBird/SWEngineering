package com.swteam6.EVCharger.repository;

import com.swteam6.EVCharger.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Song Hak Hyeon
 * ORM 프레임워크를 확장한 Spring Data JPA의 기능으로
 * JpaRepository를 상속받은 interface는 기본적인 CRUD 기능을 따로 선언하지 않아도 사용이 가능합니다.
 * interface 내부에 메소드를 선언해두면 그에 맞는 sql 쿼리를 JPA가 자동으로 수행합니다.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * 회원 가입을 처리를 위해 만든 커스텀 메소드로 email을 기준으로 select query 수행
     */
    UserEntity findByEmail(String email);

    /**
     * 로그인 처리를 위해 만든 커스텀 메소드로
     * email, userPass을 기준으로 select query 수행
     */
    UserEntity findByEmailAndUserPass(String email, String userPass);
}

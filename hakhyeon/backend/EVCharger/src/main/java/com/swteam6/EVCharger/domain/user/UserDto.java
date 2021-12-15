package com.swteam6.EVCharger.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Author: Song Hak Hyeon
 * DB에 직접 값을 넣는 Entity는 불변 객체로 두고,
 * 이와 별개로 Client와 Server간의 데이터 교환을 위해 DTO(Data Transfer Object)를 구현함.
 * 해당하는 Request/Response 마다 Dto 클래스를 만들지 않고 inner class 구조로 유지/보수 하기 편하게 설계하였습니다.
 */
public class UserDto {

    // Client의 회원가입 요청을 수행하기 위한 dto
    @Getter
    @NoArgsConstructor
    public static class SignUpRequest {
        private String email;
        private String userPass;
        private String userName;
        private String phoneNum;

        @Builder
        public SignUpRequest(String email, String userPass, String userName, String phoneNum) {
            this.email = email;
            this.userPass = userPass;
            this.userName = userName;
            this.phoneNum = phoneNum;
        }

        // request 데이터를 DB에 처리 하기 위해 dto를 entity로 변환하기 위한 메소드
        public UserEntity toEntity() {
            return UserEntity.builder()
                    .email(email)
                    .userPass(userPass)
                    .userName(userName)
                    .phoneNum(phoneNum)
                    .build();
        }
    }

    // Client의 로그인 요청을 수행하기 위한 dto
    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String userPass;

        @Builder
        public LoginRequest(String email, String userPass) {
            this.email = email;
            this.userPass = userPass;
        }

        // request 데이터를 DB에 처리 하기 위해 dto를 entity로 변환하기 위한 메소드
        public UserEntity toEntity() {
            return UserEntity.builder()
                    .email(email)
                    .userPass(userPass)
                    .build();
        }
    }

    // Client에게 response를 전달하기 위한 dto
    @Getter
    public static class Response {
        private String email;
        private String userPass;
        private String userName;
        private String phoneNum;

        public Response(UserEntity user) {
            this.email = user.getEmail();
            this.userPass = user.getUserPass();
            this.userName = user.getUserName();
            this.phoneNum = user.getPhoneNum();
        }
    }
}

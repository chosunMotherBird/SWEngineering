package com.swteam6.EVCharger.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

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

        public UserEntity toEntity() {
            return UserEntity.builder()
                    .email(email)
                    .userPass(userPass)
                    .userName(userName)
                    .phoneNum(phoneNum)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private String userId;
        private String userPass;
        private String userName;
        private String phoneNum;

        public Response(UserEntity user) {
            this.userId = user.getEmail();
            this.userPass = user.getUserPass();
            this.userName = user.getUserName();
            this.phoneNum = user.getPhoneNum();
        }
    }
}

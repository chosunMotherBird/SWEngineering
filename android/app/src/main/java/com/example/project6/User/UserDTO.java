package com.example.project6.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * 유저 정보를 전달, 받기 위한 DTO
 * Intent 로 정보를 주고 받기 위하여 객체를 Serializable 함.
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID=1L;
    String email;
    String password;
    String name;
    String phone;

    /**
     * 맨 처음 userDTO 의 값은 모두 "" 값을 가져야함. 로그인 되지 않았기 떄문.
     */
    public UserDTO() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.phone = "";
    }

    public UserDTO(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(email, userDTO.email) && Objects.equals(password, userDTO.password) && Objects.equals(name, userDTO.name) && Objects.equals(phone, userDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, phone);
    }
}


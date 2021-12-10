package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.domain.user.UserEntity;
import com.swteam6.EVCharger.exception.EmailDuplicationException;
import com.swteam6.EVCharger.exception.UserNotFoundException;
import com.swteam6.EVCharger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserEntity create(UserDto.SignUpRequest dto) throws Exception {
        if (isExistedEmail(dto))
            throw new EmailDuplicationException(dto.getEmail());
        return repository.save(dto.toEntity());
    }

    /**
     * 회원가입 처리 시 중복된 User 정보에 대한 예외처리 핸들링
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isExistedEmail(UserDto.SignUpRequest dto) throws Exception {
        return repository.findByEmail(dto.getEmail()) != null;
    }

    /**
     * login 처리 시 조회할 수 없는 User 에 대한 예외처리 핸들링
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isExistedEmailV2(UserDto.LoginRequest dto) throws Exception {
        return repository.findByEmail(dto.getEmail()) != null;
    }

    @Override
    public UserEntity login(UserDto.LoginRequest dto) throws Exception {
        if (!isExistedEmailV2(dto)) {
            throw new UserNotFoundException(dto.getEmail());
        }
        return repository.findByEmailAndUserPass(dto.getEmail(), dto.getUserPass());
    }
}

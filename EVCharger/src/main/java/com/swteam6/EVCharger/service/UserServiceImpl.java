package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.domain.user.UserEntity;
import com.swteam6.EVCharger.domain.user.exception.EmailDuplicationException;
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

    @Override
    @Transactional(readOnly = true)
    public boolean isExistedEmail(UserDto.SignUpRequest dto) throws Exception {
        return repository.findByEmail(dto.toEntity().getEmail()) != null;
    }

}

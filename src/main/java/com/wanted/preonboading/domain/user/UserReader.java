package com.wanted.preonboading.domain.user;

import com.wanted.preonboading.repository.UserRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public UserInfo read(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );

        return UserInfo.of(user);
    }
}

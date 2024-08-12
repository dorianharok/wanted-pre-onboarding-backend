package com.wanted.preonboading.domain.user;

import com.wanted.preonboading.repository.UserRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserReaderTest {

    @Autowired
    private UserReader userReader;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("유저 아이디를 통해 유저 정보를 가져온다")
    void read() {
        // given
        User user = userRepository.save(new User("유저"));
        Long id = user.getId();

        // when
        User read = userReader.read(id);

        // then
        assertThat(read.getUsername()).isEqualTo("유저");
    }

    @Test
    @DisplayName("유저 아이디가 존재하지 않는 경우 예외가 발생한다.")
    void read2() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> userReader.read(id))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}
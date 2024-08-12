package com.wanted.preonboading.domain.company;

import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CompanyFinderTest {
    
    @Autowired
    private CompanyFinder companyFinder;
    
    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회사 아이디로 회사를 찾아온다.")
    void find() {
        // given
        Company company = createCompany("테스트 회사");
        Company saved = companyRepository.save(company);
        Long id = saved.getId();

        // when
        Company readCompany = companyFinder.find(id);

        // then
        assertThat(readCompany)
                .extracting("id", "name")
                .containsExactly(id, company.getName());
    }

    @Test
    @DisplayName("아이디가 존재하지 않는 경우 예외가 발생한다.")
    void find2() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> companyFinder.find(id))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.COMPANY_NOT_FOUND.getMessage());
    }
    
    private Company createCompany(String name) {
        return new Company(name);
    }
}
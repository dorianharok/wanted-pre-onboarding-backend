package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.repository.JobPostingRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobPostingAppenderTest {

    @Autowired
    private JobPostingAppender jobPostingAppender;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        jobPostingRepository.deleteAllInBatch();
        companyRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("채용 공고를 등록한다.")
    void append() {
        // given
        Company company = companyRepository.save(new Company("테스트"));
        JobPostingInfo jobPostingInfo = new JobPostingInfo(
                null,
                "백엔드 주니어 개발자",
                1000000,
                "원티드에서 주니어 개발자를 채용합니다...",
                "Spring",
                company.getId()
        );

        // when
        Long jobPostingId = jobPostingAppender.append(jobPostingInfo);

        // then
        assertThat(jobPostingId).isNotNull();

        List<JobPosting> postings = jobPostingRepository.findAll();
        assertThat(postings.size()).isOne();

        JobPosting jobPosting = postings.get(0);
        assertThat(jobPosting)
                .extracting("id", "positionTitle", "reward", "jobDescription", "requiredSkill")
                .containsExactly(jobPostingId, jobPostingInfo.positionTitle(), jobPostingInfo.reward(), jobPostingInfo.jobDescription(), jobPostingInfo.requiredSkill());
        assertThat(jobPosting.getCompany().getId()).isEqualTo(company.getId());
    }

    @Test
    @DisplayName("회사가 존재하지 않는 경우 채용 공고를 등록할 수 없다.")
    void append2() {
        // given
        Long companyId = 1L;
        JobPostingInfo jobPostingInfo = new JobPostingInfo(
                null,
                "백엔드 주니어 개발자",
                1000000,
                "원티드에서 주니어 개발자를 채용합니다...",
                "Spring",
                1L
        );

        // when & then
        assertThatThrownBy(() -> jobPostingAppender.append(jobPostingInfo))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.COMPANY_NOT_FOUND.getMessage());
    }
}
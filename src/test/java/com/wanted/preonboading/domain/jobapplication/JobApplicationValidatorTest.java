package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.domain.jobposting.JobPosting;
import com.wanted.preonboading.domain.user.User;
import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.repository.JobApplicationRepository;
import com.wanted.preonboading.repository.JobPostingRepository;
import com.wanted.preonboading.repository.UserRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobApplicationValidatorTest {

    @Autowired
    private JobApplicationValidator jobApplicationValidator;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        jobApplicationRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        jobPostingRepository.deleteAllInBatch();
        companyRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("이미 지원한 채용공고에는 다시 지원할 수 없다.")
    void validate() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        JobPosting jobPosting = jobPostingRepository.save(
                createJobPosting("테스트", 1000000, "테스트", "테스트", company)
        );
        User user = userRepository.save(new User("테스트유저"));
        jobApplicationRepository.save(JobApplication.of(jobPosting, user));

        // when
        assertThatThrownBy(() -> jobApplicationValidator.validate(jobPosting.getId(), user.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.ALREADY_APPLIED.getMessage());
    }

    private JobPosting createJobPosting(String positionTitle, Integer reward, String jobDescription, String requiredSkill, Company company) {
        return JobPosting.builder()
                .positionTitle(positionTitle)
                .reward(reward)
                .jobDescription(jobDescription)
                .requiredSkill(requiredSkill)
                .company(company)
                .build();
    }
}
package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.domain.jobposting.JobPosting;
import com.wanted.preonboading.domain.user.User;
import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.repository.JobApplicationRepository;
import com.wanted.preonboading.repository.JobPostingRepository;
import com.wanted.preonboading.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobApplicationAppenderTest {

    @Autowired
    private JobApplicationAppender jobApplicationAppender;

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
    @DisplayName("지원 공고에 지원한다.")
    void apply() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        JobPosting jobPosting = jobPostingRepository.save(
                createJobPosting("테스트", 1000000, "테스트", "테스트", company)
        );
        User user = userRepository.save(new User("테스트유저"));

        // when
        jobApplicationAppender.apply(jobPosting.getId(), user.getId());

        // then
        List<JobApplication> jobApplications = jobApplicationRepository.findAll();
        assertThat(jobApplications).hasSize(1);
        JobApplication jobApplication = jobApplications.get(0);
        assertThat(jobApplication.getUser().getId()).isEqualTo(user.getId());
        assertThat(jobApplication.getJobPosting().getId()).isEqualTo(jobPosting.getId());
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
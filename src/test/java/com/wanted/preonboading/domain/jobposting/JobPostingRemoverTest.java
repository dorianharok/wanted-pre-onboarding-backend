package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.repository.JobPostingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobPostingRemoverTest {

    @Autowired
    private JobPostingRemover jobPostingRemover;

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
    @DisplayName("채용 공고를 삭제한다.")
    void remove() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        JobPosting jobPosting = jobPostingRepository.save(createJobPosting("테스트", 1000000, "테스트", "테스트", company));

        // when
        jobPostingRemover.remove(jobPosting.getId());

        // then
        List<JobPosting> postings = jobPostingRepository.findAll();
        assertThat(postings.size()).isZero();
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
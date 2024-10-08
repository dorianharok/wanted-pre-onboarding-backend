package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.repository.JobPostingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobPostingUpdaterTest {

    @Autowired
    private JobPostingUpdater jobPostingUpdater;

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
    @DisplayName("채용공고를 수정한다.")
    void update() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        JobPosting jobPosting = jobPostingRepository.save(createJobPosting("테스트", 1000000, "테스트", "테스트", company));
        Long id = jobPosting.getId();
        JobPostingInfo updateInfo = new JobPostingInfo(
                id,
                "테스트1",
                1500000,
                "테스트1",
                "테스트",
                company.getId()
        );

        // when
        JobPosting jobPosting1 = jobPostingUpdater.update(updateInfo);

        // then
        JobPostingInfo find = JobPostingInfo.of(jobPostingRepository.findById(id).get());
        assertThat(find)
                .extracting("id", "positionTitle", "reward", "jobDescription", "requiredSkill")
                .containsExactly(id, updateInfo.positionTitle(), updateInfo.reward(), updateInfo.jobDescription(), updateInfo.requiredSkill());
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
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
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobPostingReaderTest {

    @Autowired
    private JobPostingRemover jobPostingRemover;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobPostingReader jobPostingReader;

    @AfterEach
    void tearDown() {
        jobPostingRepository.deleteAllInBatch();
        companyRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("채용 공고를 읽어온다")
    void readJobPostings() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        jobPostingRepository.saveAll(List.of(
                createJobPosting("테스트", 1000000, "테스트", "테스트", company),
                createJobPosting("테스트2", 2000000, "테스트2", "테스트2", company)
        ));

        // when
        List<JobPostingInfo> jobPostingInfos = jobPostingReader.readJobPostings();

        // then
        assertThat(jobPostingInfos)
                .extracting("positionTitle", "reward", "jobDescription", "requiredSkill", "companyId")
                .containsExactlyInAnyOrder(
                        tuple("테스트", 1000000, "테스트", "테스트", company.getId()),
                        tuple("테스트2", 2000000, "테스트2", "테스트2", company.getId())
                );
    }

    @Test
    @DisplayName("채용 공고가 없을 경우 빈 리스트를 반환한다.")
    void readJobPostings2() {
        // given

        // when
        List<JobPostingInfo> jobPostingInfos = jobPostingReader.readJobPostings();

        // then
        assertThat(jobPostingInfos.size()).isZero();
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
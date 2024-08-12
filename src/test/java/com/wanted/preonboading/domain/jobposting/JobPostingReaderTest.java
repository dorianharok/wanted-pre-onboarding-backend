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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
class JobPostingReaderTest {

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
        List<JobPosting> jobPostings = jobPostingReader.readJobPostings();

        // then
        assertThat(jobPostings)
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
        List<JobPosting> jobPostings = jobPostingReader.readJobPostings();

        // then
        assertThat(jobPostings.size()).isZero();
    }

    @Test
    @DisplayName("채용 공고 Id로 채용 공고 내용을 반환한다.")
    void read() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        JobPosting jobPosting = jobPostingRepository.save(
                createJobPosting("테스트", 1000000, "테스트", "테스트", company)
        );
        Long id = jobPosting.getId();

        // when
        JobPosting read = jobPostingReader.read(id);

        // then
        assertThat(read)
                .extracting("positionTitle", "reward", "jobDescription", "requiredSkill", "companyId")
                .containsExactly("테스트", 1000000, "테스트", "테스트", company.getId());
    }

    @Test
    @DisplayName("채용 공고 Id가 없는 경우 예외가 발생한다.")
    void read2() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> jobPostingReader.read(id))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.COMPANY_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("회사가 등록한 다른 채용공고 아이디를 가져온다.")
    void getOtherPostingIds() {
        // given
        Company company = companyRepository.save(new Company("테스트회사"));
        List<Long> ids = jobPostingRepository.saveAll(List.of(
                createJobPosting("테스트", 1000000, "테스트", "테스트", company),
                createJobPosting("테스트2", 2000000, "테스트2", "테스트2", company),
                createJobPosting("테스트3", 3000000, "테스트3", "테스트3", company)
        )).stream().map(JobPosting::getId).toList();
        Long exceptId = ids.get(0);

        // when
        List<Long> otherPostingIds = jobPostingReader.getOtherPostingIds(company.getId(), exceptId);

        // then
        assertThat(otherPostingIds.size()).isEqualTo(2);
        assertThat(otherPostingIds).containsAll(List.of(ids.get(1), ids.get(2)));
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
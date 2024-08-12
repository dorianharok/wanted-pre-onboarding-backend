package com.wanted.preonboading.repository;

import com.wanted.preonboading.domain.jobposting.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    @Query("SELECT j.id FROM JobPosting j WHERE j.company.id = :companyId AND j.id != :id")
    List<Long> findAllIdsByCompanyIdAndIdNot(@Param("companyId") Long companyId, @Param("id") Long id);
}

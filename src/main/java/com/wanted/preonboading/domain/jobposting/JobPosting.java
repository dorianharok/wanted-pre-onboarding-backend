package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_posting")
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "position_title", nullable = false)
    private String positionTitle;
    @Column(nullable = false)
    private BigDecimal reward;
    @Lob
    @Column(name = "job_description", nullable = false)
    private String jobDescription;
    @Column(name = "required_skill", nullable = false)
    private String requiredSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "company_id", nullable = false, updatable = false)
    private Company company;

    @Builder
    private JobPosting(String positionTitle, BigDecimal reward, String jobDescription, String requiredSkill, Company company) {
        this.positionTitle = positionTitle;
        this.reward = reward;
        this.jobDescription = jobDescription;
        this.requiredSkill = requiredSkill;
        this.company = company;
    }
}

package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.domain.jobposting.JobPosting;
import com.wanted.preonboading.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_application")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private JobPosting jobPosting;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static JobApplication of(JobPosting jobPosting, User user) {
        return JobApplication.builder()
                .jobPosting(jobPosting)
                .user(user)
                .build();
    }

    @Builder
    private JobApplication(JobPosting jobPosting, User user) {
        this.jobPosting = jobPosting;
        this.user = user;
    }
}

package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

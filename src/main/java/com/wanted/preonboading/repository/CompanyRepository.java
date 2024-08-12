package com.wanted.preonboading.repository;

import com.wanted.preonboading.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}

package com.wanted.preonboading.domain.company;

import com.wanted.preonboading.repository.CompanyRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyFinder {
    private final CompanyRepository companyRepository;

    public Company find(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND)
        );
    }
}

package com.wanted.preonboading.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboading.api.request.JobApplicationCreateRequest;
import com.wanted.preonboading.domain.jobapplication.JobApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JobApplicationControllerTest {

    @Mock
    private JobApplicationService jobApplicationService;

    @InjectMocks
    private JobApplicationController jobApplicationController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobApplicationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("채용공고 지원 API")
    void applyForJob() throws Exception {
        Long jobPostingId = 1L;
        Long userId = 100L;
        JobApplicationCreateRequest request = new JobApplicationCreateRequest(jobPostingId, userId);

        doNothing().when(jobApplicationService).apply(anyLong(), anyLong());

        mockMvc.perform(post("/api/v1/job-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value(true))
                .andDo(print());

        verify(jobApplicationService, times(1)).apply(jobPostingId, userId);
    }
}
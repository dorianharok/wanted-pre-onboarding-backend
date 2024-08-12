package com.wanted.preonboading.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboading.api.request.CreateJobPostingRequest;
import com.wanted.preonboading.api.request.EditJobPostingRequest;
import com.wanted.preonboading.domain.jobposting.DetailJobPostingInfo;
import com.wanted.preonboading.domain.jobposting.JobPostingInfo;
import com.wanted.preonboading.domain.jobposting.JobPostingService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JobPostingControllerTest {

    @Mock
    private JobPostingService jobPostingService;

    @InjectMocks
    private JobPostingController jobPostingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("채용공고 생성 API")
    void createJobPosting() throws Exception {
        CreateJobPostingRequest request = new CreateJobPostingRequest("개발자", 100000, "Job Description", "Java", 1L);
        when(jobPostingService.createJobPosting(any())).thenReturn(1L);

        mockMvc.perform(post("/api/v1/job-postings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value(1L))
                .andDo(print());

        verify(jobPostingService, times(1)).createJobPosting(any());
    }

    @Test
    @DisplayName("채용공고 수정 API")
    void editJobPosting() throws Exception {
        Long postingId = 1L;
        EditJobPostingRequest request = new EditJobPostingRequest(
                "시니어 백엔드 개발자",
                120000,
                "우리 회사에서 시니어 백엔드 개발자를 찾고 있습니다. 자격 요건은...",
                "Java, Spring, AWS, Kubernetes"
        );
        JobPostingInfo updatedInfo = new JobPostingInfo(
                postingId,
                request.positionTitle(),
                request.reward(),
                request.jobDescription(),
                request.requiredSkill(),
                1L
        );
        when(jobPostingService.editJobPosting(any())).thenReturn(updatedInfo);

        mockMvc.perform(put("/api/v1/job-postings/" + postingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(postingId))
                .andExpect(jsonPath("$.data.positionTitle").value(request.positionTitle()))
                .andExpect(jsonPath("$.data.reward").value(request.reward()))
                .andExpect(jsonPath("$.data.requiredSkill").value(request.requiredSkill()))
                .andDo(print());

        verify(jobPostingService, times(1)).editJobPosting(any());
    }

    @Test
    @DisplayName("채용공고 삭제 API")
    void deleteJobPosting() throws Exception {
        doNothing().when(jobPostingService).deleteJobPosting(1L);

        mockMvc.perform(delete("/api/v1/job-postings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value(true));

        verify(jobPostingService, times(1)).deleteJobPosting(1L);
    }

    @Test
    @DisplayName("채용공고 리스트 조회 API")
    void getJobPostings() throws Exception {
        List<JobPostingInfo> jobPostings = Arrays.asList(
                new JobPostingInfo(1L, "백엔드 개발자", 100000, "백엔드 개발자를 모집합니다", "Java, Spring", 1L),
                new JobPostingInfo(2L, "프론트엔드 개발자", 90000, "프론트엔드 개발자를 모집합니다", "JavaScript, React", 1L)
        );
        when(jobPostingService.getJobPostings()).thenReturn(jobPostings);

        mockMvc.perform(get("/api/v1/job-postings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].positionTitle").value("백엔드 개발자"))
                .andExpect(jsonPath("$.data[0].reward").value(100000))
                .andExpect(jsonPath("$.data[0].requiredSkill").value("Java, Spring"))
                .andExpect(jsonPath("$.data[1].id").value(2L))
                .andExpect(jsonPath("$.data[1].positionTitle").value("프론트엔드 개발자"))
                .andExpect(jsonPath("$.data[1].reward").value(90000))
                .andExpect(jsonPath("$.data[1].requiredSkill").value("JavaScript, React"))
                .andDo(print());

        verify(jobPostingService, times(1)).getJobPostings();
    }

    @Test
    @DisplayName("채용공고 상세 조회 API")
    void getJobPostingDetails() throws Exception {
        JobPostingInfo info = new JobPostingInfo(1L, "시니어 백엔드 개발자", 100000, "시니어 백엔드 개발자를 모집합니다. 자격요건은...", "Java, Spring", 1L);
        DetailJobPostingInfo detailInfo = new DetailJobPostingInfo(info, Arrays.asList(2L, 3L));
        when(jobPostingService.getJobPostingDetails(1L)).thenReturn(detailInfo);

        mockMvc.perform(get("/api/v1/job-postings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.positionTitle").value("시니어 백엔드 개발자"))
                .andExpect(jsonPath("$.data.reward").value(100000))
                .andExpect(jsonPath("$.data.jobDescription").value("시니어 백엔드 개발자를 모집합니다. 자격요건은..."))
                .andExpect(jsonPath("$.data.requiredSkill").value("Java, Spring"))
                .andExpect(jsonPath("$.data.otherJobPostingIds", hasSize(2)))
                .andExpect(jsonPath("$.data.otherJobPostingIds[0]").value(2L))
                .andExpect(jsonPath("$.data.otherJobPostingIds[1]").value(3L))
                .andDo(print());

        verify(jobPostingService, times(1)).getJobPostingDetails(1L);
    }
}
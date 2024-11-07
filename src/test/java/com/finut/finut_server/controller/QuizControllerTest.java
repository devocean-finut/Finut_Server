package com.finut.finut_server.controller;

import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired

    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @MockBean
    private QuizDoneService quizDoneService;

    @MockBean
    private UsersService usersService;

    @MockBean
    private GoogleAuthService googleAuthService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testQuizCorrectSuccess() throws Exception {
        // Mock data
        Long quizId = 1L;
        Users mockUser = new Users(); // Replace with actual Users object setup
        Quiz mockQuiz = mock(Quiz.class); // Replace with actual Quiz object setup

        // Mock behavior
        when(usersService.getUserIdByToken(any(HttpServletRequest.class), any(HttpServletResponse.class)))
                .thenReturn(mockUser);
        when(quizService.getQuizByQuizId(quizId)).thenReturn(Optional.of(mockQuiz));

        // Perform test
        mockMvc.perform(get("/correct/{quizId}", quizId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("success"));

        // Verify that services were called as expected

        verify(quizDoneService, times(1)).saveQuizDone(mockUser, mockQuiz, true);
        verify(usersService, times(1)).updateDiffLevelCnt(mockUser.getId());
    }

}

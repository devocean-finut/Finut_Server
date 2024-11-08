package com.finut.finut_server.controller;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.quiz.AnswerType;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class QuizControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private QuizService quizService;

    @Mock
    private QuizDoneService quizDoneService;

    @InjectMocks
    private QuizController quizController;

        @Test
        public void testQuizCorrectSuccess() throws Exception {
            // Arrange
            Long quizId = 1L;
            Users mockUser = new Users(); // 필요한 필드 초기화
            mockUser.setId(1L); // ID 설정 등 필요한 필드 추가
            Quiz mockQuiz = new Quiz(quizId, DifficultyType.LO, "question", AnswerType.TRUE); // 필요한 필드 초기화

            HttpServletRequest mockRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockResponse = mock(HttpServletResponse.class);

            when(quizService.getQuizByQuizId(quizId)).thenReturn(Optional.of(mockQuiz));
            when(usersService.getUserIdByToken(mockRequest, mockResponse))
                    .thenReturn(mockUser);

            // Act
            ApiResponse<String> response = quizController.quizCorrect(quizId, mockRequest, mockResponse);

            // Assert
            assertEquals("success", response.getMessage());
            assertEquals("200", response.getStatusCode());

            verify(quizDoneService).saveQuizDone(mockUser, mockQuiz, true);
            verify(usersService).updateDiffLevelCnt(mockUser.getId());
        }


    @Test
    public void testQuizCorrectFailure_NoQuiz() throws Exception {
        // Arrange
        Long quizId = 1L;
        Users mockUser = new Users();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(usersService.getUserIdByToken(mockRequest, mockResponse)).thenReturn(mockUser);
        when(quizService.getQuizByQuizId(quizId)).thenReturn(Optional.empty());

        // Act
        ApiResponse<String> response = quizController.quizCorrect(quizId, mockRequest, mockResponse);

        // Assert
        assertEquals("500", response.getStatusCode());
        assertEquals("No Quiz", response.getMessage());

        verify(quizDoneService, never()).saveQuizDone(any(), any(), anyBoolean());
        verify(usersService, never()).updateDiffLevelCnt(any());
    }

}

package com.finut.finut_server.service;

import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRepository;
import com.finut.finut_server.domain.quizDone.QuizDoneRepository;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizDoneRepository quizDoneRepository;

    @Autowired
    private UsersRepository usersRepository;

    private final Random random = new Random();

    @Transactional
    public Optional<Quiz> getQuiz(Long userId) {
        Optional<Users> userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        Users user = userOpt.get();

        DifficultyType quizDiff = user.getDifficulty().getDifficulty();

        // quizDiff에 해당하는 모든 퀴즈 찾기
        List<Quiz> allQuizzesByDiff = quizRepository.findByDifficulty(quizDiff);

        // 해당 사용자가 완료한 퀴즈 ID 목록 가져오기
        Set<Long> completedQuizIds = quizDoneRepository.findByIdUserId(userId).stream()
                .map(quizDone -> quizDone.getQuiz().getId())
                .collect(Collectors.toSet());

        // 완료한 퀴즈는 제외한 남은 퀴즈 목록 생성
        List<Quiz> uncompletedQuizzes = allQuizzesByDiff.stream()
                .filter(quiz -> !completedQuizIds.contains(quiz.getId()))
                .toList();

        // 남은 퀴즈가 없으면 Optional.empty() 반환
        if (uncompletedQuizzes.isEmpty()) {
            return Optional.empty();
        }

        // 남은 퀴즈 목록에서 랜덤하게 하나 선택하여 반환
        return Optional.of(uncompletedQuizzes.get(random.nextInt(uncompletedQuizzes.size())));
    }


    public Optional<Quiz> getQuizByQuizId(Long quizId) {
        return quizRepository.findById(quizId);

    }
}

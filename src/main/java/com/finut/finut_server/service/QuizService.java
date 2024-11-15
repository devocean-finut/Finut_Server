package com.finut.finut_server.service;

import com.finut.finut_server.domain.difficulty.Difficulty;
import com.finut.finut_server.domain.difficulty.DifficultyType;
import com.finut.finut_server.domain.level.Level;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.quiz.QuizRepository;
import com.finut.finut_server.domain.quiz.QuizResponseDTO;
import com.finut.finut_server.domain.quizDone.QuizDoneRepository;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
            System.out.println("userOpt is empty");
            return Optional.empty();
        }
        Users user = userOpt.get();

        DifficultyType quizDiff = user.getDifficulty().getDifficulty();

        // quizDiff에 해당하는 모든 퀴즈 찾기
        List<Quiz> allQuizzesByDiff = quizRepository.findByDifficulty(quizDiff);

        // 해당 사용자가 완료한 퀴즈 ID 목록 가져오기
        Set<Long> completedQuizIds = quizDoneRepository.findAllByUserId(userId).stream()
                .map(quizDone -> quizDone.getQuizId())
                .collect(Collectors.toSet());

        // 완료한 퀴즈는 제외한 남은 퀴즈 목록 생성
        List<Quiz> uncompletedQuizzes = allQuizzesByDiff.stream()
                .filter(quiz -> !completedQuizIds.contains(quiz.getId()))
                .toList();

        // 남은 퀴즈가 없으면 Optional.empty() 반환
        if (uncompletedQuizzes.isEmpty()) {
            System.out.println("uncomplete Quizzes is empty");
            return Optional.empty();
        }

        // 남은 퀴즈 목록에서 랜덤하게 하나 선택하여 반환
        return Optional.of(uncompletedQuizzes.get(random.nextInt(uncompletedQuizzes.size())));
    }

    @Transactional
    public List<Quiz> getQuizToLevelTest(Long userId) {
        Optional<Users> userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("userOpt is empty");
            return null;
        }
        // 퀴즈 목록 생성
        List<Quiz> uncompletedQuizzes = quizRepository.findQuizzesByDifficulty();

        return uncompletedQuizzes;
    }

    @Transactional
    public QuizResponseDTO.quizResultResponseDTO getQuizLevelResult(Long userId, int score) {
        Users users = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Difficulty difficulty = new Difficulty();
        QuizResponseDTO.quizResultResponseDTO quizResultResponseDTO = new QuizResponseDTO.quizResultResponseDTO();
        if(score >= 20){
            difficulty.setDifficulty(DifficultyType.HI);
            users.setDifficulty(difficulty);
            quizResultResponseDTO.setDifficulty("HIGH");
        } else if(score >= 9){
            difficulty.setDifficulty(DifficultyType.MI);
            users.setDifficulty(difficulty);
            quizResultResponseDTO.setDifficulty("MIDDLE");
        }
        else{
            quizResultResponseDTO.setDifficulty("LOW");
        }
        usersRepository.save(users);
        return quizResultResponseDTO;
    }

    public Optional<Quiz> getQuizByQuizId(Long quizId) {
        return quizRepository.findById(quizId);

    }
}

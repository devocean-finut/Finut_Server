package com.finut.finut_server.service;

import com.finut.finut_server.apiPayload.code.status.ErrorStatus;
import com.finut.finut_server.apiPayload.exception.GeneralException;
import com.finut.finut_server.domain.attend.Attend;
import com.finut.finut_server.domain.attend.AttendRepository;
import com.finut.finut_server.domain.level.Level;
import com.finut.finut_server.domain.level.LevelRepository;
import com.finut.finut_server.domain.quiz.Quiz;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import com.google.api.services.oauth2.model.Userinfoplus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private LevelRepository levelRepository;

    GoogleAuthService googleAuthService;

    public void saveRefreshToken(String email, String refreshToken) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setRefreshToken(refreshToken);
            usersRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public UserResponseDTO.updateAttendance updateAttendDate(Long userId){
        String msg = "출석했습니다!";
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Attend attend = attendRepository.findByUserIdAndAttendDate(userId, formattedDate).orElse(null);

        if(attend == null){
            // 오늘 처음 출석하는 경우
            attend = new Attend();
            attend.setUser(user);
            attend.setAttendDate(formattedDate);
            attendRepository.save(attend);
            user.setTodaySalary(false);
            user.setAttendCount(user.getAttendCount() + 1);

            // 처음 출석하는 경우만 연속 출석 일자 체크하는 변수에 값 추가
            user.setContinuousCount(user.getContinuousCount() + 1);
            usersRepository.save(user);

            // 만약 연속 출석 5회인 경우, XP 올리기 + 연속 출석 횟수 0으로 초기화
            if(user.getContinuousCount() == 5){
                user.setXP(user.getXP() + 5);
                user.setContinuousCount(0);
                msg = "연속 5회 출석했습니다!";
            }

            // XP가 100이면 승진하기
            if(user.getXP() >= 100){
                user.setXP(user.getXP() - 100);
                Long newLevelId = user.getLevel().getId() + 1L; // id에 1 증가

                // 증가된 id를 가진 Level 엔티티를 데이터베이스에서 조회하여 user에 설정
                Level newLevel = levelRepository.findById(newLevelId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레벨입니다."));
                user.setLevel(newLevel); // user에 새로운 Level 설정
                msg = "승진했습니다!";
            }

            usersRepository.save(user);
        }
        else{
            // 처음 출석하는 경우가 아니라면
            if(user.isTodaySalary()) {
                // 오늘 이미 월급을 받았다면
                msg = "오늘은 이미 월급을 받았습니다!";
            }
            else {
                user.setAttendCount(user.getAttendCount() + 1);
                usersRepository.save(user);

                if(user.getAttendCount() == 5){
                    // attendCount가 5가 되었다면
                    user.setTodaySalary(true);
                    user.setAttendCount(0);

                    Level level = levelRepository.findById(user.getLevel().getId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 레벨입니다."));;
                    user.setMoney(user.getMoney() + level.getSalary());

                    usersRepository.save(user);
                    msg = "월급을 받았습니다!";
                }
            }
        }

        UserResponseDTO.updateAttendance updateAttendance = UserResponseDTO.updateAttendance.builder()
                .userId(userId)
                .message(msg)
                .build();

        return updateAttendance;
    }

    public Users getUserIdByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public void updateDiffLevelCnt(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setDiffQuizCount(user.getDiffQuizCount() + 1); // diffQuizCnt 증가
        user.setLevelQuizCount(user.getLevelQuizCount() + 1); // levelQuizCnt 증가
        usersRepository.save(user); // 변경사항 저장
    }

    public Users getUserIdByToken(HttpServletRequest request, HttpServletResponse response) {
        // Authorization 헤더에서 Access Token을 가져옵니다.
        String header = request.getHeader("Authorization");
        Users user;
        Optional<Quiz> quiz;

        if (header != null && header.startsWith("Bearer "))
        {
            String accessToken = header.substring(7); // "Bearer " 제거
            try {
                // Access Token을 이용해 사용자 정보를 조회합니다.
                Userinfoplus userInfo = googleAuthService.getUserInfo(accessToken);
                user = getUserIdByEmail(userInfo.getEmail());
                return user;

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}


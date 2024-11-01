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
import java.util.List;
import java.util.Optional;

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
            usersRepository.save(user);

//            // 만약 연속 출석인 경우, XP 올리기
//            if(hasFiveConsecutiveDays(userId)){
//                user.setXP(user.getXP() + 5);
//            }

//            // XP가 100이면 승진하기
//            if(user.getXP() == 100){
//                user.setLevel(user.getLevel());
//            }
        }
        else{
            // 처음 출석하는 경우가 아니라면
            if(user.isTodaySalary()){
                // 오늘 이미 월급을 받았다면
                msg = "오늘은 이미 월급을 받았습니다!";
            }
            else if(user.getAttendCount() == 5){
                // attendCount가 5가 되었다면
                user.setTodaySalary(true);
                user.setAttendCount(0);
                usersRepository.save(user);

                Level level = levelRepository.findById(user.getLevel().getId());
                user.setMoney(user.getMoney() + level.getSalary());
                msg = "월급을 받았습니다!";
            }
            else {
                user.setAttendCount(user.getAttendCount() + 1);
                usersRepository.save(user);
            }
        }

        UserResponseDTO.updateAttendance updateAttendance = UserResponseDTO.updateAttendance.builder()
                .userId(userId)
                .message(msg)
                .build();

        return updateAttendance;
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public boolean hasFiveConsecutiveDays(Long userId) {
        LocalDate today = LocalDate.now();

        // 오늘 날짜 포함하여 5일치 데이터 가져오기
        List<String> dateStrings = attendRepository.findTop5ByUserIdAndDateBeforeOrderByDateDesc(userId, today);

        // 데이터가 부족한 경우 연속 5일이 아니므로 false
        if (dateStrings.size() < 5) {
            return false;
        }

        // 날짜를 LocalDate 형식으로 변환하여 오름차순 정렬
        List<LocalDate> dates = dateStrings.stream()
                .map(date -> LocalDate.parse(date, FORMATTER))
                .sorted()
                .toList();

        // 연속적인 5일인지 확인
        for (int i = 1; i < dates.size(); i++) {
            if (!dates.get(i).minusDays(1).equals(dates.get(i - 1))) {
                return false; // 연속되지 않은 날짜가 있으면 false 반환
            }
        }
        return true;
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
//        if(true)
        {
            String accessToken = header.substring(7); // "Bearer " 제거
//            String accessToken = "ya29.a0AeDClZCXQpR9Pb1ZRQSh5SrNYPTqX3NRJRAJRxxRif17PYzuEJuiFfeuVVM0VBee9S2FAPLKrieDazlPK7jL69LtIw2YGPU2yOqhQVvUMGEXJ2e6K9Tedpsc7A84ut1azP7IlpmjzmW1aAGYfoloJ3MFFBuF-2XZCgnEIx4FaCgYKAQgSARMSFQHGX2Mip5IYMEzhkNcsRTikuilkKQ0175";

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


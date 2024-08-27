package com.finut.finut_server.service;

import com.finut.finut_server.apiPayload.code.status.ErrorStatus;
import com.finut.finut_server.apiPayload.exception.GeneralException;
import com.finut.finut_server.domain.user.UserResponseDTO;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

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
        String msg = "이미 출석했습니다!";
        Boolean attend = true;
        // userId로 attend 날짜 확인
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(!user.getAttend().equals(formattedDate)){
            // 출석을 하지 않은 경우
            user.setAttend(formattedDate);
            user.setMoney(user.getMoney() + 100000L); // 출석마다 금액이 바뀌는 등, 기획 수정 시, 코드도 수정 후 반영 예정
            usersRepository.save(user);

            msg = "출석했습니다!";
            attend = false;
        }
        UserResponseDTO.updateAttendance updateAttendance = UserResponseDTO.updateAttendance.builder()
                .userId(userId)
                .message(msg)
                .isAttend(attend)
                .build();

        return updateAttendance;
    }
}


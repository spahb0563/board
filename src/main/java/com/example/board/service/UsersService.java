package com.example.board.service;

import com.example.board.security.dto.SessionUser;
import com.example.board.model.entity.Users;
import com.example.board.model.network.dto.users.UsersResponseDto;
import com.example.board.model.network.dto.users.UsersUpdateRequestDto;
import com.example.board.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final HttpSession httpSession;

    public UsersResponseDto read(Long id) {

        Users user = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. userId : " + id));

        return new UsersResponseDto(user);
    }

    @Transactional
    public Long update(UsersUpdateRequestDto usersUpdateRequestDto) {

        Users user = usersRepository.findById(usersUpdateRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다. userId : " + usersUpdateRequestDto.getId()));

        httpSession.setAttribute("user", new SessionUser(user.update(usersUpdateRequestDto.getName(), usersUpdateRequestDto.getNickname(), usersUpdateRequestDto.getPicture())));

        return user.getId();
    }
}

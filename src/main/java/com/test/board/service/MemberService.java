package com.test.board.service;

import com.test.board.dto.LoginRequest;
import com.test.board.dto.RegisterRequest;
import com.test.board.entity.Member;
import com.test.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public void addMember(RegisterRequest registerRequest) {
        Member member = new Member();
        member.setUserId(registerRequest.getUserId());
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        member.setNickname(
                registerRequest.getLastName() + registerRequest.getFirstName()
        );
        member.setRole("ROLE_USER");
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    // public void login(LoginRequest loginRequest) {
    //     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    //         loginRequest.getUserId(),
    //         loginRequest.getPassword()
    //     );
    //     authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    // }
}

package com.test.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.board.entity.Member;
import com.test.board.repository.MemberRepository;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final MemberRepository memberRepository;

    public Member member() {
        return memberRepository
            .findByUserId(
                SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName()
            )
            .orElseThrow(() -> new UsernameNotFoundException("유저 정보가 없습니다."));
    }

    @GetMapping
    public String home() {
        String userId = member().getUserId();
        if (userId == null) {
            // return "login";
            return "redirect:/login";
            //redirect를 안 붙일 경우 security 에서 api 요청 받기 전에 요청을 가로채는데
            //html을 리턴하기 때문에 로그인에 성공했을 경우 static 을 뱉고 redirect를 해준다.
        }
        return "index";
    }
    @GetMapping(value = "main")
    public String main() {
        return "index";
    }

    @GetMapping(value = "login")
    public String login() {
            return "login";
    }

    @GetMapping(value = "register")
    public String register() {
        String userId = member().getUserId();
        if (userId == null) {
            return "register";
        } else {
            return "redirect:/main";
        }
    }

    @GetMapping(value = "blank")
    public String blank() {
        String userId = member().getUserId();
        if (userId == null) {
            return "redirect:/login";
        } else {
            return "blank";
        }
    }
}

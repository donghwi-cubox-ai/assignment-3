package com.test.board.controller;

import com.test.board.dto.LoginRequest;
import com.test.board.dto.RegisterRequest;
import com.test.board.entity.Member;
import com.test.board.repository.MemberRepository;
import com.test.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    public Member member() {
        return memberRepository
            .findByUserId(
                SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName()
            )
            .orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
    }
    @PostMapping(value = "register")
    public String register(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes) {
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/register";
        }
        memberService.addMember(registerRequest);
        return "redirect:/login";
    }

    // @PostMapping(value = "login")
    // public void login(@ModelAttribute LoginRequest loginRequest) {
    //     memberService.login(loginRequest);
    // }

    @PostMapping(value = "logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:/login";
    }
}

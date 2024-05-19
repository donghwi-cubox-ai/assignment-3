package com.test.board.controller;

import com.test.board.dto.BoardRequest;
import com.test.board.entity.Member;
import com.test.board.repository.MemberRepository;
import com.test.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {

    private final BoardService boardService;
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

    // @PostMapping(value = "add")
    // public String postBoard(@Valid @ModelAttribute BoardRequest boardRequest, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
    //     String userId = (String) session.getAttribute("userId");
    //     if(result.hasErrors()) {
    //         redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
    //         return "redirect:/blank";
    //     } else if (userId == null) {
    //         redirectAttributes.addFlashAttribute("error", "로그인이 필요한 기능입니다.");
    //         return "redirect:/login";
    //     } else {
    //         boardService.addBoard(boardRequest, userId);
    //         return "redirect:/main";
    //     }
    // }
    @PostMapping(value = "add")
    public String postBoard(@Valid @ModelAttribute BoardRequest boardRequest) {
        if(!member().getUserId().isEmpty()) {
            boardService.addBoard(boardRequest, member().getUserId());
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }
}

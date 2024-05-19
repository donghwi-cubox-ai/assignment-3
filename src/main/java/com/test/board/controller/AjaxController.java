package com.test.board.controller;

import com.test.board.dto.BoardRequest;
import com.test.board.dto.BoardResponse;
import com.test.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ajax/")
public class AjaxController {

    private final BoardService boardService;

    @GetMapping(value = "get")
    public List<BoardResponse> getBoard(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        return boardService.getBoard(userId);
    }

    @PutMapping(value = "update/{boardId}")
    public BoardResponse updateBoard(@Valid @RequestBody BoardRequest boardRequest, @PathVariable("boardId") Long id) {
        return boardService.updateBoard(boardRequest, id);
    }

    @DeleteMapping(value = "delete/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "search/{searchText}")
    public List<BoardResponse> searchBoard(@PathVariable("searchText") String searchText) {
        return boardService.getSearchBoard(searchText);
    }

}

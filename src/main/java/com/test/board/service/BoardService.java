package com.test.board.service;

import com.test.board.dto.BoardRequest;
import com.test.board.dto.BoardResponse;
import com.test.board.entity.Board;
import com.test.board.entity.Member;
import com.test.board.repository.BoardRepository;
import com.test.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<BoardResponse> getBoard(String userId) {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponse> boardResponses = new ArrayList<>();
        for(Board board : boards) {
            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setId(board.getId());
            if(userId != null && userId.equals(board.getMember().getUserId())) {
                boardResponse.setCheckLoginMember(true);
            } else {
                boardResponse.setCheckLoginMember(false);
            }
            boardResponse.setBoardName(board.getTitle());
            boardResponse.setBoardContent(board.getContent());
            String nickname = memberRepository.findById(board.getMember().getId()).get().getNickname();
            boardResponse.setNickname(nickname);
            boardResponses.add(boardResponse);
        }
        return boardResponses;
    }

    public void addBoard(BoardRequest boardRequest, String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        Board board = new Board();
        board.setTitle(boardRequest.getBoardName());
        board.setMember(member);
        board.setContent(boardRequest.getBoardContent());
        board.setCreatedAt(LocalDateTime.now());
        boardRepository.save(board);
    }

    public BoardResponse updateBoard(BoardRequest boardRequest, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.setTitle(boardRequest.getBoardName());
        board.setContent(boardRequest.getBoardContent());
        boardRepository.save(board);
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(id);
        boardResponse.setBoardName(boardRequest.getBoardName());
        boardResponse.setBoardContent(boardRequest.getBoardContent());
        boardResponse.setNickname(board.getMember().getNickname());
        return boardResponse;
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardResponse> getSearchBoard(String searchText) {
        List<Board> boards = boardRepository.findByBoards(searchText);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for(Board board : boards) {
            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setId(board.getId());
            boardResponse.setBoardName(board.getTitle());
            boardResponse.setBoardContent(board.getContent());
            String nickname = memberRepository.findById(board.getMember().getId()).get().getNickname();
            boardResponse.setNickname(nickname);
            boardResponses.add(boardResponse);
        }
        return boardResponses;
    }
}

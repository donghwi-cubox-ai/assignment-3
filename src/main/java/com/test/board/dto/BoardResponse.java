package com.test.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardResponse {

    private Long id;
    private String boardName;
    private String boardContent;
    private String nickname;
    private boolean checkLoginMember;

}

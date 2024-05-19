package com.test.board.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardRequest {

    private Long id;
    @Size(max = 50, message = "제목은 최대 50자까지 가능합니다.")
    private String boardName;
    @Size(max = 200, message = "내용은 최대 200자까지 가능합니다.")
    private String boardContent;

}

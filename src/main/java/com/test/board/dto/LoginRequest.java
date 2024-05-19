package com.test.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String userId;
    private String password;
    private String authority;
}
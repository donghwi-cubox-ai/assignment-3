package com.test.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {

    private String userId;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;

}

package com.bbrick.auth.view.web.auth.dto;

import com.bbrick.auth.comn.validation.annotation.EmailFormat;
import com.bbrick.auth.comn.validation.annotation.UserPasswordFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    @EmailFormat
    private String email;

    @NotNull
    @UserPasswordFormat
    private String password;
}

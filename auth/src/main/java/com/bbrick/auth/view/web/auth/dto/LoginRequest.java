package com.bbrick.auth.view.web.auth.dto;

import com.bbrick.auth.comn.validation.annotation.EmailFormat;
import com.bbrick.auth.comn.validation.annotation.UserPasswordFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @EmailFormat
    private String email;

    @NotNull
    @UserPasswordFormat
    private String password;
}

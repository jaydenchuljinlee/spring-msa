package com.bbrick.auth.view.web.auth;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.core.user.application.UserLoginService;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    private final UserLoginService userLoginService;

    @PostMapping(WebConstants.URL.LOGIN_REQUEST_PATH)
    public ResponseEntity<BaseResponse<Void>> login(@RequestBody LoginRequest request) {
        // Servlet Filter를 통해 Login API 처리가 된다.

        userLoginService.login(request);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PostMapping(WebConstants.URL.LOGOUT_REQUEST_PATH)
    public ResponseEntity<BaseResponse<Void>> logout() {
        // Servlet Filter를 통해 Logout API 처리가 된다.
        return ResponseEntity.ok(BaseResponse.success());
    }
}

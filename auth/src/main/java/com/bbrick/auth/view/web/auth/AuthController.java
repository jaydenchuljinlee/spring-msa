package com.bbrick.auth.view.web.auth;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.core.auth.dto.TokenDto;
import com.bbrick.auth.core.user.application.UserLoginService;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@RestController
public class AuthController {
    private final UserLoginService userLoginService;

    @PostMapping(WebConstants.URL.LOGIN_REQUEST_PATH)
    public ResponseEntity<BaseResponse<TokenDto>> login(@RequestBody LoginRequest request) {
        // Servlet Filter를 통해 Login API 처리가 된다.

        TokenDto tokenDto = userLoginService.login(request);

        return ResponseEntity
                .ok()
                .header("X-AUTH-ACCESS-TOKEN", tokenDto.getAcccessToken())
                .header("X-AUTH-REFRESH-TOKEN", tokenDto.getRefreshToken())
                .body(BaseResponse.success(tokenDto));
    }

    @PostMapping(WebConstants.URL.LOGOUT_REQUEST_PATH)
    public ResponseEntity<BaseResponse<Void>> logout(
            HttpServletRequest request,
            @RequestHeader("X-AUTH-ACCESS-TOKEN") String accessToken,
            @RequestHeader("X-AUTH-REFRESH-TOKEN") String refreshToken
    ) {
        TokenDto tokenDto = TokenDto.of(accessToken, refreshToken);

        userLoginService.logout(request, tokenDto);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN.value())
                .body(BaseResponse.success());
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenDto>> reissue(
            @RequestHeader("X-AUTH-REFRESH-TOKEN") String refreshToken
    ) {
       TokenDto tokenDto = userLoginService.reissue(refreshToken);
       return ResponseEntity.ok(BaseResponse.success(tokenDto));
    }

    @GetMapping("/ping")
    public ResponseEntity<BaseResponse<Void>> ping() {

        log.debug("test");

        return ResponseEntity.ok(BaseResponse.success());
    }
}

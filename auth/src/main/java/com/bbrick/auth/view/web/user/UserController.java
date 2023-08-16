package com.bbrick.auth.view.web.user;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.core.user.application.UserDetailService;
import com.bbrick.auth.core.user.application.UserJoinService;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.bbrick.auth.view.web.user.dto.UserJoinRequest;
import com.bbrick.auth.view.web.user.dto.UserJoinResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserJoinService userJoinService;
    private final UserDetailService userDetailQueryService;

    @PostMapping
    public ResponseEntity<BaseResponse<UserJoinResponse>> joinUser(
            @Valid @RequestBody UserJoinRequest request
            ) {
        UserDetail joinedUser = this.userJoinService.join(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhoneNumber(),
                request.genderEnumOrNull()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(
                        UserJoinResponse.of(joinedUser)
                ));
    }
}

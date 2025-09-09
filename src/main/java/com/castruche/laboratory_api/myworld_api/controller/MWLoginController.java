package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.dto.util.PasswordDto;
import com.castruche.laboratory_api.main_api.dto.util.UserMailDto;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginResponseDto;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginUserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.service.MWLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.castruche.laboratory_api.myworld_api.controller.ConstantMyWorldUrl.LOGIN;

@RestController
@RequestMapping(LOGIN)
public class MWLoginController {

    private final MWLoginService loginService;

    public MWLoginController(MWLoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto userDto) {
        LoginResponseDto response = loginService.login(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto saved = loginService.register(userDto);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    @GetMapping("/availability/username/{username}")
    public ResponseEntity<BooleanResponseDto> checkUsernameAvailability(@PathVariable String username) {
        return ResponseEntity.ok(loginService.checkUsernameAvailability(username));
    }

    @GetMapping("/availability/mail/{mail}")
    public ResponseEntity<BooleanResponseDto> checkMailAvailability(@PathVariable String mail) {
        return ResponseEntity.ok(loginService.checkMailAvailability(mail));
    }

    @GetMapping("/verify/mail/{token}")
    public ResponseEntity<BooleanResponseDto> verifyMail(@PathVariable String token) {
        return ResponseEntity.ok(loginService.verifyMail(token));
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<BooleanResponseDto> sendResetPasswordMail(@RequestBody UserMailDto userMailDto) {
        return ResponseEntity.ok(loginService.sendResetPasswordMail(userMailDto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<BooleanResponseDto> resetPassword(@RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(loginService.resetPassword(passwordDto));
    }
}

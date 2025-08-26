package com.castruche.laboratory_api.myworld_api.controller;

import com.castruche.laboratory_api.main_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.laboratory_api.main_api.dto.util.PasswordDto;
import com.castruche.laboratory_api.main_api.dto.util.UserMailDto;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginResponseDto;
import com.castruche.laboratory_api.myworld_api.dto.login.LoginUserDto;
import com.castruche.laboratory_api.myworld_api.dto.user.UserDto;
import com.castruche.laboratory_api.myworld_api.service.MWLoginService;
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
    public LoginResponseDto login(@RequestBody LoginUserDto userDto) {
        return loginService.login(userDto);
    }
    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }

    @GetMapping("/availability/username/{username}")
    public BooleanResponseDto checkUsernameAvailability(@PathVariable("username") String username) {
        return loginService.checkUsernameAvailability(username);
    }

    @GetMapping("/availability/mail/{mail}")
    public BooleanResponseDto checkMailAvailability(@PathVariable("mail") String mail) {
        return loginService.checkMailAvailability(mail);
    }

    @GetMapping("/verify/mail/{token}")
    public BooleanResponseDto verifyMail(@PathVariable("token") String token) {
        return loginService.verifyMail(token);
    }

    @PostMapping("/reset-password/request")
    public BooleanResponseDto sendResetPasswordMail(@RequestBody UserMailDto userMailDto) {
        return loginService.sendResetPasswordMail(userMailDto);
    }

    @PostMapping("/reset-password")
    public BooleanResponseDto resetPassword(@RequestBody PasswordDto passwordDto) {
        return loginService.resetPassword(passwordDto);
    }

}

package com.castruche.laboratory_api.myworld_api.dto.login;


import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class LoginUserDto extends AbstractDto {
    private String identifier;
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

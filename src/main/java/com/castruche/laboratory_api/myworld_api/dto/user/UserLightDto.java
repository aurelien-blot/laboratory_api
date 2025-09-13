package com.castruche.laboratory_api.myworld_api.dto.user;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class UserLightDto extends AbstractDto {
    private String username;

    public UserLightDto() {
        super();
    }

    public UserLightDto(Long id, String username) {
        super.setId(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

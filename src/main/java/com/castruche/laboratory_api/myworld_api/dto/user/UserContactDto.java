package com.castruche.laboratory_api.myworld_api.dto.user;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;
import com.castruche.laboratory_api.myworld_api.enums.ContactStatus;

public class UserContactDto extends AbstractDto {
    private String username;

    public UserContactDto() {
        super();
    }

    public UserContactDto(Long id, String username) {
        super.setId(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

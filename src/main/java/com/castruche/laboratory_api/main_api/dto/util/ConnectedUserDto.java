package com.castruche.laboratory_api.main_api.dto.util;


import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class ConnectedUserDto extends AbstractDto {
    private Long id;
    private String username;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

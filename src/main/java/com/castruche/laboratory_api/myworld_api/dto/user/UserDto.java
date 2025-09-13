package com.castruche.laboratory_api.myworld_api.dto.user;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class UserDto extends AbstractDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    private boolean mailVerified;

    public UserDto() {
        super();
    }

    public UserDto(Long id, String username) {
        super.setId(id);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

}

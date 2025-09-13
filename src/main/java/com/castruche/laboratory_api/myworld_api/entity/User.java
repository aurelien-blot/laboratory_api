package com.castruche.laboratory_api.myworld_api.entity;

import com.castruche.laboratory_api.main_api.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "\"user\"") // guillemets doubles échappés pour H2/SQL standard
public class User extends AbstractEntity {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    private boolean mailVerified;
    private String mailVerificationToken;
    private String resetPasswordToken;

    private boolean blocked;

    private int tentatives;

    private LocalDateTime lastVerificationMailDate;


    public User() {
        super();
    }

    public User(Long id, String username){
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

    public String getMailVerificationToken() {
        return mailVerificationToken;
    }

    public void setMailVerificationToken(String mailVerificationToken) {
        this.mailVerificationToken = mailVerificationToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getTentatives() {
        return tentatives;
    }

    public void setTentatives(int tentatives) {
        this.tentatives = tentatives;
    }

    public LocalDateTime getLastVerificationMailDate() {
        return lastVerificationMailDate;
    }

    public void setLastVerificationMailDate(LocalDateTime lastVerificationMailDate) {
        this.lastVerificationMailDate = lastVerificationMailDate;
    }
}

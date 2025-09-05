package com.castruche.laboratory_api.myworld_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Post extends AbstractEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creationBy;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(User creationBy) {
        this.creationBy = creationBy;
    }
}

package com.castruche.laboratory_api.myworld_api.entity;

import com.castruche.laboratory_api.main_api.entity.AbstractEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post extends AbstractEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creationBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictureList;


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

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }
}

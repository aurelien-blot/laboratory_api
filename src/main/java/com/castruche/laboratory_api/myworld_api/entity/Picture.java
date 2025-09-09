package com.castruche.laboratory_api.myworld_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Picture extends AbstractEntity {

    String filename;
    String extension;
    Double originalSize;
    String resizedFileFilepath;
    String originalFilepath;
    @ManyToOne(fetch = FetchType.LAZY)
    Post post;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Double getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(Double originalSize) {
        this.originalSize = originalSize;
    }

    public String getResizedFileFilepath() {
        return resizedFileFilepath;
    }

    public void setResizedFileFilepath(String resizedFileFilepath) {
        this.resizedFileFilepath = resizedFileFilepath;
    }

    public String getOriginalFilepath() {
        return originalFilepath;
    }

    public void setOriginalFilepath(String originalFilepath) {
        this.originalFilepath = originalFilepath;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

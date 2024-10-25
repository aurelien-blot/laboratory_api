package com.castruche.laboratory_api.map_gen_api.entity.map;

import com.castruche.laboratory_api.main_api.entity.AbstractEntity;
import jakarta.persistence.Entity;

@Entity
public class Biome extends AbstractEntity {

        private String technicalName;
        private String name;
        private String color;
        private Integer ordre;
        private Integer level;

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

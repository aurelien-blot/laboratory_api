package com.castruche.laboratory_api.map_gen_api.dto.map;

import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class BiomeDto extends AbstractDto {

    private String technicalName;
    private String name;
    private String color;
    private Integer order;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

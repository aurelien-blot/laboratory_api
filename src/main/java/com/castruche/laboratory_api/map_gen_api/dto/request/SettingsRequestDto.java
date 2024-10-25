package com.castruche.laboratory_api.map_gen_api.dto.request;

import com.castruche.laboratory_api.map_gen_api.enums.MapDistanceUnit;

import java.util.List;

public class SettingsRequestDto {
    private static final Double STANDARD_PIXELS_PER_INCH = 96D;
    private static final Double INCHES_PER_CENTIMETER = 2.54D;
    private Double height;
    private Double width;
    private MapDistanceUnit unit;

    private List<SettingsBiomeRequestDto> settingsBiomeList;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public MapDistanceUnit getUnit() {
        return unit;
    }

    public void setUnit(MapDistanceUnit unit) {
        this.unit = unit;
    }

    public List<SettingsBiomeRequestDto> getSettingsBiomeList() {
        return settingsBiomeList;
    }

    public void setSettingsBiomeList(List<SettingsBiomeRequestDto> settingsBiomeList) {
        this.settingsBiomeList = settingsBiomeList;
    }

    public Long getHeightPx() {
        if(null!=this.height){
            return convertToPixels(this.height, this.unit);
        }
        return null;
    }

    public Long getWidthPx() {
        if(null!=this.width){
            return convertToPixels(this.width, this.unit);
        }
        return null;
    }

    public Long getTotalPixels() {
        Long heightPx = this.getHeightPx();
        Long widthPx = this.getWidthPx();
        if(null!=heightPx && null!=widthPx){
            return heightPx * widthPx;
        }
        return null;
    }

    public boolean isValid() {
        return this.getTotalPixels() != null && this.settingsBiomeList != null && !this.settingsBiomeList.isEmpty();
    }


    private Long convertToPixels(Double value, MapDistanceUnit unit) {
        if(unit == null || value == null) {
            return null;
        }
        else if(unit.equals(MapDistanceUnit.PIXELS)) {
            return value.longValue();
        }
        else if(unit.equals(MapDistanceUnit.CENTIMETERS)) {
            return Math.round(value/INCHES_PER_CENTIMETER*STANDARD_PIXELS_PER_INCH);
        }
        return null;
    }
}

package com.castruche.laboratory_api.map_gen_api.dto.request;

public class SettingsBiomeRequestDto {

    private Long biomeId;
    private Double percentage;
    private Long pixelTotal;
    private Double treshold;

    public Long getBiomeId() {
        return biomeId;
    }

    public void setBiomeId(Long biomeId) {
        this.biomeId = biomeId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getPixelTotal() {
        return pixelTotal;
    }

    public void setPixelTotal(Long pixelTotal) {
        this.pixelTotal = pixelTotal;
    }

    public Double getTreshold() {
        return treshold;
    }

    public void setTreshold(Double treshold) {
        this.treshold = treshold;
    }
}

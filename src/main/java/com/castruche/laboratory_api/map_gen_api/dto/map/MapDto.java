package com.castruche.laboratory_api.map_gen_api.dto.map;


import com.castruche.laboratory_api.main_api.dto.AbstractDto;

public class MapDto extends AbstractDto {

    private Long heightPx;
    private Long widthPx;
    private BiomeDto [][] map;

    public Long getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(Long heightPx) {
        this.heightPx = heightPx;
    }

    public Long getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(Long widthPx) {
        this.widthPx = widthPx;
    }

    public BiomeDto[][] getMap() {
        return map;
    }

    public void setMap(BiomeDto[][] map) {
        this.map = map;
    }
}

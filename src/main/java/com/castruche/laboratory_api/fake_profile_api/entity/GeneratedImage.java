package com.castruche.laboratory_api.fake_profile_api.entity;

import com.castruche.laboratory_api.main_api.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class GeneratedImage extends AbstractEntity {

    private String templateTitle;
    private Long seedUsed;
    private Long subseedUsed;

    private String filePath;

    private String prompt;
    private String negativePrompt;
    private String styles; //TODO c était un String []
    private int subseedStrength;

    @Column(name = "seed_resize_from_h")
    private int seedResizeFromH;

    @Column(name = "seed_resize_from_w")
    private int seedResizeFromW;
    private String samplerName;
    private String scheduler;
    private int batchSize;
    private int nIter;
    private int steps;
    private int cfgScale;
    private int width;
    private int height;
    private boolean restoreFaces;
    private boolean tiling;
    private boolean doNotSaveSamples;
    private boolean doNotSaveGrid;
    private int eta;
    private Double denoisingStrength;
    private int sMinUncond;
    private int sChurn;
    private int sTmax;
    private int sTmin;
    private int sNoise;
    private boolean overrideSettingsRestoreAfterwards;
    private String refinerCheckpoint;
    private int refinerSwitchAt;
    private boolean disableExtraNetworks;
    private String firstpassImage;
    private boolean enableHr;
    private int firstphaseWidth;
    private int firstphaseHeight;
    private int hrScale;
    private String hrUpscaler;
    private int hrSecondPassSteps;

    @Column(name = "hr_resize_x")
    private int hrResizeX;

    @Column(name = "hr_resize_y")
    private int hrResizeY;
    private String hrCheckpointName;
    private String hrSamplerName;
    private String hrScheduler;
    private String hrPrompt;
    private String hrNegativePrompt;
    private String forceTaskId;
    private String samplerIndex;
    private String scriptName;
    private String scriptArgs; //TODO c était un String []
    private boolean sendImages;
    private boolean saveImages;
    private String infotext;

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public Long getSeedUsed() {
        return seedUsed;
    }

    public void setSeedUsed(Long seedUsed) {
        this.seedUsed = seedUsed;
    }

    public Long getSubseedUsed() {
        return subseedUsed;
    }

    public void setSubseedUsed(Long subseedUsed) {
        this.subseedUsed = subseedUsed;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getNegativePrompt() {
        return negativePrompt;
    }

    public void setNegativePrompt(String negativePrompt) {
        this.negativePrompt = negativePrompt;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }


    public int getSubseedStrength() {
        return subseedStrength;
    }

    public void setSubseedStrength(int subseedStrength) {
        this.subseedStrength = subseedStrength;
    }

    public int getSeedResizeFromH() {
        return seedResizeFromH;
    }

    public void setSeedResizeFromH(int seedResizeFromH) {
        this.seedResizeFromH = seedResizeFromH;
    }

    public int getSeedResizeFromW() {
        return seedResizeFromW;
    }

    public void setSeedResizeFromW(int seedResizeFromW) {
        this.seedResizeFromW = seedResizeFromW;
    }

    public String getSamplerName() {
        return samplerName;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getnIter() {
        return nIter;
    }

    public void setnIter(int nIter) {
        this.nIter = nIter;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCfgScale() {
        return cfgScale;
    }

    public void setCfgScale(int cfgScale) {
        this.cfgScale = cfgScale;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRestoreFaces() {
        return restoreFaces;
    }

    public void setRestoreFaces(boolean restoreFaces) {
        this.restoreFaces = restoreFaces;
    }

    public boolean isTiling() {
        return tiling;
    }

    public void setTiling(boolean tiling) {
        this.tiling = tiling;
    }

    public boolean isDoNotSaveSamples() {
        return doNotSaveSamples;
    }

    public void setDoNotSaveSamples(boolean doNotSaveSamples) {
        this.doNotSaveSamples = doNotSaveSamples;
    }

    public boolean isDoNotSaveGrid() {
        return doNotSaveGrid;
    }

    public void setDoNotSaveGrid(boolean doNotSaveGrid) {
        this.doNotSaveGrid = doNotSaveGrid;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public Double getDenoisingStrength() {
        return denoisingStrength;
    }

    public void setDenoisingStrength(Double denoisingStrength) {
        this.denoisingStrength = denoisingStrength;
    }

    public int getsMinUncond() {
        return sMinUncond;
    }

    public void setsMinUncond(int sMinUncond) {
        this.sMinUncond = sMinUncond;
    }

    public int getsChurn() {
        return sChurn;
    }

    public void setsChurn(int sChurn) {
        this.sChurn = sChurn;
    }

    public int getsTmax() {
        return sTmax;
    }

    public void setsTmax(int sTmax) {
        this.sTmax = sTmax;
    }

    public int getsTmin() {
        return sTmin;
    }

    public void setsTmin(int sTmin) {
        this.sTmin = sTmin;
    }

    public int getsNoise() {
        return sNoise;
    }

    public void setsNoise(int sNoise) {
        this.sNoise = sNoise;
    }

    public boolean isOverrideSettingsRestoreAfterwards() {
        return overrideSettingsRestoreAfterwards;
    }

    public void setOverrideSettingsRestoreAfterwards(boolean overrideSettingsRestoreAfterwards) {
        this.overrideSettingsRestoreAfterwards = overrideSettingsRestoreAfterwards;
    }

    public String getRefinerCheckpoint() {
        return refinerCheckpoint;
    }

    public void setRefinerCheckpoint(String refinerCheckpoint) {
        this.refinerCheckpoint = refinerCheckpoint;
    }

    public int getRefinerSwitchAt() {
        return refinerSwitchAt;
    }

    public void setRefinerSwitchAt(int refinerSwitchAt) {
        this.refinerSwitchAt = refinerSwitchAt;
    }

    public boolean isDisableExtraNetworks() {
        return disableExtraNetworks;
    }

    public void setDisableExtraNetworks(boolean disableExtraNetworks) {
        this.disableExtraNetworks = disableExtraNetworks;
    }

    public String getFirstpassImage() {
        return firstpassImage;
    }

    public void setFirstpassImage(String firstpassImage) {
        this.firstpassImage = firstpassImage;
    }

    public boolean isEnableHr() {
        return enableHr;
    }

    public void setEnableHr(boolean enableHr) {
        this.enableHr = enableHr;
    }

    public int getFirstphaseWidth() {
        return firstphaseWidth;
    }

    public void setFirstphaseWidth(int firstphaseWidth) {
        this.firstphaseWidth = firstphaseWidth;
    }

    public int getFirstphaseHeight() {
        return firstphaseHeight;
    }

    public void setFirstphaseHeight(int firstphaseHeight) {
        this.firstphaseHeight = firstphaseHeight;
    }

    public int getHrScale() {
        return hrScale;
    }

    public void setHrScale(int hrScale) {
        this.hrScale = hrScale;
    }

    public String getHrUpscaler() {
        return hrUpscaler;
    }

    public void setHrUpscaler(String hrUpscaler) {
        this.hrUpscaler = hrUpscaler;
    }

    public int getHrSecondPassSteps() {
        return hrSecondPassSteps;
    }

    public void setHrSecondPassSteps(int hrSecondPassSteps) {
        this.hrSecondPassSteps = hrSecondPassSteps;
    }

    public int getHrResizeX() {
        return hrResizeX;
    }

    public void setHrResizeX(int hrResizeX) {
        this.hrResizeX = hrResizeX;
    }

    public int getHrResizeY() {
        return hrResizeY;
    }

    public void setHrResizeY(int hrResizeY) {
        this.hrResizeY = hrResizeY;
    }

    public String getHrCheckpointName() {
        return hrCheckpointName;
    }

    public void setHrCheckpointName(String hrCheckpointName) {
        this.hrCheckpointName = hrCheckpointName;
    }

    public String getHrSamplerName() {
        return hrSamplerName;
    }

    public void setHrSamplerName(String hrSamplerName) {
        this.hrSamplerName = hrSamplerName;
    }

    public String getHrScheduler() {
        return hrScheduler;
    }

    public void setHrScheduler(String hrScheduler) {
        this.hrScheduler = hrScheduler;
    }

    public String getHrPrompt() {
        return hrPrompt;
    }

    public void setHrPrompt(String hrPrompt) {
        this.hrPrompt = hrPrompt;
    }

    public String getHrNegativePrompt() {
        return hrNegativePrompt;
    }

    public void setHrNegativePrompt(String hrNegativePrompt) {
        this.hrNegativePrompt = hrNegativePrompt;
    }

    public String getForceTaskId() {
        return forceTaskId;
    }

    public void setForceTaskId(String forceTaskId) {
        this.forceTaskId = forceTaskId;
    }

    public String getSamplerIndex() {
        return samplerIndex;
    }

    public void setSamplerIndex(String samplerIndex) {
        this.samplerIndex = samplerIndex;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getScriptArgs() {
        return scriptArgs;
    }

    public void setScriptArgs(String scriptArgs) {
        this.scriptArgs = scriptArgs;
    }

    public boolean isSendImages() {
        return sendImages;
    }

    public void setSendImages(boolean sendImages) {
        this.sendImages = sendImages;
    }

    public boolean isSaveImages() {
        return saveImages;
    }

    public void setSaveImages(boolean saveImages) {
        this.saveImages = saveImages;
    }

    public String getInfotext() {
        return infotext;
    }

    public void setInfotext(String infotext) {
        this.infotext = infotext;
    }
}

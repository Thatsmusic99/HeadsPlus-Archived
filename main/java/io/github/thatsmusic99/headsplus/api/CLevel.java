package io.github.thatsmusic99.headsplus.api;

public class CLevel implements Level {

    // T
    private String configName;
    private String displayName;
    private int requiredXP;
    private double addedVersion;

    public CLevel(String configName, String displayName, int requiredXP, double addedVersion) {
        this.configName = configName;
        this.displayName = displayName;
        this.requiredXP = requiredXP;
        this.addedVersion = addedVersion;
    }

    @Override
    public String getConfigName() {
        return configName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getRequiredXP() {
        return requiredXP;
    }

    @Override
    public double getAddedVersion() {
        return addedVersion;
    }
}

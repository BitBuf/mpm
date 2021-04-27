package dev.dewy.mpm.models;

public class Package {
    private final boolean modded;
    private final String mainClass;
    private final String tweakClass;
    private final String otherArgs;

    public Package(boolean modded, String mainClass, String tweakClass, String otherArgs) {
        this.modded = modded;
        this.mainClass = mainClass;
        this.tweakClass = tweakClass;
        this.otherArgs = otherArgs;
    }

    public boolean isModded() {
        return modded;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getTweakClass() {
        return tweakClass;
    }

    public String getOtherArgs() {
        return otherArgs;
    }
}

package dev.dewy.mpm.models;

public enum OperatingSystem {
    WINDOWS("win"),
    MACOS("osx"),
    LINUX("linux"),
    UNKNOWN(null);

    private final String representation;

    OperatingSystem(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}

package dev.dewy.mpm.settings;

public class BasicSettings {
    private final String repository;
    private final boolean verbose;

    public BasicSettings(String repository, boolean verbose) {
        this.repository = repository;
        this.verbose = verbose;
    }

    public String getRepository() {
        return repository;
    }

    public boolean isVerbose() {
        return verbose;
    }
}

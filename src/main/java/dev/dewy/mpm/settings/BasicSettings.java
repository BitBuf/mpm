package dev.dewy.mpm.settings;

public class BasicSettings {
    private String repository;
    private boolean verbose;

    public BasicSettings(String repository, boolean verbose) {
        this.repository = repository;
        this.verbose = verbose;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}

package dev.dewy.mpm.settings;

import java.util.List;

public class EnvironmentsSettings {
    private final List<String> environments;

    public EnvironmentsSettings(List<String> environments) {
        this.environments = environments;
    }

    public List<String> getEnvironments() {
        return environments;
    }
}

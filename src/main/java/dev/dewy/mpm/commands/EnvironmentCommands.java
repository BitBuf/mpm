package dev.dewy.mpm.commands;

import dev.dewy.mpm.MPM;
import dev.dewy.mpm.settings.SettingsManager;
import dev.dewy.mpm.utils.ConsoleUtils;
import dev.dewy.mpm.utils.FileSystemUtils;
import picocli.CommandLine.*;

import java.io.File;
import java.util.List;

@Command(
        name = "env",
        description = "Manage your environments."
)
public class EnvironmentCommands {
    @Command(name = "create", description = "Create a new environment.")
    public void create(@Parameters(description = "Environments to create.") String[] envs) {
        List<String> existing = MPM.environmentsSettings.getEnvironments();

        for (String env : envs) {
            if (!existing.contains(env)) {
                ConsoleUtils.info("Creating environment " + env);

                new File(MPM.ENV_DIR + "/" + env).mkdirs();
                existing.add(env);
            } else {
                ConsoleUtils.warning("Environment " + env + " already exists. It will not be re-created.");
            }
        }

        SettingsManager.save();
    }

    @Command(name = "remove", description = "Remove (delete) an existing environment.")
    public void remove(@Parameters(description = "Environments to remove.") String[] envs) {
        List<String> existing = MPM.environmentsSettings.getEnvironments();

        for (String env : envs) {
            if (existing.contains(env)) {
                ConsoleUtils.info("Removing environment " + env);

                FileSystemUtils.deleteDirectory(new File(MPM.ENV_DIR + "/" + env));
                existing.remove(env);
            } else {
                ConsoleUtils.warning("Environment " + env + " doesn't yet exist. It will not be removed.");
            }
        }

        SettingsManager.save();
    }
}

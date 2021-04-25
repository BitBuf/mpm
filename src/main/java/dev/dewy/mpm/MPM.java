package dev.dewy.mpm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.dewy.mpm.commands.AccountCommands;
import dev.dewy.mpm.commands.EnvironmentCommands;
import dev.dewy.mpm.commands.InstallCommand;
import dev.dewy.mpm.commands.LaunchCommand;
import dev.dewy.mpm.models.OperatingSystem;
import dev.dewy.mpm.settings.AuthSettings;
import dev.dewy.mpm.settings.BasicSettings;
import dev.dewy.mpm.settings.EnvironmentsSettings;
import dev.dewy.mpm.settings.SettingsManager;
import dev.dewy.mpm.utils.ConsoleUtils;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(
        name = "mpm",
        version = "MPM (Minecraft Package Manager) v" + MPM.VERSION,
        description = "Work with Minecraft from the command line.",
        mixinStandardHelpOptions = true,
        subcommands = {
                EnvironmentCommands.class,
                AccountCommands.class,
                InstallCommand.class,
                LaunchCommand.class
        }
)
public class MPM implements Callable<Integer> {
    public static final String VERSION = "1.0.0";

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static BasicSettings basicSettings;
    public static AuthSettings authSettings;
    public static EnvironmentsSettings environmentsSettings;

    public static final File BASE_DIR = new File(System.getProperty("user.home") + "/.mpm");
    public static final File CACHE_DIR = new File(BASE_DIR + "/cache");
    public static final File ASSETS_DIR = new File(BASE_DIR + "/assets");
    public static final File PACKAGES_DIR = new File(BASE_DIR + "/packages");
    public static final File ENV_DIR = new File(BASE_DIR + "/environments");

    public static final File BASIC_SETTINGS = new File(BASE_DIR + "/settings.json");
    public static final File AUTH_SETTINGS = new File(BASE_DIR + "/auth.json");
    public static final File ENV_SETTINGS = new File(BASE_DIR + "/environments.json");

    @Override
    public Integer call() {
        return 0;
    }

    public static OperatingSystem getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("nux") || os.contains("nix") || os.indexOf("aix") > 0) {
            return OperatingSystem.LINUX;
        } else if (os.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (os.contains("mac")) {
            return OperatingSystem.MACOS;
        } else {
            return OperatingSystem.UNKNOWN;
        }
    }

    private static void init() {
        CACHE_DIR.mkdirs();
        ASSETS_DIR.mkdirs();
        PACKAGES_DIR.mkdirs();
        ENV_DIR.mkdirs();

        try {
            if (BASIC_SETTINGS.createNewFile()) {
                basicSettings = new BasicSettings("https://mpm.dewy.dev", false);
            } else {
                basicSettings = SettingsManager.deserializeBasic();
            }

            if (AUTH_SETTINGS.createNewFile()) {
                authSettings = new AuthSettings(new ArrayList<>());
            } else {
                authSettings = SettingsManager.deserializeAuth();
            }

            if (ENV_SETTINGS.createNewFile()) {
                environmentsSettings = new EnvironmentsSettings(new ArrayList<>());
            } else {
                environmentsSettings = SettingsManager.deserializeEnv();
            }
        } catch (IOException e) {
            ConsoleUtils.error("An exception occurred when loading MPM settings.");
            e.printStackTrace();
        }

        SettingsManager.save();
    }

    public static void main(String[] args) {
        init();

        int exitCode = new CommandLine(new MPM()).execute(args);
        System.exit(exitCode);
    }
}

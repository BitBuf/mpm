package dev.dewy.mpm.settings;

import com.google.gson.stream.JsonReader;
import dev.dewy.mpm.MPM;
import dev.dewy.mpm.utils.ConsoleUtils;

import java.io.*;

public class SettingsManager {
    public static BasicSettings deserializeBasic() throws FileNotFoundException {
        return MPM.GSON.fromJson(new JsonReader(new FileReader(MPM.SETTINGS)), BasicSettings.class);
    }

    public static AuthSettings deserializeAuth() throws FileNotFoundException {
        return MPM.GSON.fromJson(new JsonReader(new FileReader(MPM.AUTH_SETTINGS)), AuthSettings.class);
    }

    public static EnvironmentsSettings deserializeEnv() throws FileNotFoundException {
        return MPM.GSON.fromJson(new JsonReader(new FileReader(MPM.ENV_SETTINGS)), EnvironmentsSettings.class);
    }

    public static void save() {
        if (MPM.basicSettings.isVerbose()) {
            System.out.println("Saving configuration...");
        }

        try (Writer basicWriter = new FileWriter(MPM.SETTINGS);
             Writer authWriter = new FileWriter(MPM.AUTH_SETTINGS);
             Writer envWriter = new FileWriter(MPM.ENV_SETTINGS)) {

            MPM.GSON.toJson(MPM.basicSettings, basicWriter);
            MPM.GSON.toJson(MPM.authSettings, authWriter);
            MPM.GSON.toJson(MPM.environmentsSettings, envWriter);
        } catch (IOException e) {
            ConsoleUtils.error("An exception occurred whilst saving MPM settings.");
            e.printStackTrace();
        }
    }
}

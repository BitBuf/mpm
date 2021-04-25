package dev.dewy.mpm.commands;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import dev.dewy.mpm.MPM;
import dev.dewy.mpm.models.Account;
import dev.dewy.mpm.settings.SettingsManager;
import dev.dewy.mpm.utils.ConsoleUtils;
import picocli.CommandLine.*;

@Command(
        name = "account",
        description = "Manage your accounts."
)
public class AccountCommands {
    @Command(name = "add", description = "Add a new account.")
    public void add(@Parameters(description = "Account to add.") String[] pair) {
        if (pair.length == 2) {
            ConsoleUtils.info("Logging in with account " + pair[0]);

            AuthenticationService auth = new AuthenticationService();
            auth.setUsername(pair[0]);
            auth.setPassword(pair[1]);

            try {
                auth.login();
            } catch (RequestException e) {
                ConsoleUtils.error("An exception occurred when logging in with account " + pair[0]);
                e.printStackTrace();

                return;
            }

            MPM.authSettings.getAccounts().add(new Account(
                    pair[0],
                    pair[1],
                    auth.getAccessToken(),
                    auth.getClientToken()
            ));

            SettingsManager.save();
            ConsoleUtils.info("Added account " + pair[0]);
        }
    }

    @Command(name = "remove", description = "Remove an account.")
    public void remove(@Parameters(description = "Account to remove.") String account) {
        ConsoleUtils.info("Removing account " + account);

        MPM.authSettings.getAccounts().remove(new Account(account));

        SettingsManager.save();
        ConsoleUtils.info("Removed account " + account);
    }
}

package dev.dewy.mpm.commands;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import dev.dewy.mpm.MPM;
import dev.dewy.mpm.models.Account;
import dev.dewy.mpm.utils.ConsoleUtils;
import dev.dewy.mpm.utils.FileSystemUtils;
import dev.dewy.mpm.utils.NetUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Command(
        name = "launch",
        description = "Launch Minecraft packages."
)
public class LaunchCommand implements Callable<Integer> {
    @Parameters(description = "Package to launch, the environment and account to launch under.")
    public String[] arguments;

    @Override
    public Integer call() {
        if (arguments.length > 1 && arguments.length < 4) {
            File packageDir = new File(MPM.PACKAGES_DIR + "/" + arguments[0]);
            File environment = new File(MPM.ENV_DIR + "/" + arguments[1]);

            if (packageDir.exists() && environment.exists()) {
                File librariesDir = new File(packageDir + "/libraries");

                for (File lib : librariesDir.listFiles()) {
                    try {
                        FileSystemUtils.loadLibrary(lib);
                    } catch (Exception e) {
                        ConsoleUtils.error("Unable to load library " + lib.getName() + ". Aborting.");
                        e.printStackTrace();

                        return -1;
                    }
                }

                try {
                    FileSystemUtils.loadLibrary(new File(packageDir + "/" + packageDir.getName() + ".jar"));
                } catch (Exception e) {
                    ConsoleUtils.error("Unable to load main game into classpath. Aborting.");
                    e.printStackTrace();

                    return -1;
                }

                System.setProperty("org.lwjgl.librarypath", librariesDir.getAbsolutePath());

                Method mainMethod = null;
                try {
                    mainMethod = Class.forName("net.minecraft.client.main.Main").getMethod("main", String[].class);
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    ConsoleUtils.error("Unable to locate Minecraft main method. Aborting.");
                    e.printStackTrace();
                }

                StringBuilder sb = new StringBuilder("--version MPM --gameDir " + environment + " --resourcePackDir " + environment + "/resourcepacks --assetsDir " + new File(packageDir + "/assets" + " --assetIndex " + arguments[0]));

                if (arguments.length == 3) {
                    Account account = new Account(arguments[2]);

                    if (MPM.authSettings.getAccounts().contains(account)) {
                        Account fullAccount = MPM.authSettings.getAccounts().get(MPM.authSettings.getAccounts().indexOf(account));
                        AuthenticationService authService = new AuthenticationService();

                        authService.setUsername(fullAccount.getEmail());
                        authService.setPassword(fullAccount.getPassword());

                        try {
                            authService.login();
                        } catch (RequestException e) {
                            ConsoleUtils.error("Unable to authenticate with account " + fullAccount.getEmail() + ". Aborting launch.");
                            e.printStackTrace();

                            return -1;
                        }

                        try {
                            sb.append(" --accessToken ").append(authService.getAccessToken()).append(" --username ").append(fullAccount.getUsername()).append(" --uuid ").append(NetUtils.getUuidFromName(fullAccount.getUsername()));
                        } catch (IOException e) {
                            ConsoleUtils.error("Unable to obtain UUID of account " + fullAccount.getEmail() + ". Aborting launch.");
                            e.printStackTrace();

                            return -1;
                        }
                    } else {
                        ConsoleUtils.warning("Account " + account.getEmail() + " not found. Will launch in offline mode.");
                        sb.append(" --accessToken MPM");
                    }
                } else {
                    sb.append(" --accessToken MPM");
                }

                try {
                    mainMethod.invoke(null, (Object) sb.toString().split(" "));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }
}

package dev.dewy.mpm.commands;

import dev.dewy.mpm.MPM;
import dev.dewy.mpm.utils.ConsoleUtils;
import dev.dewy.mpm.utils.FileSystemUtils;
import picocli.CommandLine.*;

import java.io.File;
import java.util.concurrent.Callable;

@Command(
        name = "remove",
        description = "Delete Minecraft packages."
)
public class RemoveCommand implements Callable<Integer> {
    @Parameters(description = "Packages to remove.")
    public String[] packages;

    @Override
    public Integer call() {
        for (String pkg : packages) {
            File pkgDir = new File(MPM.PACKAGES_DIR + "/" + pkg);

            if (!pkgDir.exists()) {
                ConsoleUtils.warning("Package " + pkg + " is not installed. It will not be removed.");
                FileSystemUtils.deleteDirectory(new File(MPM.PACKAGES_DIR + "/" + pkg));
            }

            ConsoleUtils.warning("Removing package " + pkg);
            FileSystemUtils.deleteDirectory(pkgDir);
        }

        return 0;
    }
}

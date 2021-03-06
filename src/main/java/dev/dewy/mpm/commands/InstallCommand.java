package dev.dewy.mpm.commands;

import dev.dewy.mpm.MPM;
import dev.dewy.mpm.utils.ConsoleUtils;
import dev.dewy.mpm.utils.FileSystemUtils;
import dev.dewy.mpm.utils.NetUtils;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import picocli.CommandLine.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

@Command(
        name = "install",
        description = "Install Minecraft packages."
)
public class InstallCommand implements Callable<Integer> {
    @Parameters(description = "Packages to install.")
    public String[] packages;

    @Override
    public Integer call() {
        for (String pkg : packages) {
            if (new File(MPM.PACKAGES_DIR + "/" + pkg).exists()) {
                ConsoleUtils.warning("Package " + pkg + " is already installed. Reinstalling...");
                FileSystemUtils.deleteDirectory(new File(MPM.PACKAGES_DIR + "/" + pkg));
            }

            try {
                ConsoleUtils.info("Installing package " + pkg);

                String packageFileName = pkg + "-" + MPM.getOperatingSystem().getRepresentation() + ".tar.gz";
                URL packageUrl = new URL(MPM.basicSettings.getRepository() + "/" + packageFileName);

                File tar = new File(MPM.CACHE_DIR + "/" + packageFileName);
                File tmp = new File(MPM.CACHE_DIR + "/tmp/");

                if (NetUtils.exists(packageUrl)) {
                    NetUtils.download(packageUrl, tar);

                    Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
                    archiver.extract(tar, tmp);

                    File destination = new File(MPM.PACKAGES_DIR + "/" + pkg);
                    destination.mkdirs();
                    Files.move(new File(tmp.getPath() + "/" + pkg).toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    tmp.delete();
                    tar.delete();
                } else {
                    ConsoleUtils.warning("Package " + pkg + " not found in repository " + MPM.basicSettings.getRepository());
                }
            } catch (IOException e) {
                ConsoleUtils.error("Unable to install package " + pkg);
                e.printStackTrace();
            }
        }

        return 0;
    }
}

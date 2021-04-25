package dev.dewy.mpm.utils;

import dev.dewy.mpm.MPM;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class FileSystemUtils {
    public static boolean deleteDirectory(File dir) {
        File[] allContents = dir.listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }

        return dir.delete();
    }

    public static void loadLibrary(File jar) throws Exception {
        if (MPM.basicSettings.isVerbose()) {
            System.out.println("Loading library " + jar);
        }

        URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL url = jar.toURI().toURL();

        for (URL it : loader.getURLs()){
            if (it.equals(url)){
                return;
            }
        }

        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(loader, url);
    }
}

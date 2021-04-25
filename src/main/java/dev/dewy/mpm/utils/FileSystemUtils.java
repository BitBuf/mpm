package dev.dewy.mpm.utils;

import java.io.File;

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
}

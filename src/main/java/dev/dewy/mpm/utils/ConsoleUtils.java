package dev.dewy.mpm.utils;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

public class ConsoleUtils {
    public static void info(String message) {
        System.out.println(colorize(message, BRIGHT_BLUE_TEXT()));
    }

    public static void warning(String message) {
        System.out.println(colorize("WARNING: " + message, YELLOW_TEXT()));
    }

    public static void error(String message) {
        System.out.println(colorize("ERROR: " + message, BRIGHT_RED_TEXT()));
    }
}

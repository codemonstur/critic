package critic.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

/**
 * The {@code Logger} enum.
 */
public enum Logger {;
    private static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Performs the {@code info} operation.
     *
     * @param message the message
     */
    public static void info(final String message) {
        System.out.println(format("[%s] [INFO] " + message, LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS)));
    }
    /**
     * Performs the {@code warn} operation.
     *
     * @param message the message
     */
    public static void warn(final String message) {
        System.err.println(format("[%s] [WARN] " + message, LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS)));
    }
    /**
     * Performs the {@code error} operation.
     *
     * @param message the message
     */
    public static void error(final String message) {
        System.err.println(format("[%s] [FAIL] " + message, LocalDateTime.now().format(YYYY_MM_DD_HH_MM_SS)));
    }
}

package critic.util;

import static critic.util.Logger.error;

/**
 * The {@code CliApp} enum.
 */
public enum CliApp {;

    /**
     * The {@code Constructor} interface.
     */
    public interface Constructor {
        /**
         * Performs the {@code build} operation.
         *
         * @throws Exception if the operation fails
         */
        void build() throws Exception;
    }

    /**
     * Performs the {@code buildApp} operation.
     *
     * @param constructor the constructor
     */
    public static void buildApp(final Constructor constructor) {
        try {
            constructor.build();
        } catch (Exception e) {
            error(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

package critic.util;

import static critic.util.Logger.error;

public enum CliApp {;

    public interface Constructor {
        void build() throws Exception;
    }

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

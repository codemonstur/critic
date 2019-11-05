package critic.actions;

import critic.model.CliArguments;
import critic.util.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum SpellingGrammar {;

    public static void execute(final CliArguments arguments) throws IOException {
        Files.walk(arguments.source.toPath())
            .filter(path -> path.toFile().isFile())
            .forEach(SpellingGrammar::checkSpelling);
    }

    private static void checkSpelling(final Path path) {
        try {
            final String content = Files.readString(path);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }
}

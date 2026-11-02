package critic.actions;

import critic.model.CliArguments;
import critic.util.Logger;
import critic.util.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static critic.util.IO.newFileWithDirectory;
import static critic.util.IO.writeToFile;

public enum SpellingGrammar {;

    public static void execute(final CliArguments arguments) throws IOException {
        final Map<String, Long> errors = new HashMap<>();

        Files.walk(arguments.source.toPath())
                .filter(path -> path.toFile().isFile())
                .forEach(path -> countErrors(errors, path));

        final String output = arguments.outputFormat.toString(errors);
        writeToFile(newFileWithDirectory(arguments.target, "reports",
                "spelling." + arguments.outputFormat.name()), output);
    }

    private static void countErrors(final Map<String, Long> errors, final Path path) {
        try {
            final String content = Files.readString(path);
            if (content.indexOf('\u0000') >= 0) return; // skip binary files
            errors.put(path.toAbsolutePath().toString(), Text.countSpellingErrors(content));
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

}
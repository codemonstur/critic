package critic.actions;

import critic.model.CliArguments;
import critic.model.TextComplexity;
import critic.util.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static critic.util.IO.newFileWithDirectory;
import static critic.util.IO.writeToFile;
import static java.nio.file.Files.walk;

public enum Metrics {;

    public static void execute(final CliArguments arguments) throws IOException {
        final Map<String, TextComplexity> scores = new HashMap<>();

        walk(arguments.source.toPath())
            .filter(path -> path.toFile().isFile())
            .forEach(path -> calculateComplexity(scores, path));

        final String output = arguments.outputFormat.toString(scores);
        writeToFile(newFileWithDirectory(arguments.target, "reports", "metrics.json"), output);
    }

    private static void calculateComplexity(final Map<String, TextComplexity> scores, final Path path) {
        try {
            scores.put(path.toAbsolutePath().toString(), new TextComplexity(Files.readString(path)));
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    /*
        Automatic Readability should not be higher than 6. But lower is better
    *   1 	5-6 	Kindergarten
        2 	6-7 	First/Second Grade
        3 	7-9 	Third Grade
        4 	9-10 	Fourth Grade
        5 	10-11 	Fifth Grade
        6 	11-12 	Sixth Grade
        7 	12-13 	Seventh Grade
        8 	13-14 	Eighth Grade
        9 	14-15 	Ninth Grade
        10 	15-16 	Tenth Grade
        11 	16-17 	Eleventh Grade
        12 	17-18 	Twelfth grade
        13 	18-24 	College student
        14 	24+ 	Professor
    * https://en.wikipedia.org/wiki/Automated_readability_index

        fleschReadingEase should be as high as possible. Don't fall below 70.

    * https://blog.quiet.ly/community/how-readability-tests-strengthen-content-marketing/
    *
    *
    * */

}

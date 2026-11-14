package critic;

import critic.model.CliArguments;

import static critic.util.CliApp.buildApp;
import static jcli.CliParserBuilder.newCliParser;

/**
 * The {@code Main} enum.
 */
public enum Main {;

    /**
     * Runs the application.
     *
     * @param args the command-line arguments
     */
    public static void main(final String... args) {
        buildApp(() -> {
            final CliArguments arguments = newCliParser(CliArguments::new)
                    .onErrorPrintHelpAndExit()
                    .onHelpPrintHelpAndExit()
                    .parse(args);

            arguments.action.execute(arguments);
        });
    }

}

package critic;

import critic.model.CliArguments;

import static critic.util.CliApp.buildApp;
import static jcli.CliParserBuilder.newCliParser;

public enum Main {;

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

package critic.model;

import critic.actions.Action;
import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;

import java.io.File;

/**
 * The {@code CliArguments} class.
 */
@CliCommand(name = "critic", description = "A tool for critiquing large documents")
public final class CliArguments {

    /**
     * The {@code source} field.
     */
    @CliOption(name = 's', longName = "source-dir", defaultValue = "src", description = "The directory to find the text")
    public File source;
    /**
     * The {@code target} field.
     */
    @CliOption(name = 't', longName = "target-dir", defaultValue = "target", description = "The directory to write the results")
    public String target;
    /**
     * The {@code outputFormat} field.
     */
    @CliOption(name = 'o', longName = "output-format", defaultValue = "json", description = "The output format for the report. One of; json, xml, yaml")
    public OutputFormat outputFormat;

    /**
     * The {@code action} field.
     */
    @CliPositional(defaultValue = "check")
    public Action action;


    /**
     * Creates a new instance.
     */
    public CliArguments() {
    }
}

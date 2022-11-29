package critic.model;

import critic.actions.Action;
import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;

import java.io.File;

@CliCommand(name = "critic", description = "A tool for critiquing large documents")
public final class CliArguments {

    @CliOption(name = 's', longName = "source-dir", defaultValue = "src", description = "The directory to find the text")
    public File source;
    @CliOption(name = 't', longName = "target-dir", defaultValue = "target", description = "The directory to write the results")
    public String target;
    @CliOption(name = 'o', longName = "output-format", defaultValue = "json", description = "The output format for the report. One of; json, xml, yaml")
    public OutputFormat outputFormat;

    @CliPositional(defaultValue = "check")
    public Action action;

}

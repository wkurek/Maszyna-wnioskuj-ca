package util.arguments;

import com.beust.jcommander.Parameter;

public class Arguments {
    @Parameter(names = "-knowledgeBase", description = "Path to file containing knowledge base.", required = true)
    public String knowledgeBaseFilePath;

    @Parameter(names = {"-var", "-variables"}, description = "Path to file containing variables used in knowledge base.",
            required = true)
    public String variablesFilePath;

    @Parameter(names = {"-const", "-constants"}, description = "Path to file containing constants used in knowledge base.",
            required = true)
    public String constantsFilePath;

    @Parameter(names = {"-arg", "-argument"}, description = "Path to file containing argument to be proved.",
            required = true)
    public String argumentFilePath;


    @Parameter(names = { "-h", "-help" }, help = true, description = "help mode.")
    public boolean help;
}

package com.conf;

import org.apache.commons.cli.*;

public class CmdMain {
    private static final String USAGE = "CmdMain";
    private static final String OPT_File = "f";
    private static final String OPT_OUT = "o";
    private static final String OPT_IN = "i";
    private static final String OPT_HELP = "h";
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder(OPT_File).desc("user custom types file.")
                .hasArg().argName("typedef file").required().longOpt("file").build());
        options.addOption(Option.builder(OPT_HELP).desc("show the help menu.")
                .hasArg(false).longOpt("help").build());
        options.addOption(Option.builder(OPT_IN).desc("the dir where the *.xls/*.xlsx files in.")
                .hasArg().longOpt("in").argName("Excel files dir").build());
        options.addOption(Option.builder(OPT_OUT).desc("the dir where to generate output files.")
                .hasArg().longOpt("out").argName("Gen files dir").build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            if (cmd.hasOption(OPT_HELP)) {
                printHelp(options);
                return;
            }
            String typedefFile = cmd.getOptionValue(OPT_File);
            String excelDir = cmd.getOptionValue(OPT_OUT);
            runConvert(typedefFile, excelDir);
        } catch (Exception  e) {
            System.out.println(e.getLocalizedMessage());
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(USAGE, options);
    }

    public static void runConvert(String typedefFile, String excelDir) {
        System.out.println(typedefFile);
        System.out.println(excelDir);
    }
}

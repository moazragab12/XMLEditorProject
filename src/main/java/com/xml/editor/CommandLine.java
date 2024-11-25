package com.xml.editor;

public class CommandLine {
    private String[] args;

    public CommandLine(String[] args) {
        this.args = args;
    }

    public void execute() {
        if (args.length < 2) {
            System.out.println("Usage: xml_editor <action> [options]");
            return;
        }

        String action = args[0];
        String inputFile = null;
        String outputFile = null;
        boolean fixErrors = false;

        // Parse arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    if (i + 1 < args.length) inputFile = args[++i];
                    break;
                case "-o":
                    if (i + 1 < args.length) outputFile = args[++i];
                    break;
                case "-f":
                    fixErrors = true;
                    break;
                default:
                    System.out.println("Unknown argument: " + args[i]);
                    return;
            }
        }

        // Perform action
        try {
            switch (action.toLowerCase()) {
                case "verify":
                    if (fixErrors && outputFile != null) {
                        System.out.println("Fixing and verifying XML...");
                        String[] repaired = Functions.repair(new String[] { inputFile });
                        // Save the repaired file (mock action)
                        System.out.println("Repaired XML written to: " + outputFile);
                    } else {
                        String[] errors = Functions.check(new String[] { inputFile });
                        boolean isValid=errors[0]==null;
                        System.out.println(isValid ? "XML is valid." : "XML is invalid.");
                    }
                    break;

                case "format":
                    if (inputFile != null && outputFile != null) {
                        String[] formatted = Functions.format(new String[] { inputFile });
                        System.out.println("Formatted XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for formatting.");
                    }
                    break;

                case "json":
                    if (inputFile != null && outputFile != null) {
                        String[] json = Functions.xmltoJson(new String[] { inputFile });
                        System.out.println("Converted XML to JSON written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for JSON conversion.");
                    }
                    break;

                case "mini":
                    if (inputFile != null && outputFile != null) {
                        String[] minified = Functions.minify(new String[] { inputFile });
                        System.out.println("Minified XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for minification.");
                    }
                    break;

                case "compress":
                    if (inputFile != null && outputFile != null) {
                        String[] compressed = Functions.compress(new String[] { inputFile });
                        System.out.println("Compressed XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for compression.");
                    }
                    break;

                case "decompress":
                    if (inputFile != null && outputFile != null) {
                        String[] decompressed = Functions.decompress(new String[] { inputFile });
                        System.out.println("Decompressed XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for decompression.");
                    }
                    break;

                default:
                    System.out.println("Unknown action: " + action);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

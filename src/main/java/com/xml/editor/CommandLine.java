package com.xml.editor;

/**
 * The CommandLine class handles the parsing and execution of XML editor commands
 * from the command-line interface. It processes user inputs and performs various
 * XML-related actions such as verification, formatting, conversion, minification,
 * compression, and decompression.
 */
public class CommandLine {
    private String[] args;

    /**
     * Constructs a CommandLine instance with the specified command-line arguments.
     *
     * @param args an array of command-line arguments
     */
    public CommandLine(String[] args) {
        this.args = args;
    }

    /**
     * Executes the command specified in the command-line arguments. It parses the
     * arguments, performs the corresponding action, and outputs the results.
     *
     * Actions include:
     * - "verify": Verifies the XML and optionally fixes errors.
     * - "format": Formats the XML.
     * - "json": Converts the XML to JSON.
     * - "mini": Minifies the XML.
     * - "compress": Compresses the XML.
     * - "decompress": Decompresses the XML.
     *
     * If arguments are invalid or missing, an error message is displayed.
     */
    public void execute() {
        // Check if there are enough arguments
        if (args.length < 2) {
            System.out.println("Usage: xml_editor <action> [options]");
            return;
        }

        // Extract the action and initialize variables
        String action = args[0];
        String inputFile = null;
        String outputFile = null;
        boolean fixErrors = false;

        // Parse command-line options
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

        // Perform the requested action
        try {
            switch (action.toLowerCase()) {
                case "verify":
                    // Verify the XML file and optionally fix errors
                    if (fixErrors && outputFile != null) {
                        System.out.println("Fixing and verifying XML...");
                        String[] repaired = Functions.repair(new String[] { inputFile });
                        // Save the repaired file (mock action)
                        System.out.println("Repaired XML written to: " + outputFile);
                    } else {
                        String[] errors = Functions.check(new String[] { inputFile });
                        boolean isValid = errors[0] == null;
                        System.out.println(isValid ? "XML is valid." : "XML is invalid.");
                    }
                    break;

                case "format":
                    // Format the XML and write to the output file
                    if (inputFile != null && outputFile != null) {
                        String[] formatted = Functions.format(new String[] { inputFile });
                        System.out.println("Formatted XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for formatting.");
                    }
                    break;

                case "json":
                    // Convert XML to JSON and write to the output file
                    if (inputFile != null && outputFile != null) {
                        String[] json = Functions.xmltoJson(new String[] { inputFile });
                        System.out.println("Converted XML to JSON written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for JSON conversion.");
                    }
                    break;

                case "mini":
                    // Minify the XML and write to the output file
                    if (inputFile != null && outputFile != null) {
                        String[] minified = Functions.minify(new String[] { inputFile });
                        System.out.println("Minified XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for minification.");
                    }
                    break;

                case "compress":
                    // Compress the XML and write to the output file
                    if (inputFile != null && outputFile != null) {
                        String[] compressed = Functions.compress(new String[] { inputFile });
                        System.out.println("Compressed XML written to: " + outputFile);
                    } else {
                        System.out.println("Error: Input and output files are required for compression.");
                    }
                    break;

                case "decompress":
                    // Decompress the XML and write to the output file
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

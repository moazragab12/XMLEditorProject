package com.xml.editor;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface for defining command functions.
 */
@FunctionalInterface
interface commandFunctions {
    /**
     * Executes the command with the provided string input.
     *
     * @param s The input string for the command.
     * @throws IOException If an I/O error occurs.
     */
    void command(String s) throws IOException;
}

/**
 * Contains the functions that handle various XML file operations, such as compression,
 * verification, formatting, and others.
 */
class functionsCL {

    /**
     * Compresses the specified XML file.
     *
     * @param s The input string containing file paths.
     */
    public static void compress(String s) {
        commandLineV2.write_file(s, 5, Functions.compress(commandLineV2.read_file(s, 3)));
        System.out.println("file compressed");
    }

    /**
     * Verifies the specified XML file and repairs it if needed.
     *
     * @param s The input string containing file paths.
     */
    public static void verify(String s) {
        if (Functions.check(commandLineV2.read_file(s, 3)).length == 0)
            System.out.println("file is valid");
        else {
            if (s.split(" ").length == 4) {
                System.out.println("file isn't valid");
                System.out.println(String.join("\n", Functions.check(commandLineV2.read_file(s, 3))));
            } else {
                commandLineV2.write_file(s, 6, Functions.repair(commandLineV2.read_file(s, 3)));
                System.out.println("file repaired");
            }
        }
    }

    /**
     * Formats the specified XML file.
     *
     * @param s The input string containing file paths.
     */
    public static void format(String s) {
        commandLineV2.write_file(s, 5, Functions.format(commandLineV2.read_file(s, 3)));
        System.out.println("file formatted");
    }

    /**
     * Converts the specified XML file to JSON format.
     *
     * @param s The input string containing file paths.
     */
    public static void json(String s) {
        commandLineV2.write_file(s, 5, Functions.xmltoJson(commandLineV2.read_file(s, 3)));
        System.out.println("file converted");
    }

    /**
     * Minifies the specified XML file.
     *
     * @param s The input string containing file paths.
     */
    public static void mini(String s) {
        commandLineV2.write_file(s, 5, Functions.minify(commandLineV2.read_file(s, 3)));
        System.out.println("file minified");
    }

    /**
     * Decompresses the specified file.
     *
     * @param s The input string containing file paths.
     */
    public static void decompress(String s) {
        commandLineV2.write_file(s, 5, Functions.decompress(commandLineV2.read_file(s, 3)));
        System.out.println("file decompressed");
    }

    /**
     * Draws a graph based on the XML file and saves it as a JPG image.
     *
     * @param s The input string containing file paths.
     * @throws IOException If an I/O error occurs.
     */
    public static void draw(String s) throws IOException {
        // Run the drawing code on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Draw graph on the canvas
            SocialNetworkGraph graph = Functions.draw(commandLineV2.read_file(s, 3));
            Canvas canvas = graph.drawGraphOnCanvas(800, 600);

            // Create a WritableImage to take a snapshot of the canvas
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            Image image = canvas.snapshot(null, writableImage);  // Taking snapshot of canvas

            // Extract the file name from input string (make sure it’s a valid file path)
            String[] parts = s.split(" ");
            String filePath = (parts.length > 5) ? parts[5] : "output.jpg";  // Default to "output.jpg" if no path is found

            // Ensure file path ends with .jpg extension
            if (!filePath.endsWith(".jpg")) {
                filePath += ".jpg";  // Add .jpg if it's not already present
            }

            // Convert the JavaFX Image to a BufferedImage
            BufferedImage bufferedImage = new BufferedImage((int) canvas.getWidth(), (int) canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < canvas.getWidth(); x++) {
                for (int y = 0; y < canvas.getHeight(); y++) {
                    javafx.scene.paint.Color fxColor = writableImage.getPixelReader().getColor(x, y);
                    Color awtColor = new Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue());
                    bufferedImage.setRGB(x, y, awtColor.getRGB());
                }
            }

            // Save the BufferedImage as a JPG file
            File outputFile = new File(filePath);
            try {
                ImageIO.write(bufferedImage, "jpg", outputFile);
                System.out.println("file drawn");
            }
            catch (IOException e) {
                System.err.println("Failed to save image: " + e.getMessage());
            }
        });
    }

    /**
     * Finds the most active users in the social network based on the XML file.
     *
     * @param s The input string containing file paths.
     */
    public static void most_active(String s) {
        ArrayList<User> most_active = NetworkAnalysis.mostActive(String.join("\n", commandLineV2.read_file(s, 3)));
        System.out.println(most_active.toString());
    }

    /**
     * Finds the most influential users in the social network based on the XML file.
     *
     * @param s The input string containing file paths.
     */
    public static void most_influencer(String s) {
        ArrayList<User> most_influencer = NetworkAnalysis.mostInfluencer(String.join("\n", commandLineV2.read_file(s, 3)));
        System.out.println(most_influencer.toString());
    }

    /**
     * Finds the mutual followers between the specified users in the social network.
     *
     * @param s The input string containing file paths and user IDs.
     */
    public static void mutual(String s) {
        String[] idsAsString = s.split(" ")[5].split(",");
        int[] ids = Arrays.stream(idsAsString)
                .mapToInt(Integer::parseInt)
                .toArray();
        ArrayList<User> mutual = NetworkAnalysis.mutualFollowers(String.join("\n", commandLineV2.read_file(s, 3)), ids);
        System.out.println(NetworkAnalysis.printAllUsres(mutual));
    }

    /**
     * Suggests new followers for the specified user based on the XML file.
     *
     * @param s The input string containing file paths and user ID.
     */
    public static void suggest(String s) {
        String idAsString = s.split(" ")[5];
        int id = Integer.parseInt(idAsString);
        String[] suggest = Functions.suggest(commandLineV2.read_file(s, 3), id);
        if (String.join("\n", suggest).isEmpty()) System.out.println("No Suggestions for the user with id: " + id);
        else System.out.println(String.join("\n", suggest));
    }

    /**
     * Searches for a specific word or topic in the XML file.
     *
     * @param s The input string containing file paths and the search type.
     */
    public static void search(String s) {
        String search = s.split(" ")[3];
        if (Objects.equals(s.split(" ")[2], "-w"))
            System.out.println(String.join("\n", Functions.wordSearch(commandLineV2.read_file(s, 5), search)));
        else {
            System.out.println(String.join("\n", Functions.topicSearch(commandLineV2.read_file(s, 5), search)));
        }
    }
}

/**
 * The commandLineV2 class processes and executes the commands provided in the command line.
 */
public class commandLineV2 {
    static Map<String, commandFunctions> commands;

    /**
     * Processes the given command line arguments and executes the corresponding function.
     *
     * @param args The command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    static void processCommand(String[] args) throws IOException {
        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            command.append(arg).append(" ");
        }

        String regex1 = "xml_editor [a-z]+ -i (\\S+\\.xml) -o (\\S+\\.xml)" +
                "|xml_editor json -i (\\S+\\.xml) -o (\\S+\\.json)" +
                "|xml_editor compress -i (\\S+\\.xml) -o (\\S+\\.comp)" +
                "|xml_editor decompress -i (\\S+\\.comp) -o (\\S+\\.xml)" +
                "|xml_editor verify -i (\\S+\\.xml)" +
                "|xml_editor most_active -i (\\S+\\.xml)" +
                "|xml_editor most_influencer -i (\\S+\\.xml)" +
                "|xml_editor verify -i (\\S+\\.xml) -f -o (\\S+\\.xml)" +
                "|xml_editor draw -i (\\S+\\.xml) -o (\\S+\\.jpg)" +
                "|xml_editor mutual -i (\\S+\\.xml) -ids (\\d+(,\\d+)*)" +
                "|xml_editor suggest -i (\\S+\\.xml) -id (\\d+)" +
                "(?i)|xml_editor search -w [a-z]+ -i (\\S+\\.xml)" +
                "|xml_editor search -t [a-z]+ -i (\\S+\\.xml)";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(command.toString().trim());
        initFunctions();
        if (matcher1.matches()) {
            process(command.toString());
        } else {
            System.out.println("Invalid command: " + command);
        }
    }

    /**
     * Initializes the mapping of commands to their corresponding functions.
     */
    public static void initFunctions() {
        commands = new HashMap<>();
        commands.put("compress", functionsCL::compress);
        commands.put("verify", functionsCL::verify);
        commands.put("format", functionsCL::format);
        commands.put("json", functionsCL::json);
        commands.put("mini", functionsCL::mini);
        commands.put("decompress", functionsCL::decompress);
        commands.put("draw", functionsCL::draw);
        commands.put("most_active", functionsCL::most_active);
        commands.put("most_influencer", functionsCL::most_influencer);
        commands.put("mutual", functionsCL::mutual);
        commands.put("suggest", functionsCL::suggest);
        commands.put("search", functionsCL::search);
    }

    /**
     * Reads the file from the specified path.
     *
     * @param command The command containing the file path.
     * @param z       The index for extracting the file path.
     * @return The file content as a string array.
     */
    public static String[] read_file(String command, int z) {
        String input = command.split(" ")[z];
        StringBuilder temp = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(input))) {
            while (scanner.hasNextLine()) {
                temp.append(scanner.nextLine()).append("\n");
            }
        }
        catch (Exception e) {
            System.out.println("cannot read file please choose valid file");

        }
        return temp.toString().split("\n");
    }

    /**
     * Writes the content of the given array of strings to a file specified by the command.
     *
     * @param command the command string that contains the file path at the specified index
     * @param z       the index in the command string where the file path is located
     * @param text1   the array of strings to be written to the file
     */
    public static void write_file(String command, int z, String[] text1) {
        String path = command.split(" ")[z];
        String text = String.join("\n", text1);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(text);
            writer.close();
        }
        catch (Exception e) {
            System.out.println("cannot write on this  file please choose valid file");

        }
    }

    /**
     * Processes the given command by validating its name and executing the associated function
     * if it is valid. If the command is invalid, it suggests the closest valid command.
     *
     * @param command the command string to be processed
     * @throws IOException if an error occurs during command processing
     */
    public static void process(String command) throws IOException {
        String commandName = command.split(" ")[1];

        boolean isValidCommand = commands.containsKey(commandName);
        if (isValidCommand) {
            commandFunctions function = commands.get(commandName);
            function.command(command);
        } else {
            System.out.println("There is no command named " + commandName);
            String suggestion = getClosestCommand(commandName);
            if (suggestion != null) {
                System.out.println("Did you mean " + suggestion + "?");

            }
        }
    }

    /**
     * Suggests the closest valid command to the user input based on the Levenshtein distance.
     * The suggestion is only provided if the similarity between the input and the closest valid command
     * is above a certain threshold.
     *
     * @param input the input command to compare against the valid commands
     * @return the closest valid command, or null if no close match is found
     */
    private static String getClosestCommand(String input) {
        String closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (String validCommand : commands.keySet()) {
            int distance = levenshteinDistance(input, validCommand);
            if (distance < minDistance) {
                minDistance = distance;
                closest = validCommand;
            }
        }

        // Suggest only if the similarity is close enough
        return (minDistance <= 3) ? closest : null; // Threshold of 3 can be adjusted
    }

    /**
     * Calculates the Levenshtein distance (edit distance) between two strings.
     * The distance is the minimum number of insertions, deletions, or substitutions
     * required to transform one string into another.
     *
     * @param a the first string
     * @param b the second string
     * @return the Levenshtein distance between the two strings
     */
    private static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[a.length()][b.length()];
    }
}



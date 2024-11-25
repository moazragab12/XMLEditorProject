package com.xml.editor;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FunctionalInterface
interface commandFunctions {
    String[] command(String[] s);
}



public  class  commandLineV2 {
    static Map<String, commandFunctions> commands;
    static void processCommand(String[] args) {
        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            command.append(arg).append(" ");
        }

        String regex1 = "xml_editor [a-z]+ -i (\\S+\\.xml) -o (\\S+\\.xml)" +
                        "|xml_editor verify -i (\\S+\\.xml)" +
                        "|xml_editor verify -i (\\S+\\.xml) -f -o (\\S+\\.xml)";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(command.toString().trim());
        initFunctions();
        if (matcher1.matches()) {
            process(command.toString());
        } else {
            System.out.println("Invalid command: " + command);
        }
    }
    public static void initFunctions() {
        commands = new HashMap<>();
        commands.put("compress", Functions::compress);
        commands.put("verify", Functions::check);
        commands.put("format", Functions::format);
        commands.put("json", Functions::xmltoJson);
        commands.put("mini", Functions::minify);
        commands.put("decompress", Functions::decompress);
    }
    public static void process(String command) {
        String commandName = command.split(" ")[1];

        boolean isValidCommand = commands.containsKey(commandName);
        if (isValidCommand) {
            commandFunctions function =  commands.get(commandName);
            String input = command.split(" ")[3];
            String output;
            String temp="";
            String[] draft ;
            try (Scanner scanner = new Scanner(new File(input))) {
                while (scanner.hasNextLine()) {
                    temp=temp+scanner.nextLine() + "\n";
                }
            }
            catch (Exception e) {
                System.out.println("cannot read file please choose valid file");
                e.printStackTrace();
            }
            String[] inputLines =temp.split("\n");
            if(!Objects.equals(commandName, "verify")){
                draft=function.command(inputLines);
                 output = command.split(" ")[5];
            try {
                FileWriter writer = new FileWriter(output);
                writer.write(String.join("\n", draft));
                writer.close();
            } catch (Exception e) {
                System.out.println("cannot write on this  file please choose valid file");
                e.printStackTrace();
            }}
            else if(command.split(" ").length==4){
                draft=function.command(inputLines);
                System.out.println(String.join("\n", draft));
            }
            else{
                draft=function.command(inputLines);
                System.out.println(String.join("\n", draft));
                draft=Functions.repair(inputLines);
                output = command.split(" ")[6];
                try {
                    FileWriter writer = new FileWriter(output);
                    writer.write(String.join("\n", draft));
                    writer.close();
                } catch (Exception e) {
                    System.out.println("cannot write on this  file please choose valid file");
                    e.printStackTrace();
                }
            }


        } else {
            System.out.println("There is no command named " + commandName);
            String suggestion = getClosestCommand(commandName);
            if (suggestion != null) {
                System.out.println("Did you mean " + suggestion + "?");

            }
        }
    }
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



import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class XmlHandler
{
    private static int indentLevel;

    static public void fixXML(String inputFilePath, String outputFilePath) throws IOException
    {
        List<String> lines = readLines(inputFilePath);
        List<String> annotatedLines = checkError(lines);
        List<String> fixedLines = fixLines(lines);
        List<String> formattedLines = format(fixedLines);
        writeLines(outputFilePath, format(fixedLines));
    }

    static public List<String> checkError(List<String> lines) {
        List<String> annotatedLines = new ArrayList<>();
        TagContext openTags = new TagContext();
        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty())
                continue; // Skip empty lines

            List<String> tokens = splitIntoTagsAndContent(trimmedLine);
            StringBuilder annotatedLine = new StringBuilder();
            boolean bodyEnclosed = true;
            String errorMsg = "";
            String padding = "\t";

            for (String token : tokens) {
                annotatedLine.append(token);
                if (isOpenTag(token)) {
                    if (openTags.isRepeatedTag(token)) {
                        bodyEnclosed = true;
                        errorMsg = String.format("Line %d: Expected closing tag for '%s', but found another opening tag '%s'.", lineNumber, openTags.popTag(), token);
                    } else {
                        openTags.pushTag(token);
                    }
                } else if (isCloseTag(token)) {
                    if (openTags.topTagMatches(token)) {
                        bodyEnclosed = true;
                        openTags.popTag();
                    } else {
                        // Check for mismatched closing tag
                        errorMsg = String.format("Line %d: Mismatched closing tag. Expected closing for '%s', but found '%s'.", lineNumber, openTags.peekTag(), token);

                        // Handling unexpected tags or removing wrongly matched tags
                        if (openTags.isEmpty()) {
                            errorMsg = String.format("Line %d: Unexpected closing tag '%s' found, but no opening tag is available.", lineNumber, token);
                        } else if (!openTags.contain(generateOpeningTag(token))) {
                            openTags.popTag();  // Try to pop any unmatched tags to allow further parsing
                            errorMsg = String.format("Line %d: Unexpected closing tag '%s'. No matching opening tag.", lineNumber, token);
                        }
                    }
                } else {
                    // It's body content, so append it and immediately close the last open tag
                    bodyEnclosed = false;
                }
            }

            if (!bodyEnclosed && !openTags.isEmpty()) {
                errorMsg = String.format("Line %d: Missing closing tag for '%s'.", lineNumber, openTags.popTag());
            }

            if (!annotatedLine.isEmpty()) {
                if (!errorMsg.isEmpty()) {
                    annotatedLine.append(padding).append("<!-- Error: ").append(errorMsg).append(" -->");
                }
                annotatedLines.add(String.valueOf(annotatedLine));
            }
        }

        // Add missing closing tags for remaining open tags
        while (!openTags.isEmpty()) {
            String openTag = openTags.popTag();
            if (openTags.isTagRepeatedOnce(openTag))
                annotatedLines.add(String.format("<!-- Missing closing tag for opening tag '%s'. -->", openTag)); // Add each missing closing tag on a new line
        }

        return annotatedLines;
    }


    private static String errorComment(String message1, String expected, String message2, String found)
    {
        return "\t<!-- Error: " + message1 + expected + message2 + found + " -->";
    }

    public static String annotateLine(TagContext openTags, String line)
    {
        List<String> tokens = splitIntoTagsAndContent(line);
        StringBuilder annotatedLine = new StringBuilder();
        String error = "";
        for (String token : tokens)
        {
            annotatedLine.append(token);
            if (isOpenTag(token))
            {
                if (openTags.isRepeatedTag(token))
                {
                    error = errorComment("Expecting closing tag for ", openTags.popTag(), " Found ", token);
                }
                else if (openTags.isTagRepeatedOnce(token))
                {
                    error = errorComment("Nested open tag ", token, " was found ", "");
                }
                else
                {
                    openTags.pushTag(token);
                }
            }
            else if (isCloseTag(token))
            {
                if (!openTags.topTagMatches(token))
                    error = errorComment("Unmatched closing tag ", token, "", "");

                if (!openTags.isEmpty())
                    openTags.popTag();
            }
        }
        annotatedLine.append(error);
        return annotatedLine.toString();
    }


    static private List<String> readLines(String filePath) throws IOException
    {
        return Files.readAllLines(Paths.get(filePath));
    }

    static private void writeLines(String filePath, List<String> lines) throws IOException
    {
        Files.write(Path.of(filePath), lines);
    }


    private static String formatLine(Stack<String> openTags, String line)
    {
        StringBuilder formatedLine = new StringBuilder();
        List<String> tokens = splitIntoTagsAndContent(line);

        for (String token : tokens)
        {
            if (isOpenTag(token))
            {
                formatedLine.append(addIndentation(token, indentLevel));
                openTags.push(token);
                indentLevel++;
            }
            else if (isCloseTag(token) && !openTags.isEmpty())
            {
                indentLevel--;

                if (formatedLine.isEmpty()) formatedLine.append(addIndentation(token, indentLevel));
                else formatedLine.append(token);

                openTags.pop();
            }
            else
            {
                formatedLine.append(token);
            }
        }

        return formatedLine.toString();
    }


    public static List<String> format(List<String> lines)
    {
        List<String> formattedLines = new ArrayList<>();
        Stack<String> openTags = new Stack<>();
        indentLevel = 0;
        for (String line : lines)
        {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty())
                continue; // Skip empty lines

            String formattedLine = formatLine(openTags, trimmedLine);
            formattedLines.add(formattedLine);
        }
        return formattedLines;
    }

    static private List<String> fixLines(List<String> lines)
    {
        List<String> fixedLines = new ArrayList<>();
        TagContext openTags = new TagContext();
        int lineNumber = 0;

        for (String line : lines)
        {
            lineNumber++;
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty())
                continue; // Skip empty lines

            List<String> tokens = splitIntoTagsAndContent(trimmedLine);
            StringBuilder fixedLine = new StringBuilder();
            boolean bodyEnclosed = true;

            for (String token : tokens)
            {
                if (isOpenTag(token))
                {
                    if (openTags.isRepeatedTag(token))
                    {
                        openTags.popTag();
                        bodyEnclosed = true;
                        fixedLine.append(generateClosingTag(token));
                    }
                    else if (openTags.isTagRepeatedOnce(token))
                    {
                        fixedLine.append(generateClosingTag(openTags.popTag()));
                    }
                    else
                    {
                        openTags.pushTag(token);
                        fixedLine.append(token);
                    }
                }
                else if (isCloseTag(token))
                {
                    if (openTags.topTagMatches(token))
                    {
                        bodyEnclosed = true;
                        openTags.popTag();
                        fixedLine.append(token);
                    }
                    else
                    {
                        if (!openTags.isEmpty())
                        {
                            String openTag = generateOpeningTag(token);
                            if (openTags.contain(openTag))
                                continue;

                            String closeTag = generateClosingTag(openTags.popTag());
                            bodyEnclosed = true;
                            fixedLine.append(closeTag);
                            fixedLines.add(fixedLine.toString());
                            fixedLine = new StringBuilder();
                            if (openTags.topTagMatches(token))
                            {
                                openTags.popTag();
                                fixedLine.append(token);
                            }
                        }
                    }
                }
                else
                {
                    // It's body content, so append it and immediately close the last open tag
                    fixedLine.append(token);
                    bodyEnclosed = false;
                }
            }

            if (!bodyEnclosed)
            {
                if (!openTags.isEmpty())
                {
                    String closeTag = generateClosingTag(openTags.popTag());
                    fixedLine.append(closeTag);
                }
            }

            if (!fixedLine.isEmpty())
                fixedLines.add(fixedLine.toString());  // Only add non-empty lines
        }

        // Add missing closing tags for remaining open tags
        while (!openTags.isEmpty())
        {
            String closeTag = generateClosingTag(openTags.popTag());
            fixedLines.add(closeTag); // Add each missing closing tag on a new line
            System.out.println("Missing closing tag added: " + closeTag);
        }

        return fixedLines;
    }

    private static String addIndentation(String content, int indentLevel)
    {
        indentLevel = Math.max(indentLevel, 0);
        String indent = "\t";
        return indent.repeat(indentLevel) + content;
    }

    static private List<String> splitIntoTagsAndContent(String line)
    {
        List<String> tokens = new ArrayList<>();
        String tagRegex = "(<[^>]+>)";
        Pattern pattern = Pattern.compile(tagRegex);
        Matcher matcher = pattern.matcher(line);

        int lastEnd = 0;
        while (matcher.find())
        {
            if (matcher.start() > lastEnd)
                tokens.add(line.substring(lastEnd, matcher.start()).trim());

            tokens.add(matcher.group());
            lastEnd = matcher.end();
        }

        if (lastEnd < line.length())
            tokens.add(line.substring(lastEnd).trim());

        return tokens;
    }

    static private boolean isOpenTag(String token)
    {
        return token.startsWith("<") && !token.startsWith("</") && !token.endsWith("/>");
    }

    static private boolean isCloseTag(String token)
    {
        return token.startsWith("</") && token.endsWith(">");
    }


    static private String generateClosingTag(String openTag)
    {
        return "</" + openTag.substring(1);
    }

    static private String generateOpeningTag(String closeTag)
    {
        return "<" + closeTag.substring(2);
    }

}

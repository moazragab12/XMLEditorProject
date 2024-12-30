package com.xml.editor;

import java.util.*;
/**
 * A utility class for formatting XML content to improve its readability by adding
 * proper indentation based on the structure of the XML tags.
 * <p>
 * The {@code XMLFormatter} class formats the XML lines by adding indentation and
 * properly nesting the XML tags, making the XML content easier to read.
 * </p>
 * <p>Example usage:</p>
 * <pre>
 *     List<String> formattedXml = new XMLFormatter(xmlLines).format();
 * </pre>
 */
public class XMLFormatter
{

    private static int indentLevel;
    private final Stack<String> openTags;
    private final List<String> lines;  // Private data field

    /**
     * Constructs an instance of {@code XMLFormatter}.
     *
     * @param lines the list of XML lines to be formatted.
     */
    public XMLFormatter(List<String> lines)
    {
        this.lines = lines;
        this.openTags = new Stack<>();  // Initialize the stack in the constructor
    }

    /**
     * Formats the XML content to improve its readability by adding proper indentation.
     *
     * @return a list of formatted XML lines.
     */
    public List<String> format()
    {
        List<String> formattedLines = new ArrayList<>();
        indentLevel = 0;
        for (String line : lines)
        {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty())
            {
                formattedLines.add(formatLine(trimmedLine));
            }
        }
        return formattedLines;
    }

    /**
     * Formats a single line of XML by analyzing its tokens and adding appropriate indentation.
     *
     * @param line the XML line to be formatted.
     * @return the formatted XML line as a string.
     */
    private String formatLine(String line)
    {
        StringBuilder formattedLine = new StringBuilder();
        List<String> tokens = XMLTagUtils.splitTagsAndContent(line);

        for (String token : tokens)
        {
            if (XMLTagUtils.isOpenTag(token))
            {
                openTags.push(token);
                formattedLine.append(addIndentation(token, indentLevel));
                indentLevel++;
            }
            else if (XMLTagUtils.isCloseTag(token) && !openTags.isEmpty())
            {
                openTags.pop();
                indentLevel--;
                formattedLine.append( tokens.size() == 1 ? addIndentation(token, indentLevel) : token );
            }
            else
            {
                formattedLine.append( tokens.size() == 1 ? addIndentation(token, indentLevel) : token );
            }

        }
        return formattedLine.toString();
    }

    /**
     * Adds indentation to the given content based on the current indentation level.
     *
     * @param content the content to which indentation should be added.
     * @param indentLevel the current indentation level.
     * @return the indented content as a string.
     */
    private String addIndentation(String content, int indentLevel)
    {
        indentLevel = Math.max(indentLevel, 0);
        return "\t".repeat(indentLevel) + content;
    }
}

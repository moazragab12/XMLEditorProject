package com.xml.editor;

import java.util.*;

public class XMLFormatter
{

    private static int indentLevel;
    private final Stack<String> openTags;
    private final List<String> lines;  // Private data field

    // Constructor now accepts 'lines' as input
    public XMLFormatter(List<String> lines)
    {
        this.lines = lines;
        this.openTags = new Stack<>();  // Initialize the stack in the constructor
    }

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


    private String addIndentation(String content, int indentLevel)
    {
        indentLevel = Math.max(indentLevel, 0);
        return "\t".repeat(indentLevel) + content;
    }
}

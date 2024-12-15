package com.xml.editor;

import java.util.*;

public class XMLChecker
{
    private final XMLTagsTracker openTags; // private field to track open tags
    private final List<String> lines; // private field to hold the lines of XML content

    public XMLChecker(List<String> lines)
    {
        this.openTags = new XMLTagsTracker();
        this.lines = lines;
    }

    private boolean isValid()
    {
        Stack<String> stack = new Stack<>();
        for (String line : lines)
        {
            List<String> tmp = XMLTagUtils.splitTagsAndContent(line);
            for (String token : tmp)
            {
                if (XMLTagUtils.isOpenTag(token))
                    stack.push(token);

                else if (XMLTagUtils.isCloseTag(token))
                {
                    if (stack.isEmpty())
                        return false;

                    String openTag = stack.pop();
                    if (!XMLTagUtils.isMatched(openTag, token))
                        return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public List<String> check()
    {
        if (isValid())
            return new ArrayList<>();

        List<String> annotatedLines = new ArrayList<>();
        int lineNumber = 0;
        for (String line : lines)
        {
            lineNumber++;
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) continue;

            String annotatedLine = processLine(trimmedLine, lineNumber);
            if (!annotatedLine.isEmpty())
                annotatedLines.add(annotatedLine);
        }
        handleUnclosedTags(annotatedLines);
        return annotatedLines;
    }

    private String processLine(String line, int lineNumber)
    {
        List<String> tokens = XMLTagUtils.splitTagsAndContent(line);
        StringBuilder annotatedLine = new StringBuilder();
        boolean bodyEnclosed = true;
        String errorMsg = "";
        for (String token : tokens)
        {
            annotatedLine.append(token);

            if (XMLTagUtils.isOpenTag(token))
                errorMsg = handleOpenTag(lineNumber, token);

            else if (XMLTagUtils.isCloseTag(token))
                errorMsg = handleCloseTag(lineNumber, token);

            else
                bodyEnclosed = false;
        }

        if (!bodyEnclosed && !openTags.isEmpty())
            errorMsg = String.format("Line %d: Missing closing tag for '%s'.", lineNumber, openTags.popTag());

        if (!errorMsg.isEmpty())
            annotatedLine.append(" <!-- Error: ").append(errorMsg).append(" -->");

        return annotatedLine.toString();
    }

    private String handleOpenTag(int lineNumber, String token)
    {
        if (openTags.isRepeatedTag(token))
        {
            String expectedTag = openTags.popTag();
            return String.format("Line %d: Expected closing tag for '%s', but found another opening tag '%s'.", lineNumber, expectedTag, token);
        }
        else
        {
            openTags.pushTag(token);
        }
        return "";
    }

    private String handleCloseTag(int lineNumber, String token)
    {
        if (openTags.isTopTagMatches(token))
        {
            openTags.popTag();
        }
        else
        {
            if (!openTags.isEmpty())
                return String.format("Line %d: Mismatched closing tag. Expected closing for '%s', but found '%s'.", lineNumber, openTags.peekTag(), token);
            else
            {
                return String.format("Line %d: Mismatched closing tag. No opening tag found for '%s'.", lineNumber, token);
            }
        }
        return "";
    }

    private void handleUnclosedTags(List<String> annotatedLines)
    {
        while (!openTags.isEmpty())
        {
            String unclosedTag = openTags.popTag();
            annotatedLines.add(String.format("<!-- Missing closing tag for opening tag '%s'. -->", unclosedTag));
        }
    }
}
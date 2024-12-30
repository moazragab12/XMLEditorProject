package com.xml.editor;

import java.util.*;
/**
 * The {@code XMLChecker} class is used to validate XML content by checking if all tags are properly nested
 * and matched. It provides functionality to process XML lines, detect errors in tag structure, and generate
 * annotated lines highlighting any issues.
 */
public class XMLChecker
{
    private final XMLTagsTracker openTags; // Private field to track open tags
    private final List<String> lines; // Private field to hold the lines of XML content

    /**
     * Constructs an instance of {@code XMLChecker}.
     *
     * @param lines the list of XML lines to be checked for validity.
     */
    public XMLChecker(List<String> lines)
    {
        this.openTags = new XMLTagsTracker();
        this.lines = lines;
    }

    /**
     * Checks if the XML content is valid by verifying proper nesting and matching
     * of opening and closing tags.
     *
     * @return {@code true} if the XML content is valid, otherwise {@code false}.
     */
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

    /**
     * Checks the XML content for errors and provides a list of annotated lines
     * highlighting the errors.
     *
     * @return a list of annotated lines with error messages, or an empty list if the XML is valid.
     */
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

    /**
     * Processes a single line of XML, checking for errors and generating an annotated line
     * with error messages if necessary.
     *
     * @param line       the XML line to be processed.
     * @param lineNumber the line number of the current XML line.
     * @return the annotated line with error messages, or an empty string if no errors are found.
     */
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

    /**
     * Handles the logic for processing an open tag, checking for repeated or unclosed tags.
     *
     * @param lineNumber the line number where the open tag is found.
     * @param token      the token representing the open tag.
     * @return an error message if a problem is detected, or an empty string if the tag is valid.
     */
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

    /**
     * Handles the logic for processing a close tag, checking for mismatches or missing opening tags.
     *
     * @param lineNumber the line number where the close tag is found.
     * @param token      the token representing the close tag.
     * @return an error message if a problem is detected, or an empty string if the tag is valid.
     */
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

    /**
     * Handles unclosed tags at the end of the XML content, adding error messages for each.
     *
     * @param annotatedLines the list of annotated lines to which the errors for unclosed tags are added.
     */
    private void handleUnclosedTags(List<String> annotatedLines)
    {
        while (!openTags.isEmpty())
        {
            String unclosedTag = openTags.popTag();
            annotatedLines.add(String.format("<!-- Missing closing tag for opening tag '%s'. -->", unclosedTag));
        }
    }
}

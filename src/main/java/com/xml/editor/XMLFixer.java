package com.xml.editor;

import java.util.*;
/**
 * A utility class for fixing XML content by ensuring that all tags are properly opened and closed.
 * <p>
 * The {@code XMLFixer} class scans through the provided XML content, identifies mismatched
 * or missing tags, and ensures the XML structure is well-formed by adding any necessary
 * opening or closing tags.
 * </p>
 * <p>Example usage:</p>
 * <pre>
 *     List<String> fixedXml = new XMLFixer(xmlLines).fix();
 * </pre>
 */
public class XMLFixer
{
    private final List<String> lines;
    private final XMLTagsTracker openTags;
    private final Stack<String> bodyStack;
    private final List<String> fixedLines;

    /**
     * Constructs an instance of {@code XMLFixer}.
     *
     * @param lines the list of XML lines that need to be fixed.
     */
    public XMLFixer(List<String> lines)
    {
        this.lines = lines;
        this.openTags = new XMLTagsTracker();
        this.bodyStack = new Stack<>();
        fixedLines = new ArrayList<>();
    }

    /**
     * Fixes the provided XML content by ensuring all tags are properly opened and closed.
     *
     * <p>This method iterates through the XML lines, checks for mismatched or missing tags,
     * and ensures that the XML structure is well-formed by adding the necessary opening or
     * closing tags where appropriate.</p>
     *
     * @return a list of fixed XML lines.
     */
    public List<String> fix()
    {
        for (String line : lines)
        {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty())
                continue; // Skip empty lines

            List<String> tokens = XMLTagUtils.splitTagsAndContent(trimmedLine);
            StringBuilder fixedLine = new StringBuilder();
            boolean bodyEnclosed = true;

            for (String token : tokens)
            {
                if (XMLTagUtils.isOpenTag(token))
                {
                    if (openTags.isRepeatedTag(token))
                    {
                        openTags.popTag();
                        bodyEnclosed = true;
                        fixedLine.append(XMLTagUtils.createClosingTag(token));
                    }
                    else if (openTags.isTagRepeatedOnce(token))
                    {
                        fixedLine.append(XMLTagUtils.createClosingTag(openTags.popTag()));
                    }
                    else
                    {
                        openTags.pushTag(token);
                        fixedLine.append(token);
                    }
                }
                else if (XMLTagUtils.isCloseTag(token))
                {
                    if (openTags.isTopTagMatches(token))
                    {
                        bodyEnclosed = true;
                        openTags.popTag();
                        fixedLine.append(token);
                    }
                    else
                    {
                        if (!openTags.isEmpty())
                        {
                            String openTag = XMLTagUtils.createOpeningTag(token);
                            if (openTags.isTagPresent(openTag))
                                continue;

                            String closeTag = XMLTagUtils.createClosingTag(openTags.popTag());
                            bodyEnclosed = true;
                            fixedLine.append(closeTag);
                            fixedLines.add(fixedLine.toString());
                            fixedLine = new StringBuilder();
                            if (openTags.isTopTagMatches(token))
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
                    String closeTag = XMLTagUtils.createClosingTag(openTags.popTag());
                    fixedLine.append(closeTag);
                }
            }

            if (!fixedLine.isEmpty())
                fixedLines.add(fixedLine.toString());  // Only add non-empty lines
        }

        // Add missing closing tags for remaining open tags
        while (!openTags.isEmpty())
        {
            String closeTag = XMLTagUtils.createClosingTag(openTags.popTag());
            fixedLines.add(closeTag); // Add each missing closing tag on a new line
            System.out.println("Missing closing tag added: " + closeTag);
        }

        return fixedLines;
    }


}

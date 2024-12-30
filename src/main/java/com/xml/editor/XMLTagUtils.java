package com.xml.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Utility class providing methods for manipulating and analyzing XML tags.
 * <p>
 * The {@code XMLTagUtils} class contains static methods to split XML tags and content,
 * check if XML tags are matched, and create corresponding opening and closing tags.
 * These utility methods are useful for processing and validating XML structures.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     List<String> tokens = XMLTagUtils.splitTagsAndContent("&lt;tag&gt;Content&lt;/tag&gt;");
 *     boolean isMatched = XMLTagUtils.isMatched("&lt;tag&gt;", "&lt;/tag&gt;");
 *     String closingTag = XMLTagUtils.createClosingTag("&lt;tag&gt;");
 * </pre>
 */
public class XMLTagUtils
{
    private XMLTagUtils() {}

    private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]+>");

    /**
     * Splits a line of text into a list of tokens, separating XML tags and content.
     *
     * @param line the line of text to be split.
     * @return a list of tokens containing XML tags and content.
     */
    static public List<String> splitTagsAndContent(String line)
    {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = TAG_PATTERN.matcher(line);

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

    /**
     * Checks if a given closing tag matches the corresponding opening tag.
     *
     * @param openTag the opening XML tag.
     * @param closeTag the closing XML tag.
     * @return true if the closing tag matches the opening tag, false otherwise.
     */
    static public boolean isMatched(String openTag, String closeTag)
    {
        return closeTag.equals("</" + openTag.substring(1));
    }

    /**
     * Determines if a given token is an opening XML tag.
     *
     * @param token the token to be checked.
     * @return true if the token is an opening XML tag, false otherwise.
     */
    static public boolean isOpenTag(String token)
    {
        return token.startsWith("<") && !token.startsWith("</") && !token.endsWith("/>");
    }

    /**
     * Determines if a given token is a closing XML tag.
     *
     * @param token the token to be checked.
     * @return true if the token is a closing XML tag, false otherwise.
     */
    static public boolean isCloseTag(String token)
    {
        return token.startsWith("</") && token.endsWith(">");
    }

    /**
     * Creates a corresponding closing tag for a given opening tag.
     *
     * @param openTag the opening XML tag.
     * @return the corresponding closing XML tag.
     * @throws IllegalArgumentException if the opening tag is null or empty.
     */
    static public String createClosingTag(String openTag)
    {
        if (openTag == null || openTag.isEmpty())
        {
            throw new IllegalArgumentException("Open tag cannot be null or empty.");
        }
        return "</" + openTag.substring(1);
    }

    /**
     * Creates a corresponding opening tag for a given closing tag.
     *
     * @param closeTag the closing XML tag.
     * @return the corresponding opening XML tag.
     * @throws IllegalArgumentException if the closing tag is null or empty.
     */
    static public String createOpeningTag(String closeTag)
    {
        if (closeTag == null || closeTag.isEmpty())
        {
            throw new IllegalArgumentException("Close tag cannot be null or empty.");
        }
        return "<" + closeTag.substring(2);
    }
}

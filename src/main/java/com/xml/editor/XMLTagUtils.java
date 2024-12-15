package com.xml.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLTagUtils
{
    private XMLTagUtils() {}

    private static final Pattern TAG_PATTERN = Pattern.compile("<[^>]+>");

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

    static public boolean isMatched(String openTag, String closeTag)
    {
        return closeTag.equals("</" + openTag.substring(1));
    }

    static public boolean isOpenTag(String token)
    {
        return token.startsWith("<") && !token.startsWith("</") && !token.endsWith("/>");
    }

    static public boolean isCloseTag(String token)
    {
        return token.startsWith("</") && token.endsWith(">");
    }

    static public String createClosingTag(String openTag)
    {
        if (openTag == null || openTag.isEmpty())
        {
            throw new IllegalArgumentException("Open tag cannot be null or empty.");
        }
        return "</" + openTag.substring(1);
    }

    static public String createOpeningTag(String closeTag)
    {
        if (closeTag == null || closeTag.isEmpty())
        {
            throw new IllegalArgumentException("Close tag cannot be null or empty.");
        }
        return "<" + closeTag.substring(2);
    }
}

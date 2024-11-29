package com.xml.editor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tagcontext
{
    private final Stack<String> openTagsStack = new Stack<>();
    private final TreeMap<String, Integer> freq = new TreeMap<>();

    void pushTag(String tag)
    {
        openTagsStack.push(tag);
        freq.put(tag, freq.getOrDefault(tag, 0) + 1);
    }

    String popTag()
    {
        String tag = openTagsStack.pop();
        freq.put(tag, freq.getOrDefault(tag, 0) - 1);
        return tag;
    }

    String peekTag()
    {
        return openTagsStack.peek();
    }

    boolean isEmpty()
    {
        return openTagsStack.isEmpty();
    }

    boolean isRepeatedTag(String tag)
    {
        return !isEmpty() && tag.equals(peekTag());
    }

    boolean isTagRepeatedOnce(String tag)
    {
        return freq.getOrDefault(tag, 0) == 1;
    }

    boolean topTagMatches(String closeTag)
    {
        return !isEmpty() && closeTag.equals("</" + peekTag().substring(1));
    }

    boolean contain(String openTag)
    {
        return freq.containsKey(openTag);
    }
}
package com.xml.editor;

import java.util.*;
/**
 * The {@code XMLTagsTracker} class is designed to manage and track the occurrence of XML tags.
 * It uses a stack to keep track of opened tags and a map to record the frequency of each tag.
 * The class provides methods for pushing and popping tags, checking the status of the stack,
 * and determining whether tags are repeated or match specific conditions.
 * <p>
 * This class is useful for validating and managing XML structures, ensuring proper tag matching
 * and detecting repeated or unmatched tags during XML parsing or processing.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     XMLTagsTracker tracker = new XMLTagsTracker();
 *     tracker.pushTag("&lt;tag1&gt;");
 *     tracker.pushTag("&lt;tag2&gt;");
 *     String topTag = tracker.peekTag(); // Returns "&lt;tag2&gt;"
 *     tracker.popTag(); // Pops "&lt;tag2&gt;"
 *     boolean isRepeated = tracker.isRepeatedTag("&lt;tag1&gt;"); // Returns true
 * </pre>
 */
public class XMLTagsTracker {
    private final Stack<String> openTagsStack = new Stack<>();
    private final Map<String, Integer> tagFrequency = new TreeMap<>();

    /**
     * Pushes an opening XML tag onto the stack and updates its frequency count.
     *
     * @param tag the XML tag to push onto the stack.
     */
    public void pushTag(String tag) {
        openTagsStack.push(tag);
        tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
    }

    /**
     * Pops the top tag from the stack and decrements its frequency count.
     *
     * @return the popped XML tag, or an empty string if the stack is empty.
     */
    public String popTag() {
        if (openTagsStack.empty())
            return "";

        String tag = openTagsStack.pop();
        tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) - 1);
        return tag;
    }

    /**
     * Retrieves the top tag from the stack without removing it.
     *
     * @return the top XML tag, or an empty string if the stack is empty.
     */
    public String peekTag() {
        return !openTagsStack.empty() ? openTagsStack.peek() : "";
    }

    /**
     * Checks if the stack of XML tags is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return openTagsStack.isEmpty();
    }

    /**
     * Checks if the given tag matches the top tag on the stack.
     *
     * @param tag the XML tag to compare with the top tag.
     * @return true if the given tag is the same as the top tag, false otherwise.
     */
    public boolean isRepeatedTag(String tag) {
        return !isEmpty() && tag.equals(peekTag());
    }

    /**
     * Checks if the given tag has been pushed onto the stack exactly once.
     *
     * @param tag the XML tag to check.
     * @return true if the tag appears exactly once, false otherwise.
     */
    public boolean isTagRepeatedOnce(String tag) {
        return tagFrequency.getOrDefault(tag, 0) == 1;
    }

    /**
     * Checks if the given closing tag matches the top opening tag on the stack.
     *
     * @param closeTag the closing XML tag to compare.
     * @return true if the closing tag matches the top opening tag, false otherwise.
     */
    public boolean isTopTagMatches(String closeTag) {
        return !isEmpty() && closeTag.equals("</" + peekTag().substring(1));
    }

    /**
     * Checks if a specific opening tag is present in the stack.
     *
     * @param openTag the opening XML tag to check for.
     * @return true if the tag is present, false otherwise.
     */
    public boolean isTagPresent(String openTag) {
        return tagFrequency.getOrDefault(openTag, 0) > 0;
    }
}

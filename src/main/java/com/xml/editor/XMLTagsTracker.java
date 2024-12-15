import java.util.*;

public class XMLTagsTracker {
    private final Stack<String> openTagsStack = new Stack<>();
    private final Map<String, Integer> tagFrequency = new TreeMap<>();

    public void pushTag(String tag) {
        openTagsStack.push(tag);
        tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
    }

    public String popTag() {
        if (openTagsStack.empty())
            return "";

        String tag = openTagsStack.pop();
        tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) - 1);
        return tag;
    }

    public String peekTag() {
        return !openTagsStack.empty() ? openTagsStack.peek() : "";
    }

    public boolean isEmpty() {
        return openTagsStack.isEmpty();
    }

    public boolean isRepeatedTag(String tag) {
        return !isEmpty() && tag.equals(peekTag());
    }

    public boolean isTagRepeatedOnce(String tag) {
        return tagFrequency.getOrDefault(tag, 0) == 1;
    }

    public boolean isTopTagMatches(String closeTag) {
        return !isEmpty() && closeTag.equals("</" + peekTag().substring(1));
    }

    public boolean isTagPresent(String openTag) {
        return tagFrequency.getOrDefault(openTag, 0) > 0;
    }
}

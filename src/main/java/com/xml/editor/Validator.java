package com.xml.editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class Validator {

    TreeMap<Integer, Character> Errors_Detectors = new TreeMap<>();
    HashMap<String, Integer> FreqOfStack = new HashMap<>();
    private final Stack<Pair<String, Integer>> openTagsStack = new Stack<>();
    private int errorCount = 0;
    String filePath;

    public XMLValidator(String filePath) {
        this.filePath = filePath;
    }

    public void validate() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;

            String[] tokens = splitTagContent(line.trim());
            for (String token : tokens)
                processToken(token, lineNumber);
        }
    }


    private void processToken(String token, int lineNumber) {
        if (isOpenTag(token)) handleOpeningTag(token, lineNumber);

        else if (isCloseTag(token)) handleClosingTag(token, lineNumber);
    }

    private void handleOpeningTag(String tag, int lineNumber) {
        if (!openTagsStack.isEmpty() && tag.equals(openTagsStack.peek().key)) {

            logError(lineNumber, "Consecutive opening tags detected. The second " + openTagsStack.peek() + " tag should be a closing tag.");
            Errors_Detectors.put(lineNumber, '/');
            openTagsStack.pop();

            if (FreqOfStack.containsKey(tag)) {
                FreqOfStack.put(tag, FreqOfStack.get(tag) - 1);
                if (FreqOfStack.get(tag) < 0)
                    FreqOfStack.put(tag, 0);
            } else {
                FreqOfStack.put(tag, 0);
            }
        } else {
            Pair<String, Integer> editor = new Pair<>(tag, lineNumber);
            openTagsStack.push(editor);
            if (FreqOfStack.containsKey(tag)) FreqOfStack.put(tag, FreqOfStack.get(tag) + 1);
            else {
                FreqOfStack.put(tag, 1);
            }


        }
    }

    private void handleClosingTag(String tag, int lineNumber) {
        if (openTagsStack.isEmpty()) {
            logError(lineNumber, "Closing tag " + tag + " appears without a matching opening tag.");
            Errors_Detectors.put(lineNumber, 'D');
        } else {
            Pair<String, Integer> openTag = openTagsStack.peek();
            String tmp = "<" + tag.substring(2);

            if (!compareTags(openTag.key, tag)) {
                logError(lineNumber, "Mismatched tags. Open tag " + openTag + " does not match closing tag " + tag + ".");

                if (FreqOfStack.get(tmp) > 0)//error
                {
                    while (!tmp.equals(openTagsStack.peek().key)) {
                        openTag = openTagsStack.peek();
                        Errors_Detectors.put(openTagsStack.peek().value, 'D');
                        openTagsStack.pop();
                        FreqOfStack.put(openTag.key, FreqOfStack.get(openTag.key) - 1);
                        if (FreqOfStack.get(openTag.key) < 0)
                            FreqOfStack.put(openTag.key, 0);
                    }
                    if (tmp.equals(openTagsStack.peek().key)) {
                        openTagsStack.pop();
                        FreqOfStack.put(tmp, FreqOfStack.get(tmp) - 1);
                        if (FreqOfStack.get(tmp) < 0)
                            FreqOfStack.put(tmp, 0);
                    }
                } else {
                    Errors_Detectors.put(lineNumber, 'D');
                }

            } else {

                openTagsStack.pop();

                if (FreqOfStack.containsKey(tag)) {
                    FreqOfStack.put(tag, FreqOfStack.get(tag) - 1);
                    if (FreqOfStack.get(tag) < 0)
                        FreqOfStack.put(tag, 0);
                } else {
                    FreqOfStack.put(tag, 0);
                }
            }


        }
    }

}

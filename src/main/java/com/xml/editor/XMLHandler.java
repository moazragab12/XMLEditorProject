package com.xml.editor;

import java.util.*;

public class XMLHandler
{

    /**
     * Fixes a list of XML lines by resolving inconsistencies or errors in the tags.
     *
     * @param lines the list of XML lines to be fixed.
     * @return a list of XML lines with errors corrected.
     */
    public static List<String> fix(List<String> lines)
    {
        return new XMLFixer(lines).fix();
    }

    /**
     * Checks a list of XML lines for correctness and identifies any issues.
     *
     * @param lines the list of XML lines to be checked.
     * @return a list of messages or results from the check process.
     */
    public static List<String> check(List<String> lines)
    {
        return new XMLChecker(lines).check();
    }

    /**
     * Formats a list of XML lines to improve readability and adhere to a consistent structure.
     *
     * @param lines the list of XML lines to be formatted.
     * @return a list of XML lines with improved formatting.
     */
    public static List<String> format(List<String> lines)
    {
        return new XMLFormatter(lines).format();
    }
}

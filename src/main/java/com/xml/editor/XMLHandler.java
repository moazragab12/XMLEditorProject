package com.xml.editor;

import java.util.*;
/**
 * The {@code XMLHandler} class provides utility methods for handling XML content in the form of a list of XML lines.
 * It includes methods for fixing, checking, and formatting XML lines to ensure consistency, correctness, and readability.
 * <p>
 * This class serves as a central point for XML operations such as error fixing, validation, and formatting of XML content.
 * Each method performs a specific function:
 * <ul>
 *     <li>{@link #fix(List)}: Resolves errors in the XML tags.</li>
 *     <li>{@link #check(List)}: Checks the XML lines for correctness and identifies potential issues.</li>
 *     <li>{@link #format(List)}: Formats the XML lines to improve readability and structure.</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     List<String> xmlLines = Arrays.asList("&lt;tag&gt;content&lt;/tag&gt;", "&lt;tag&gt;more content&lt;/tag&gt;");
 *     List<String> fixedLines = XMLHandler.fix(xmlLines);
 *     List<String> checkedLines = XMLHandler.check(xmlLines);
 *     List<String> formattedLines = XMLHandler.format(xmlLines);
 * </pre>
 *
 * @see XMLFixer
 * @see XMLChecker
 * @see XMLFormatter
 */
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

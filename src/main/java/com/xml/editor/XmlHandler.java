import java.util.*;

public class XMLHandler
{


    public static List<String> fix(List<String> lines)
    {
        return new XMLFixer(lines).fix();
    }

    public static List<String> check(List<String> lines)
    {
        return new XMLChecker(lines).check();
    }

    public static List<String> format(List<String> lines)
    {
        return new XMLFormatter(lines).format();
    }
}

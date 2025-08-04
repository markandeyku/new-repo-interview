package java17;

public class Formatter {

    public static void main(String[] args) {
        formatter(10);
        double num = 10;

        System.out.println(String.format("double %.2f",num));
    }

    static String formatter(Object o) {
        String formatted = "unknown";
        if (o instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (o instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (o instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (o instanceof String s) {
            formatted = String.format("String %s", s);
        }

        System.out.println(o + " formatted as " + formatted);
        return formatted;
    }


    static void testStringJava17(String s) {
        switch (s) {
            case ""                   -> System.out.println("Unknown!");
            case "Java 11", "Java 17"   -> System.out.println("LTS");
            default                     -> System.out.println("Ok");
        }


    }
/**
 *
 * Summary: Why Upgrade to Java 17?
 * ✅ LTS Release (Recommended for production).
 * ✅ Improved performance, security, and maintainability.
 * ✅ Modern language features (Pattern Matching, Sealed Classes).
 * ✅ Better macOS & memory management support.
 *
 *
 */



}

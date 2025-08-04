package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePhoneNo {
    public static void main(String[] args) {
        String phoneNo = "918478748358";

        Pattern pattern = Pattern.compile("[0-9]{10}+");

        Matcher matcher = pattern.matcher(phoneNo);

        System.out.println(matcher.find());

        //These are character class
        //[abc] -->  Either a or b or c
        //[^abc] --> Any character except a or b or c
        ///[a-z] --> any lower case alphabet
        //[a-zA-Z]  --> lower or upper alphabet
        //[0-9]   --> any digit from 0 to  9
        //[a-d[m-p]]  --> [a-dm-p]  --> Union of a to d and p to m
        //[a-z&&[def]]  --> Intersection of d or e or f
        //[a-z&&[^bc]]  --> a through z except b or c



        //Regex qualifiers
        //x?  --? x occurs only once or not at all
        // x+   --> x occurs once or more
        //x*   --> x occurs 0 or more times
        //x{n}   --> x occurs  n times only
        // x{n, }  --> x occurs n or more times
        //x{y,z}  ---> x occurs at least y times and at most z times
        //


        //let's take some examples

        System.out.println("[abc]?, a   "+Pattern.matches("[abc]?", "aa")); // a occurs more than once, but logic is only 1 or not at all
        System.out.println("[abc]?, a   "+Pattern.matches("[abc]?", "")); // not at all, true
        System.out.println("[abc]?, a   "+Pattern.matches("[abc]?", "fg")); //false
        System.out.println("[abc]?, a   "+Pattern.matches("[abc]?", "abc")); //false




        System.out.println("[abc], a   "+Pattern.matches("[abc]", "a"));
        System.out.println("[abc]+, a   "+Pattern.matches("[abc]+", "a"));
        System.out.println("[abc]+, a   "+Pattern.matches("[abc]+", "abcaabc")); // true
        System.out.println("[abc]+, a   "+Pattern.matches("[abc]+", "abdc")); // false
        System.out.println("[abc]+, a blank   "+Pattern.matches("[abc]+", "")); // false , at least once times needed




        System.out.println("[abc]*, a   "+Pattern.matches("[abc]*", "a"));
        System.out.println("[abc]*, a   "+Pattern.matches("[abc]*", "")); // 0 times
        System.out.println("[abc]*, a   "+Pattern.matches("[abc]*", "abcabaabac")); // more times




        System.out.println("[abc]{2}, a   "+Pattern.matches("[abc]{2}", "ab")); // only two length  true
        System.out.println("[abc]{2}, a  not satisfield   "+Pattern.matches("[abc]{2}", "abd")); // 2 length not satisfied




    }
}

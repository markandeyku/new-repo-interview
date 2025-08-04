package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMetadataCharcters {

    public static void main(String[] args) {

        // .   --> Any character may or may not
        // \d   ---> Any digit [0-9]
        // \D   =----> Any non digit [^0-9]

        //  \s   -->  any white space character
        //  \S   ----> Any non-white space character
        // \w  ---> Any word character [a-zA-Z-0-9]
        // \W   ---> Any non word character
        //  \b ---> any word boundry


        Pattern pattern = Pattern.compile("[\s]");

        Matcher matcher = pattern.matcher("hello markandey .... x9493 ab    kumar");

        String[] A ="hello markandey .... x9493 ab    kumar".split("[\\s]*");  // This will split with each characters
        String[] B ="hello markandey .... x9493 ab    kumar".split("[\\s]+");  // This will print only words except white space
        System.out.println("A  --> ");
        for (var x : A){
            System.out.println(x);
        }
        System.out.println("B  --> ");
        for (var x : B){
            System.out.println(x);
        }

        // now I do not want split with space, instead I want to split each alphabet char

        String[] C ="hello markandey .... x9493 ab    kumar".split("[^a-zA-Z0-9]+");  // This will split with each characters


        System.out.println("C  --> ");
        for (var x : C){
            System.out.println(x);
        }
    }
}

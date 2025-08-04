package regex;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SplittingWithSpecialCharacters {

    public static void main(String[] args) {
        String str = "hello...markandey..kumar..rammohan@@hfsj  ,,jkshdjf,hellosd,fjskd,,..sdfhjsdf";

        String[] parts = str.split("[. ,@]+");
        int r = parts.length-1;
        int l = 0;
        while(l <=r){
            String temp = parts[l];
            parts[l] = parts[r];
            parts[r] = temp;
            l++;
            r--;
        }

       String reverse = String.join(".", parts);

        System.out.println(reverse);
        for (String curr : parts){
            System.out.println(curr);
        }
    }
}

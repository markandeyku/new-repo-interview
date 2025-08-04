package test.designpattern.practice.singlton;

public class SingltonDesignPattern {

    public static void main(String[] args) {

        ReadTweetService obj1 = ReadTweetService.getReadTweetServiceInstance();
        ReadTweetService obj2 = ReadTweetService.getReadTweetServiceInstance();
        System.out.println("Checking obejcts are same or not " + (obj1 == obj2));
    }
}

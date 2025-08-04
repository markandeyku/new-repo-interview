package test.designpattern.practice.singlton;

public class ReadTweetService {

    private static ReadTweetService instance;

    public static ReadTweetService getReadTweetServiceInstance(){
        if(instance == null) {
            instance = new ReadTweetService();
        }
        return instance;
    }
}

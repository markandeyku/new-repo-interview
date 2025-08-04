package dsignpattern.behavioral;

import java.util.ArrayList;
import java.util.List;

public class ObserverDesignPattern {
    public static void main(String[] args) {

        /**
         * Example 1: Observer Pattern (Real-World Example - Stock Price Alert System)
         *
         *
         */

        // first create channel
        // then add some subscribers
        // then upload vides
        // if not any subscriber  then there will not be send any notification

        Channel channel = new Channel("Markandey kumar channel", "789374876826785687");

        channel.subscribe(new Subscriber("markandey","markandey.kuamr@gmail.com"));
        channel.subscribe(new Subscriber("sidharth","sidh.kuamr@gmail.com"));

        channel.uploadVideo("System design in the simplified version", "videos content");
    }

    /**
     * Observer
     * Observable
     *
     *
     * let's say we have subscribers  and channel
     *
     * used on Youtube
     *
     */


}


class Channel{

    // a channel can have multiple subscribers

    private String name;

    private String id;

    public Channel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public boolean uploadVideo(String title, String content){
        //implement logic to upload
        // now sent notification to all subscribers that video is uploaded
        System.out.println("vodeo uploaded successfully....");
        for (var sub : subscribers){
            sendNotification(title, sub);
        }
        return true;
    }


    public void sendNotification(String title, Subscriber subscriber) {
        //implement logic to send notification as push mechenaism utilising third party or custom
        System.out.println("Notification sent to name : "+ subscriber.getName() +", emailId " +subscriber.getEmailId() + "  with title "+ title);
    }
}

class Subscriber {

    private String name;
    private String emailId;

    public Subscriber(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }


    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }
}


// Another example

interface Stock {
    void register(Investor investor);
    void unregister(Investor investor);
    void notifyInvestors();
}

// Concrete Subject (StockPrice)
class StockPrice implements Stock {
    private List<Investor> investors = new ArrayList<>();
    private double price;

    public void setPrice(double price) {
        this.price = price;
        notifyInvestors(); // Notify observers when price changes
    }

    @Override
    public void register(Investor investor) {
        investors.add(investor);
    }

    @Override
    public void unregister(Investor investor) {
        investors.remove(investor);
    }

    @Override
    public void notifyInvestors() {
        for (Investor investor : investors) {
            investor.update(price);
        }
    }
}

// Observer Interface
interface Investor {
    void update(double price);
}

// Concrete Observer
class InvestorA implements Investor {
    @Override
    public void update(double price) {
        System.out.println("Investor A notified: Stock price is now $" + price);
    }
}

class InvestorB implements Investor {
    @Override
    public void update(double price) {
        System.out.println("Investor B notified: Stock price is now $" + price);
    }
}

// Client Code
 class ObserverPatternDemo {
    public static void main(String[] args) {
        StockPrice stock = new StockPrice();

        Investor investorA = new InvestorA();
        Investor investorB = new InvestorB();

        stock.register(investorA);
        stock.register(investorB);

        stock.setPrice(105.50);  // Both investors will be notified
        stock.setPrice(110.75);  // Both investors will be notified again
    }
}

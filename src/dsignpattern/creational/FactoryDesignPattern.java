package dsignpattern.creational;

import java.util.List;

public class FactoryDesignPattern {

    // let's take an example of email client
    // we can take example of different payment gateway according to bank

}


interface EmailClient {
     List<String> fetchmessageIds(String emailid, String token);
}

class GmailClient implements EmailClient{

    @Override
    public List<String> fetchmessageIds(String emailid, String token) {
        return List.of();
    }
}

class OutlookClient implements EmailClient{

    @Override
    public List<String> fetchmessageIds(String emailid, String token) {
        return List.of();
    }
}

class AppleClient implements EmailClient{

    @Override
    public List<String> fetchmessageIds(String emailid, String token) {
        return List.of();
    }
}

class EmailFactory{

    public static EmailClient getClient(String clientName){
        return switch (clientName){
            case "GMAIL" -> new GmailClient();
            case "OUTLOOK" -> new OutlookClient();
            case "APPLE" -> new AppleClient();
            default -> throw new IllegalStateException("Unexpected value: " + clientName);
        };
    }
}


class EmailMessageService{

    public static void main(String[] args) {
        EmailClient emailClient = EmailFactory.getClient("GMAIL");

        List<String> messages = emailClient.fetchmessageIds("abc@gmail.com","josjdifod");

    }
}
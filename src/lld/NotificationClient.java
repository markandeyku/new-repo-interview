package lld;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.*;
import java.util.concurrent.*;


// functional Requirements

/**
 * 1. We should be able to send notification
 * 2. Notification can be sent of different types
 * 3. Email,Message, Other type of messages
 * 4. push or pull notification --> push notification
 */

//Non functional requirements
/**
 * 1. Email notification should be able to sent async like using kafka queue
 * 2. Gaurntees only one time message or notification delivery
 * 3. Syncing notification to all logged in devices
 * 4.
 */

//Let's discuss about the flow of notification message

/**
 * User1 --> Type message --> Decide the notificattion type like warning,alert or highly imprtant or just
 * a normal message --> After prepared body and seleting type of message and its template ---> click on send ---> message will be sent to
 * queue/kafka(which will work asynchrnously  like in seperate threads ) --> Receiver (subscribed that queue) --> read the message and sent to particular device
 * using third party push notification
 */

//observer design pattern will be used in this





// ------------------ Domain Models ------------------

class User1 {
    private final String userId;
    private final String userNo;
    private final String userName;
    private final String phoneNo;
    private final String emailId;

    // Private constructor
    public User1(Builder builder) {
        this.userId = builder.userId;
        this.userNo = builder.userNo;
        this.userName = builder.userName;
        this.phoneNo = builder.phoneNo;
        this.emailId = builder.emailId;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUserNo() { return userNo; }
    public String getUserName() { return userName; }
    public String getPhoneNo() { return phoneNo; }
    public String getEmailId() { return emailId; }

    // ✅ Builder Class
    public static class Builder {
        private String userId;
        private String userNo;
        private String userName;
        private String phoneNo;
        private String emailId;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userNo(String userNo) {
            this.userNo = userNo;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder phoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        public Builder emailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public User1 build() {
            return new User1(this);
        }
    }
}

enum MessageType {
    ALERT, EMERGENCY, NORMAL
}

class Message {
    private final String messageId;
    private final String content;
    private final MessageType messageType;

    public Message(String messageId, String content, MessageType messageType) {
        this.messageId = messageId;
        this.content = content;
        this.messageType = messageType;
    }

    public String getContent() { return content; }
    public MessageType getMessageType() { return messageType; }
}

class Sender extends User1 {
    public Sender(Builder builder) { super(builder); }
}

class Receiver extends User1 {
    public Receiver(Builder builder) { super(builder); }
}

enum ChannelType {
    EMAIL, MOBILE, PUSH
}

class Notification {
    private final Message message;
    private final Sender sender;
    private final List<Receiver> receivers;
    private final ChannelType channelType;

    public Notification(Message message, Sender sender, List<Receiver> receivers, ChannelType channelType) {
        this.message = message;
        this.sender = sender;
        this.receivers = receivers;
        this.channelType = channelType;
    }

    public Message getMessage() { return message; }
    public Sender getSender() { return sender; }
    public List<Receiver> getReceivers() { return receivers; }
    public ChannelType getChannelType() { return channelType; }
}

// ------------------ Services ------------------

interface NotificationService {
    void send(Notification notification);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void send(Notification notification) {
        for (Receiver r : notification.getReceivers()) {
            System.out.println("📧 Sending EMAIL to " + r.getEmailId() +
                    " | Msg: " + notification.getMessage().getContent());
        }
    }
}

class MobileNotificationService implements NotificationService {
    @Override
    public void send(Notification notification) {
        for (Receiver r : notification.getReceivers()) {
            System.out.println("📱 Sending SMS to " + r.getPhoneNo() +
                    " | Msg: " + notification.getMessage().getContent());
        }
    }
}

class PushNotificationService implements NotificationService {
    @Override
    public void send(Notification notification) {
        for (Receiver r : notification.getReceivers()) {
            System.out.println("🔔 Sending PUSH to " + r.getUserName() +
                    " | Msg: " + notification.getMessage().getContent());
        }
    }
}

// ------------------ Factory ------------------

class NotificationServiceFactory {
    public static NotificationService getService(ChannelType type) {
        switch (type) {
            case EMAIL: return new EmailNotificationService();
            case MOBILE: return new MobileNotificationService();
            case PUSH: return new PushNotificationService();
            default: throw new IllegalArgumentException("Unsupported channel");
        }
    }
}

// ------------------ Async Dispatcher ------------------

class MessageSenderWorker implements Runnable {

    public static MessageSenderWorker messageSenderWorker;

    private MessageSenderWorker(){
    }

    public static MessageSenderWorker getInstance(){
        if(messageSenderWorker == null){
            messageSenderWorker = new MessageSenderWorker();
        }
        return messageSenderWorker;
    }

    private final BlockingQueue<Notification> queue = new LinkedBlockingQueue<>();

    public void sendNotification(Notification notification) {
        queue.add(notification);
    }

    @Override
    public void run() {
        try {
            System.out.println("hello");
            while (true) {
                Notification notification = queue.take(); // blocking
                NotificationService service =
                        NotificationServiceFactory.getService(notification.getChannelType());
                service.send(notification);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// client can send email only 1 at a time like Email or Mobile notification

// ---- Client ----

public class NotificationClient {
    public static void main(String[] args) throws InterruptedException {
        MessageSenderWorker worker = MessageSenderWorker.getInstance();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        Sender sender = new Sender(new User1.Builder()
                .userId("S1").userName("Admin").emailId("admin@system.com"));

        Receiver r1 = new Receiver(new User1.Builder()
                .userId("R1").userName("Alice").emailId("alice@example.com"));

        Receiver r2 = new Receiver(new User1.Builder()
                .userId("R2").userName("Bob").phoneNo("9876543210"));

        Message message = new Message("M1", "System maintenance at 2 AM", MessageType.ALERT);

        worker.sendNotification(new Notification(message, sender, Arrays.asList(r1), ChannelType.EMAIL));
        worker.sendNotification(new Notification(message, sender, Arrays.asList(r2), ChannelType.MOBILE));
        worker.sendNotification(new Notification(message, sender, Arrays.asList(r1, r2), ChannelType.PUSH));

        Thread.sleep(2000);
    }
}

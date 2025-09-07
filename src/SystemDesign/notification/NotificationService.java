package SystemDesign.notification;




// 1. Notification Entity
class Notification {
    private String recipient;
    private String message;
    private ChannelType channelType;
    private Priority priority;

    public Notification(String recipient, String message, ChannelType channelType, Priority priority) {
        this.recipient = recipient;
        this.message = message;
        this.channelType = channelType;
        this.priority = priority;
    }

    // + getters/setters/constructors


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

enum ChannelType { EMAIL, SMS, PUSH }
enum Priority { HIGH, MEDIUM, LOW }
enum Status { PENDING, SENT, FAILED, RETRYING }

// 2. Channel Interface
interface Channel {
    void send(Notification notification);
}

// 3. Concrete Channels
class EmailChannel implements Channel {
    @Override
    public void send(Notification notification) {
        // logic to send email
        System.out.println("Email sent to " + notification.getRecipient());
    }
}

class SMSChannel implements Channel {
    @Override
    public void send(Notification notification) {
        // logic to send sms
        System.out.println("SMS sent to " + notification.getRecipient());
    }
}

class PushChannel implements Channel {
    @Override
    public void send(Notification notification) {
        // logic to send push
        System.out.println("Push notification sent to " + notification.getRecipient());
    }
}

// 4. Channel Factory
class ChannelFactory {
    public static Channel getChannel(ChannelType type) {
        switch (type) {
            case EMAIL: return new EmailChannel();
            case SMS: return new SMSChannel();
            case PUSH: return new PushChannel();
            default: throw new IllegalArgumentException("Invalid channel");
        }
    }
}

// 5. Notification Service
class NotificationService {
    public void sendNotification(Notification notification) {
        Channel channel = ChannelFactory.getChannel(notification.getChannelType());
        channel.send(notification);
    }
}

 class Main {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        Notification n1 = new Notification("user@example.com", "Your OTP is 1234", ChannelType.EMAIL, Priority.HIGH);
        service.sendNotification(n1);

        Notification n2 = new Notification("9876543210", "Balance low", ChannelType.SMS, Priority.LOW);
        service.sendNotification(n2);
    }
}


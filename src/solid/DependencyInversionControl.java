package solid;

public class DependencyInversionControl {
}

// Lower modules should depend on the higher module but not higher modules should depend on the lower depend
/**
 * Bad Design (Without DIP - Tight Coupling)
 *
 * High level mpdiules should not depend on low level modules instead they should depend on abstractions
 *
 */

class PaypalPayment{
    public void pay(double amount){
        // prpocess payment
    }
}

/**
 * The PaymentService is tightly coupled with PayPalPayment.
 * Adding a new payment gateway like Stripe or Razorpay requires modifying PaymentService.
 * Violates the Open-Closed Principle (OCP) as well.
 */
class PaymentService1{

    private final PaypalPayment payPalPayment; // Stripe or Razorpay  --- need to change this service

    PaymentService1() {
        this.payPalPayment = new PaypalPayment();
    }

    public void processPayment(double amount) {
        payPalPayment.pay(amount);
    }
}


/**
 * Good Design (With DIP - Loose Coupling Using Abstraction)
 * We'll use an interface to abstract the payment logic, promoting flexibility.
 */

// this is abstraction
interface PaymentGateway{
    void pay(double amount);
}


// Low-level Modules (Implementations)
class PayPalPayment implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

class StripePayment implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Stripe.");
    }
}



// High-level Module (Depends on Abstraction)
class PaymentService2 {

    private final PaymentGateway paymentGateway;

    PaymentService2(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void processPayment(double amount){
        paymentGateway.pay(amount);
    }

}


// Usage (Client Code)
 class Main1 {
    public static void main(String[] args) {
        PaymentService2 payPalService = new PaymentService2(new PayPalPayment());
        payPalService.processPayment(100.0);

        PaymentService2 stripeService = new PaymentService2(new StripePayment());
        stripeService.processPayment(200.0);
    }
}


class Database{
    public void connect() {
        System.out.println("Connected to MySQL");
    }
}


// other example

// userservice is high level module and database is low level module. Userservice depends on database which makes it tight coupling
class UserService{
    private Database database = new Database();

    public void saveUser(User user) {
        database.connect();
        System.out.println("User saved to database");
    }
}

// For making it loose coupling we use dependency injection

interface Database1 {

    void connect();
}


// now userService is not tightly coupled
class UserService1{

    private Database1 database;

    public UserService1(Database1 database) {
        this.database = database;
    }

    public void saveUser(User user) {
        database.connect();
        System.out.println("User saved to database");
    }
}
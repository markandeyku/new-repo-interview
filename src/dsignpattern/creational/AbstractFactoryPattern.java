package dsignpattern.creational;

public class AbstractFactoryPattern {
}


/**
 * Design a system that can dynamically switch between multiple payment gateways like PayPal, Stripe, and Razorpay using the Abstract Factory Pattern.
 */

interface Payment{
    void processPayment(double amount);
}

//Step 2: Implement Concrete Payment Methods

class PaypalPayment implements Payment{

    @Override
    public void processPayment(double amount) {
        System.out.println("Payment processed by paypal "+ amount);
    }
}

class StripePayment  implements Payment{

    @Override
    public void processPayment(double amount) {
        System.out.println("Payment processed by StripePayment "+ amount);

    }
}
class RazorpayPayment  implements Payment{

    @Override
    public void processPayment(double amount) {
        System.out.println("Payment processed by RazorpayPayment "+ amount);
    }
}

//Step 3: Define Abstract Factory

// Abstract Factory
interface PaymentGatewayFactory {
    Payment createPayment();
}

//Step 4: Implement Concrete Factories

// Concrete Factory for PayPal
class PayPalFactory implements PaymentGatewayFactory {
    @Override
    public Payment createPayment() {
        return new PaypalPayment();
    }
}

// Concrete Factory for Stripe
class StripeFactory implements PaymentGatewayFactory {
    @Override
    public Payment createPayment() {
        return new StripePayment();
    }
}

// Concrete Factory for Razorpay
class RazorpayFactory implements PaymentGatewayFactory {
    @Override
    public Payment createPayment() {
        return new RazorpayPayment();
    }
}


//Step 5: Create a Factory Producer (Factory of Factories)

// Factory Producer
class PaymentGatewayProducer {
    public static PaymentGatewayFactory getFactory(String gatewayType) {
        if (gatewayType.equalsIgnoreCase("PAYPAL")) {
            return new PayPalFactory();
        } else if (gatewayType.equalsIgnoreCase("STRIPE")) {
            return new StripeFactory();
        } else if (gatewayType.equalsIgnoreCase("RAZORPAY")) {
            return new RazorpayFactory();
        }
        throw new IllegalArgumentException("❌ Invalid Payment Gateway: " + gatewayType);
    }
}


//Step 6: Implement Main Application

 class PaymentApp {
    public static void main(String[] args) {
        // Example 1: PayPal Payment
        PaymentGatewayFactory payPalFactory = PaymentGatewayProducer.getFactory("PAYPAL");
        Payment payPalPayment = payPalFactory.createPayment();
        payPalPayment.processPayment(150.00);

        // Example 2: Stripe Payment
        PaymentGatewayFactory stripeFactory = PaymentGatewayProducer.getFactory("STRIPE");
        Payment stripePayment = stripeFactory.createPayment();
        stripePayment.processPayment(250.50);

        // Example 3: Razorpay Payment
        PaymentGatewayFactory razorpayFactory = PaymentGatewayProducer.getFactory("RAZORPAY");
        Payment razorpayPayment = razorpayFactory.createPayment();
        razorpayPayment.processPayment(5000.00);
    }
}

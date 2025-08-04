package dsignpattern.behavioral;

// client code
public class StrategyDesignPattern {
    public static void main(String[] args) {

        ShoppingCart cart = new ShoppingCart(new CreditCard());
        cart.checkout(1000);
        cart.setPaymentStrategy(new Paypal());
        cart.checkout(9000);

    }
}
//The Strategy Pattern defines a family of interchangeable algorithms, allowing the client to select the best one dynamically.
// Dynamically we can decide which object functinality needs to used


// take an example of Paymentstrategey


// stratgey intefac
interface PaymentStrategy{
    void pay(int amount);
}


//concrete strategy
class CreditCard implements  PaymentStrategy{

    @Override
    public void pay(int amount) {
        System.out.println("payment is done with credit card of amount :: "+ amount);

    }
}


//concrete strategy
class Paypal implements PaymentStrategy{

    @Override
    public void pay(int amount) {
        System.out.println("payment is done with paypal of amount :: "+ amount);
    }
}

//context class

class ShoppingCart{

    private PaymentStrategy paymentStrategy;

    public ShoppingCart(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount){
        paymentStrategy.pay(amount);
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}






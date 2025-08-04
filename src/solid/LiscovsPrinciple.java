package solid;

public class LiscovsPrinciple {

}

//parent class, subclasses are substitutable

//lets talk about payment service has two methods, one for confirmation and other one process payment

abstract class Payment{

    public abstract void processPayment(double amount, String from, String to);

    public abstract void confirmPayment();
}


class CreditCardPayment extends Payment{
    @Override
    public void processPayment(double amount, String from, String to) {
        System.out.println("process payment ");
    }

    @Override
    public void confirmPayment() {
        System.out.println("Payment is completed");
    }
}

class CODPayment extends Payment{

    @Override
    public void processPayment(double amount, String from, String to) {
        System.out.println("processed payment ");

    }

    @Override
    public void confirmPayment() {
        throw new UnsupportedOperationException();
    }
}

// To make this correct code to follow principle, we need to reduce responsiblity of parent class


abstract class Payment1 {
    public abstract void processPayment(double amount, String from, String to);
}


abstract class OnlinePaymentMode {
    public abstract void processPayment(double amount, String from, String to);
    public abstract void confirmPayment();
}

abstract class OfflinePaymentMode{
    public abstract void processPayment(double amount, String from, String to);
}


class CreditCardPaymentService extends OnlinePaymentMode{

    @Override
    public void processPayment(double amount, String from, String to) {

    }

    @Override
    public void confirmPayment() {

    }
}

class CodPaymentService extends OfflinePaymentMode{

    @Override
    public void processPayment(double amount, String from, String to) {

    }
}


// I can take other example like Bird and Penguin

abstract class Bird{
    abstract void fly();
    abstract void makeSound();
}

class Penguin extends Bird{

    // this is violating the principle of liscov principles
    @Override
    void fly() {
        throw new UnsupportedOperationException("Penguin can not fly");
    }

    @Override
    void makeSound() {
//implement sound logic
    }
}


class Parrot extends Bird{

    @Override
    void fly() {
        System.out.println("parrot flying");
    }

    @Override
    void makeSound() {
        System.out.println("parrot sound ...");
    }
}


// Penguin and Parrot are subclasses but not substitutable

// let's make this correct code by reducing the responsibility of class

abstract class Bird1{
    abstract void makeSound();
}

interface  FlyableBird{
     void fly();
}

class Penguin1 extends Bird1{

    @Override
    void makeSound() {
        System.out.println(" penguin sound");
    }
}


class Parrot1 extends Bird1 implements FlyableBird{

    @Override
    void makeSound() {

    }

    @Override
    public void fly() {

    }
}
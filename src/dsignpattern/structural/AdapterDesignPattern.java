package dsignpattern.structural;

public class AdapterDesignPattern {

    public static void main(String[] args) {

    }
}

// let's take an example of Credit card payment system
// client wants to make a payment , but payment is done through Payment gateway and bank

//Target interface

interface CreditCard{
    void pay(String account, double amount);
}

// adaptee
class BankApi{
    void transferFund(String account, double amount){
        // logic to transfer
    }
}

//Adapter class

class PaymentGateway implements CreditCard{

    private BankApi bankApi;

    PaymentGateway(BankApi bankApi){
        this.bankApi = bankApi;
    }
    @Override
    public void pay(String account, double amount) {
        System.out.println("amount payment started "+ amount);

        bankApi.transferFund(account, amount);
        System.out.println("Amount transferred  "+ amount);

    }
}

//credit card cannot directly use the bank api transfer method, so, it uses payment gateway
//as an adapter

// client
class User{
    public static void main(String[] args) {
        BankApi bankApi = new BankApi();

        CreditCard creditCard = new PaymentGateway(bankApi);
        creditCard.pay("78456897896456",989);
    }
}


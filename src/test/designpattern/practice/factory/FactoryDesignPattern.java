package test.designpattern.practice.factory;

public class FactoryDesignPattern {

    public static void main(String[] args) {
        // let's do the implementaion of the banking system
        // we have bank as inteface and 2 concrete classes
        //PrivateBank and PublicBank, psuBank and ppuBank
        // now I want to create a factory class to create the objects
        // this factory class will take the inp ut from the user


        Bank bank = BankFactory.getBank(BankType.PRIVATE);
        bank.openAccount(); // we can pass here required parameters like user details name, address, user deatiakland other things that are reuired

        bank.getAccountDetails();
    }
}

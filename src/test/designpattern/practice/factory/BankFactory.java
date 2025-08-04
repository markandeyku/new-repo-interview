package test.designpattern.practice.factory;

public class BankFactory {

        private Bank bank;
        public static Bank getBank(BankType bankType) {
            return switch (bankType) {
                case PUBLIC -> new PublicBank();
                case PRIVATE -> new PrivateBank();
                default -> throw new IllegalStateException("Unexpected value: " + bankType);
            };
        }

}

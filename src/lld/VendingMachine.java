package lld;

import java.util.*;

// ----------------- Product -----------------
class Product {
    private final int id;
    private final String name;
    private final double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void reduceQuantity() {
        if (quantity > 0) quantity--;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }
}

// ----------------- Inventory -----------------
class Inventory {
    private final Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public boolean isAvailable(int id) {
        Product product = products.get(id);
        return product != null && product.isAvailable();
    }
}

// ----------------- Coins -----------------
enum Coin {
    PENNY(0.01), NICKEL(0.05), DIME(0.10),
    QUARTER(0.25), DOLLAR(1.0);

    private final double value;
    Coin(double value) { this.value = value; }
    public double getValue() { return value; }
}

// ----------------- Payment Processor -----------------
class PaymentProcessor {
    private double balance = 0.0;

    public void insertCoin(Coin coin) {
        balance += coin.getValue();
        System.out.println("Inserted: " + coin + " | Balance: $" + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void deduct(double amount) {
        balance -= amount;
    }

    public void refund() {
        System.out.println("Refunded: $" + balance);
        balance = 0.0;
    }
}

// ----------------- State Interface -----------------
interface State {
    void insertCoin(Coin coin);
    void selectProduct(int productId);
    void dispenseProduct(int productId);
    void cancel();
}

// ----------------- Concrete States -----------------
class IdleState implements State {
    private final VendingMachine vm;

    public IdleState(VendingMachine vm) { this.vm = vm; }

    @Override
    public void insertCoin(Coin coin) {
        vm.getPaymentProcessor().insertCoin(coin);
        vm.setState(new HasMoneyState(vm));
    }

    @Override
    public void selectProduct(int productId) {
        System.out.println("Insert coins first!");
    }

    @Override
    public void dispenseProduct(int productId) {
        System.out.println("Insert coins first!");
    }

    @Override
    public void cancel() {
        System.out.println("Nothing to cancel!");
    }
}

class HasMoneyState implements State {
    private final VendingMachine vm;

    public HasMoneyState(VendingMachine vm) { this.vm = vm; }

    @Override
    public void insertCoin(Coin coin) {
        vm.getPaymentProcessor().insertCoin(coin);
    }

    @Override
    public void selectProduct(int productId) {
        Inventory inventory = vm.getInventory();
        Product product = inventory.getProduct(productId);

        if (product == null) {
            System.out.println("Invalid product ID!");
            return;
        }
        if (!product.isAvailable()) {
            System.out.println(product.getName() + " is out of stock!");
            vm.setState(new IdleState(vm));
            return;
        }
        if (vm.getPaymentProcessor().getBalance() < product.getPrice()) {
            System.out.println("Insufficient balance! Please insert more coins.");
            return;
        }

        vm.setState(new DispensingState(vm));
        vm.dispenseProduct(productId);
    }

    @Override
    public void dispenseProduct(int productId) {
        System.out.println("Select product first!");
    }

    @Override
    public void cancel() {
        vm.getPaymentProcessor().refund();
        vm.setState(new IdleState(vm));
    }
}

class DispensingState implements State {
    private final VendingMachine vm;

    public DispensingState(VendingMachine vm) { this.vm = vm; }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Currently dispensing, please wait...");
    }

    @Override
    public void selectProduct(int productId) {
        System.out.println("Already processing product...");
    }

    @Override
    public void dispenseProduct(int productId) {
        Product product = vm.getInventory().getProduct(productId);

        if (product != null && product.isAvailable()) {
            double price = product.getPrice();
            vm.getPaymentProcessor().deduct(price);
            product.reduceQuantity();
            System.out.println("Dispensed: " + product.getName());
            System.out.println("Remaining balance: $" + vm.getPaymentProcessor().getBalance());
        } else {
            System.out.println("Product unavailable!");
        }

        vm.setState(new IdleState(vm));
    }

    @Override
    public void cancel() {
        System.out.println("Cannot cancel, product is being dispensed!");
    }
}

// ----------------- Vending Machine -----------------
class VendingMachine {
    private final Inventory inventory = new Inventory();
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();
    private State currentState;

    public VendingMachine() {
        this.currentState = new IdleState(this);
    }

    public Inventory getInventory() { return inventory; }
    public PaymentProcessor getPaymentProcessor() { return paymentProcessor; }

    public void setState(State state) { this.currentState = state; }

    public void insertCoin(Coin coin) { currentState.insertCoin(coin); }
    public void selectProduct(int productId) { currentState.selectProduct(productId); }
    public void dispenseProduct(int productId) { currentState.dispenseProduct(productId); }
    public void cancel() { currentState.cancel(); }
}

// ----------------- Driver -----------------
 class VendingMachineDemo {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();

        // Load inventory
        vm.getInventory().addProduct(new Product(1, "Coke", 1.25, 5));
        vm.getInventory().addProduct(new Product(2, "Pepsi", 1.00, 2));
        vm.getInventory().addProduct(new Product(3, "Water", 0.75, 0)); // out of stock

        // Simulation
        vm.insertCoin(Coin.DOLLAR);
        vm.insertCoin(Coin.QUARTER);
        vm.selectProduct(1); // Coke
        System.out.println();

        vm.insertCoin(Coin.DOLLAR);
        vm.selectProduct(3); // Water (out of stock)
        System.out.println();

        vm.insertCoin(Coin.DOLLAR);
        vm.cancel(); // Refund
    }
}

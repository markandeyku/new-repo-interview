package lld;


import java.util.*;

// ---------- Product1 ----------
class Product1 {
    private String id;
    private String name;
    private double price;

    public Product1(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " (₹" + price + ")";
    }
}

// ---------- Cart Item ----------
class CartItem {
    private Product1 Product1;
    private int quantity;

    public CartItem(Product1 Product1, int quantity) {
        this.Product1 = Product1;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return Product1.getPrice() * quantity;
    }

    public Product1 getProduct1() { return Product1; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return Product1.getName() + " x" + quantity + " = ₹" + getTotalPrice();
    }
}

// ---------- Discount Strategy ----------
interface DiscountStrategy {
    double applyDiscount(double total);
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(double total) { return total; }
}

class PercentageDiscount implements DiscountStrategy {
    private double percent;
    public PercentageDiscount(double percent) { this.percent = percent; }

    public double applyDiscount(double total) {
        return total - (total * percent / 100);
    }
}

// ---------- Payment Strategy ----------
interface PaymentStrategy1 {
    void pay(double amount);
}

class CardPayment implements PaymentStrategy1 {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " via Credit/Debit Card");
    }
}

class UPIPayment implements PaymentStrategy1 {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " via UPI");
    }
}

class WalletPayment implements PaymentStrategy1 {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " via Wallet");
    }
}

// ---------- Factory for Payment ----------
class PaymentFactory {
    public static PaymentStrategy1 getPaymentMethod(String method) {
        switch (method.toUpperCase()) {
            case "CARD": return new CardPayment();
            case "UPI": return new UPIPayment();
            case "WALLET": return new WalletPayment();
            default: throw new IllegalArgumentException("Unknown payment method");
        }
    }
}

// ---------- Singleton ShoppingCart ----------
class ShoppingCart {
    private static ShoppingCart instance;
    private List<CartItem> items;
    private DiscountStrategy discountStrategy;

    private ShoppingCart() {
        this.items = new ArrayList<>();
        this.discountStrategy = new NoDiscount(); // default
    }

    public static synchronized ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addItem(Product1 Product1, int quantity) {
        items.add(new CartItem(Product1, quantity));
    }

    public void removeItem(String Product1Id) {
        items.removeIf(item -> item.getProduct1().getId().equals(Product1Id));
    }

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return discountStrategy.applyDiscount(total);
    }

    public void checkout(PaymentStrategy1 paymentMethod) {
        double totalAmount = calculateTotal();
        paymentMethod.pay(totalAmount);
        items.clear(); // empty cart after checkout
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        for (CartItem item : items) {
            System.out.println(" - " + item);
        }
        System.out.println("Total (after discount): ₹" + calculateTotal());
    }
}

// ---------- Main ----------
 class Main1 {
    public static void main(String[] args) {
        Product1 p1 = new Product1("101", "Laptop", 50000);
        Product1 p2 = new Product1("102", "Headphones", 2000);
        Product1 p3 = new Product1("103", "Mouse", 800);

        ShoppingCart cart = ShoppingCart.getInstance();
        cart.addItem(p1, 1);
        cart.addItem(p2, 2);
        cart.addItem(p3, 1);

        cart.displayCart();

        // Apply discount
        cart.setDiscountStrategy(new PercentageDiscount(10));
        cart.displayCart();

        // Payment
        PaymentStrategy1 payment = PaymentFactory.getPaymentMethod("UPI");
        cart.checkout(payment);
    }
}

package lld;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// ---- Vehicle1 ----
enum Vehicle1Type1 { Car1, Bike1, Truck1 }

abstract class Vehicle1 {
    private String id;
    private String model;
    private Vehicle1Type1 type;
    private boolean available = true;

    public Vehicle1(String id, String model, Vehicle1Type1 type) {
        this.id = id;
        this.model = model;
        this.type = type;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public Vehicle1Type1 getType() { return type; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return type + " - " + model + " (" + id + ")";
    }
}

class Car1 extends Vehicle1 {
    public Car1(String id, String model) { super(id, model, Vehicle1Type1.Car1); }
}

class Bike1 extends Vehicle1 {
    public Bike1(String id, String model) { super(id, model, Vehicle1Type1.Bike1); }
}

class Truck1 extends Vehicle1 {
    public Truck1(String id, String model) { super(id, model, Vehicle1Type1.Truck1); }
}

// ---- USER ----
class User {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}

// ---- STRATEGY: PRICING ----
interface PricingStrategy {
    double calculatePrice(RentalBooking booking);
}

class HourlyPricingStrategy implements PricingStrategy {
    public double calculatePrice(RentalBooking booking) {
        long hours = Duration.between(booking.getStartTime(), booking.getEndTime()).toHours();
        return hours * 100; // ₹100 per hour
    }
}

class DailyPricingStrategy implements PricingStrategy {
    public double calculatePrice(RentalBooking booking) {
        long days = Duration.between(booking.getStartTime(), booking.getEndTime()).toDays();
        return days * 500; // ₹500 per day
    }
}

class KmPricingStrategy implements PricingStrategy {
    public double calculatePrice(RentalBooking booking) {
        return booking.getKmUsed() * 10; // ₹10 per km
    }
}

// ---- BOOKING ----
class RentalBooking {
    private String bookingId;
    private User user;
    private Vehicle1 Vehicle1;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private int kmUsed;

    public RentalBooking(User user, Vehicle1 Vehicle1) {
        this.bookingId = UUID.randomUUID().toString();
        this.user = user;
        this.Vehicle1 = Vehicle1;
        this.startTime = LocalDateTime.now();
    }

    public void endBooking(int kmUsed, PricingStrategy strategy) {
        this.endTime = LocalDateTime.now();
        this.kmUsed = kmUsed;
        this.price = strategy.calculatePrice(this);
        Vehicle1.setAvailable(true);
    }

    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Vehicle1 getVehicle1() { return Vehicle1; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getKmUsed() { return kmUsed; }
    public double getPrice() { return price; }
}

// ---- INVENTORY ----
class InventoryService {
    private List<Vehicle1> Vehicle1s = new ArrayList<>();

    public void addVehicle1(Vehicle1 v) { Vehicle1s.add(v); }

    public List<Vehicle1> searchAvailableVehicle1s(Vehicle1Type1 type) {
        List<Vehicle1> result = new ArrayList<>();
        for (Vehicle1 v : Vehicle1s) {
            if (v.getType() == type && v.isAvailable()) {
                result.add(v);
            }
        }
        return result;
    }
}

// ---- PAYMENT ----
class PaymentService {
    public void processPayment(User user, double amount) {
        System.out.println("Payment of ₹" + amount + " processed for user " + user.getName());
    }
}

// ---- RENTAL SYSTEM (Facade + Singleton) ----
class RentalSystem {
    private static RentalSystem instance;
    private InventoryService inventory;
    private PaymentService paymentService;
    private List<RentalBooking> bookings;

    private RentalSystem() {
        inventory = new InventoryService();
        paymentService = new PaymentService();
        bookings = new ArrayList<>();
    }

    public static synchronized RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    public void addVehicle1(Vehicle1 v) { inventory.addVehicle1(v); }

    public RentalBooking createBooking(User user, Vehicle1Type1 type) {
        List<Vehicle1> available = inventory.searchAvailableVehicle1s(type);
        if (available.isEmpty()) {
            System.out.println("No " + type + " available.");
            return null;
        }
        Vehicle1 v = available.get(0);
        v.setAvailable(false);
        RentalBooking booking = new RentalBooking(user, v);
        bookings.add(booking);
        System.out.println("Booking created: " + booking.getBookingId() + " for " + v);
        return booking;
    }

    public void closeBooking(RentalBooking booking, int kmUsed, PricingStrategy strategy) {
        booking.endBooking(kmUsed, strategy);
        paymentService.processPayment(booking.getUser(), booking.getPrice());
        System.out.println("Booking closed. Total: ₹" + booking.getPrice());
    }
}

// ---- MAIN DRIVER ----
 class RentalMain {
    public static void main(String[] args) throws InterruptedException {
        RentalSystem system = RentalSystem.getInstance();

        // Vehicle1s
        system.addVehicle1(new Car1("C1", "Honda City"));
        system.addVehicle1(new Bike1("B1", "Royal Enfield"));
        system.addVehicle1(new Truck1("T1", "Tata Truck1"));

        // User
        User user = new User("U1", "Mark");

        // Booking a Car1
        RentalBooking booking = system.createBooking(user, Vehicle1Type1.Car1);

        Thread.sleep(2000); // simulate usage

        // End booking with hourly pricing
        system.closeBooking(booking, 20, new HourlyPricingStrategy());
    }
}

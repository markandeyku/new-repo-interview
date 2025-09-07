package lld;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Design Patterns in Parking Lot
 * 1. Singleton – ParkingLot
 * Only one parking lot instance should exist per location.
 * We enforce this with the Singleton Pattern.
 * 2. Factory Method / Abstract Factory – ParkingSpotFactory
 * Different vehicle types (Car, Bike, Truck, EV) → different spots.
 * A Factory creates the right ParkingSpot.
 * 3. Strategy – PricingStrategy
 * Pricing can change: hourly, flat fee, progressive, subscription.
 * The Strategy Pattern allows flexible calculation.
 * 4. Observer – Notification
 * Notify user when the lot is full or EV charging completes.
 * Observer pattern for real-time updates.
 */


/**
 * ParkingLot (Singleton)
 *  ├── List<ParkingFloor>
 *  ├── List<EntryGate>
 *  ├── List<ExitGate>
 *  └── getAvailableSpot(VehicleType)
 *
 * ParkingSpotFactory (Factory)
 *  └── createSpot(VehicleType)
 *
 * PricingStrategy (Strategy - interface)
 *  ├── calculateFee(ParkingTicket)
 *  ├── HourlyPricingStrategy
 *  ├── FlatRatePricingStrategy
 *  └── ProgressivePricingStrategy
 *
 * EntryGate
 *  └── issueTicket(Vehicle)
 *
 * ExitGate
 *  └── processPayment(ParkingTicket, PricingStrategy)
 */

/**
 * 🔹 Flow of the System (End-to-End)
 * Initialization
 * Create a ParkingLot (Singleton).
 * Add ParkingFloors and initialize spots via ParkingSpotFactory.
 * Vehicle Entry
 * Vehicle arrives at EntryGate.
 * System checks for an available spot (getAvailableSpot).
 * Assigns spot and issues ParkingTicket.
 * Parking Occupancy
 * Spot is marked as occupied.
 * Ticket has entry time and vehicle info.
 *
 * Vehicle Exit
 * At ExitGate, driver submits ticket.
 * ExitGate applies chosen PricingStrategy.
 * Payment is processed, and spot is freed.
 * Availability Update
 * Spot becomes available again for the next vehicle.
 * ParkingFloor.displayAvailability() can show current status.
 * 🔹 Example Interview Walkthrough
 * 💡 Suppose the interviewer asks: "Explain the flow when a Car enters and exits."
 * You can say:
 *
 * When the Car comes in, the EntryGate calls ParkingLot.getAvailableSpot(CAR).
 * The system checks floors, finds a free CarSpot, and assigns it to the vehicle.
 * A ParkingTicket is generated with entry time.
 * When the Car exits, the ExitGate marks exit time on the ticket.
 * The PricingStrategy is applied (e.g., hourly).
 * The payment is shown, and the CarSpot is freed.
 * 🔹 Why This Design is Good
 * Singleton ensures one parking lot instance.
 * Factory makes adding new spot/vehicle types easy.
 * Strategy allows multiple pricing policies.
 * Observer (extendable) allows user notifications.
 * Clean separation of concerns → scalable & maintainabl
 */


 class Main {
    public static void main(String[] args) {
        ParkingFloor floor1 = new ParkingFloor(1); // decide how many floors you have

        floor1.addSpot(ParkingSpotFactory.createSpot(VehicleType.CAR, "C1")); // add spots
        floor1.addSpot(ParkingSpotFactory.createSpot(VehicleType.BIKE, "B1"));
        floor1.addSpot(ParkingSpotFactory.createSpot(VehicleType.TRUCK, "T1"));
        floor1.addSpot(ParkingSpotFactory.createSpot(VehicleType.ELECTRIC, "E1"));

        Vehicle car = new Car("DL-01-1234"); // car to park
        Vehicle bike = new Bike("DL-02-5678"); // bike to park

        ParkingTicket ticket1 = floor1.parkVehicle(car); // parking car
        ParkingTicket ticket2 = floor1.parkVehicle(bike); // parking bike

        floor1.displayAvailability(); // showing available seats


        floor1.exitVehicle(ticket1, new HourlyPaymentStrategy(30));  // exiting the vehicles ,  Here we need price strategy using strategy pattern
        floor1.exitVehicle(ticket2, new FlatFeeStrategy(20));

        floor1.displayAvailability(); // showing available seats

    }
}

abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }

    @Override
    public String toString() {
        return type + " (" + licensePlate + ")";
    }
}

class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }
}

class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, VehicleType.BIKE);
    }
}

class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }
}

class ElectricCar extends Vehicle {
    public ElectricCar(String licensePlate) {
        super(licensePlate, VehicleType.ELECTRIC);
    }
}


// ----- Singleton Pattern -----
public class ParkingLot {
    private static ParkingLot instance;
    private List<ParkingFloor> floors;

    private ParkingLot() {
        floors = new ArrayList<>();
    }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public ParkingSpot getAvailableSpot(VehicleType type) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.getAvailableSpot(type);
            if (spot != null) return spot;
        }
        return null;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }
}

class ParkingFloor {
    private int floorNumber;
    private Map<VehicleType, Map<String, ParkingSpot>> spotMap; // all spots

    Map<VehicleType, Set<ParkingSpot>> availableSpots; // only free spots


    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spotMap = new HashMap<>();
        this.availableSpots = new HashMap<>();
        for (VehicleType type : VehicleType.values()) {
            spotMap.put(type, new HashMap<>());
            availableSpots.put(type, new HashSet<>());
        }
    }

    public void addSpot(ParkingSpot spot) {
        spotMap.get(spot.getVehicle().getType()).put(spot.getId(), spot);
        availableSpots.get(spot.getVehicle().getType()).add(spot);
    }

    public ParkingSpot getAvailableSpot(VehicleType type) {
        Set<ParkingSpot> available = availableSpots.get(type);
        if (available.isEmpty()) return null;
        return available.iterator().next();
    }

    public ParkingSpot getSpotById(VehicleType type, String spotId) {
        return spotMap.get(type).get(spotId);
    }

    public void displayAvailability() {
        System.out.println("Floor " + floorNumber + " availability:");
        for (VehicleType type : availableSpots.keySet()) {
            System.out.print("   " + type + " spots: ");
            for (ParkingSpot spot : availableSpots.get(type)) {
                System.out.print("[" + spot + "] ");
            }
            System.out.println();
        }
    }
    public ParkingTicket parkVehicle(Vehicle vehicle) {
        Set<ParkingSpot> freeSpots = availableSpots.get(vehicle.getType());
        if (freeSpots == null || freeSpots.isEmpty()) {
            System.out.println("No available spot for " + vehicle.getType());
            return null;
        }

        // Get any available spot (can optimize with a queue)
        ParkingSpot spot = freeSpots.iterator().next();
        spot.assignVehicle(vehicle);

        // Mark spot as unavailable
        freeSpots.remove(spot);


        // Generate ticket
        ParkingTicket ticket = new ParkingTicket(UUID.randomUUID().toString(), vehicle, spot);
        return ticket;
    }


    public void exitVehicle(ParkingTicket ticket, PaymentStrategy paymentStrategy) {
        Vehicle vehicle = ticket.getVehicle();
        ParkingSpot spot = ticket.getSpot();
        ticket.markExit();

        // Calculate price using strategy
        double price = paymentStrategy.calculatePrice(ticket);
        System.out.println("Payment for " + vehicle + ": $" + price + " completed.");

        // Free the spot
        spot.removeVehicle();
        availableSpots.get(vehicle.getType()).add(spot);
      }


    public void freeSpot(ParkingSpot spot) {
        availableSpots.get(spot.getVehicle().getType()).add(spot);
        spot.removeVehicle();
    }

}

 interface PaymentStrategy {
    double calculatePrice(ParkingTicket ticket);
}

// Simple hourly rate
 class HourlyPaymentStrategy implements PaymentStrategy {
    private double ratePerHour;

    public HourlyPaymentStrategy(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculatePrice(ParkingTicket ticket) {

        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();  // current time when exiting

        Duration duration = Duration.between(entryTime, exitTime);
        long priceHours = duration.toHours();
        if (duration.toMinutes() % 60 != 0) {
            priceHours += 1; // round up
        }
        return priceHours * ratePerHour;
    }
}

// Flat fee
 class FlatFeeStrategy implements PaymentStrategy {
    private double flatFee;

    public FlatFeeStrategy(double flatFee) {
        this.flatFee = flatFee;
    }

    @Override
    public double calculatePrice(ParkingTicket ticket) {
        return flatFee;
    }
}



// ----- Factory Pattern -----
class ParkingSpotFactory {
    public static ParkingSpot createSpot(VehicleType type, String id) {
        switch (type) {
            case CAR: return new CarSpot(id);
            case BIKE: return new BikeSpot(id);
            case TRUCK: return new TruckSpot(id);
            case ELECTRIC: return new ElectricSpot(id);
            default: throw new IllegalArgumentException("Invalid vehicle type");
        }
    }
}

enum VehicleType {
    CAR,
    BIKE,
    TRUCK,
    ELECTRIC
}

abstract class ParkingSpot {
    private String id;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(String id) {
        this.id = id;
        this.occupied = false;
    }

    public boolean isAvailable() {
        return !occupied;
    }

    public void assignVehicle(Vehicle v) {
        if (occupied) throw new IllegalStateException("Spot already occupied!");
        this.vehicle = v;
        this.occupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.occupied = false;
    }

    public Vehicle getVehicle() { return vehicle; }
    public String getId() { return id; }

    @Override
    public String toString() {
        return "Spot " + id + " (" + this.getClass().getSimpleName() + ") " +
                (occupied ? "Occupied by " + vehicle.getLicensePlate() : "Free");
    }
}

class CarSpot extends ParkingSpot {
    public CarSpot(String id) {
        super(id);
    }
}

class BikeSpot extends ParkingSpot {
    public BikeSpot(String id) {
        super(id);
    }
}

class TruckSpot extends ParkingSpot {
    public TruckSpot(String id) {
        super(id);
    }
}

class ElectricSpot extends ParkingSpot {
    public ElectricSpot(String id) {
        super(id);
    }
}


// ----- Ticket -----
class ParkingTicket {
    private String ticketId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private boolean isPaid;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.isPaid = false;
    }

    public void markExit() {
        this.exitTime = LocalDateTime.now();
    }

    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public ParkingSpot getSpot() { return spot; }
    public Vehicle getVehicle() { return vehicle; }
}




// ----- Entry Gate -----
class EntryGate {
    public ParkingTicket issueTicket(Vehicle vehicle) {
        ParkingSpot spot = ParkingLot.getInstance().getAvailableSpot(vehicle.getType());
        if (spot == null) throw new RuntimeException("No spots available!");

        spot.assignVehicle(vehicle);
        return new ParkingTicket(UUID.randomUUID().toString(), vehicle, spot);
    }
}

// ----- Exit Gate -----
class ExitGate {
    public void processPayment(ParkingTicket ticket, PaymentStrategy strategy) {
        ticket.markExit();
        double amount = strategy.calculatePrice(ticket);
        System.out.println("Payment required: ₹" + amount);
        ticket.getSpot().removeVehicle();
    }
}

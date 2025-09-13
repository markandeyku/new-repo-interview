package lld;

import java.time.LocalDate;
import java.util.*;

// functional requirements

/**
 * 1. User should be able to search the hotels according to city wise
 * 2. User should be able to book the hotel room
 * 3. Do the payment
 * 4. Cancel the booking
 * 5. In case of unknown issue, refund the amount and cancel the booking by admin
 * 6. Admin should be able to add the hotels, room of different types on the basis of city and location
 * 7. Admin should also be able to do the bookings for users with different payment modes like credit card, cash
 * 8. Admin should be able to decide the room rate, discount according to the situation
 * 9. User should be able to see booking history
 * 10. Admin can check all booking history
 */


//Non functional requirements
/**
 * Should be highly concurrent and consistent og booking
 * Double booking should not be allowed for the same user
 * Low latency
 * Highly scalable
 */

// let's discuss the flow
/**
 * User search hotel on the basis of city ---> Select Hotel  --> Select Room type (1 or more room)  --> apply discount if any --> checkout -->
 * Complete payment ---> Room book  --> Print Hotel Room booking  details
 */

// let's discuss what are the entities involved

/**
 * User {
 *     userID;
 *     userName;
 *     emailId
 *     phoneNo
 *     Address
 *     ...
 * }
 *
 * Admin extends User{
 *     addHotel();
 *     addRoom();
 *     removeHotel();
 *     removeRoom();
 * }
 *
 * Hotel{
 *     hotelId
 *     hotelName
 *     country
 *     city
 *     Location location
 *     List<Room> room
 * }
 *
 * Location{
 *     latitude
 *     altitude
 * }
 *
 * Room{
 *     roomId;
 *     hotelId;
 *     RoomType;
 *     price
 *     isAvailable
 *     ..
 * }
 *
 * RoomType{
 *     DUPLEX,SINGLE,
 * }
 *
 * Booking{
 *     bookingId
 *     userName
 *     hotelName
 *     List<Room> bookedRooms
 *     bookingDate
 *     checkInDate
 *     checkOutDate
 *     BookingStatus
 *     Payment
 * }
 *
 * Payment{
 *     paymentId
 *     PaymentStatus
 *     PaymentMode - (CREDIT_CARD, DEBIT_CARD, CASH, UPI, NET_BANKING)
 *     amount
 * }
 *
 * Discount{
 *     discountId
 *     percentage
 *     validTill
 *     appliesTo (roomType/hotel/user-specific)
 * }
 *8. Search Service
 * Search hotels by city
 * Filter by location, priceRange, roomType
 * 9. Booking Service
 * Book rooms
 * Cancel booking
 * Ensure no double booking (concurrency handling)
 * Admin override bookings
 *
 */



// ----------- ENUMS -----------
enum RoomType { SINGLE, DOUBLE, SUITE }
enum BookingStatus { CONFIRMED, CANCELLED }

// ----------- ENTITIES -----------



class Room {
    private final String roomId;
    private final RoomType type;
    private double price;
    private boolean available;

    public Room(String roomId, RoomType type, double price) {
        this.roomId = roomId; this.type = type; this.price = price;
        this.available = true;
    }

    public String getRoomId() { return roomId; }
    public RoomType getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
}

class Hotel {
    private final String hotelId;
    private final String name;
    private final String city;
    private final List<Room> rooms = new ArrayList<>();

    public Hotel(String hotelId, String name, String city) {
        this.hotelId = hotelId; this.name = name; this.city = city;
    }

    public String getHotelId() { return hotelId; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public List<Room> getRooms() { return rooms; }

    public void addRoom(Room room) { rooms.add(room); }
}


class Booking {
    private final String bookingId;
    private final User user;
    private final Hotel hotel;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private BookingStatus status;
    private final String transactionId;
    private final double amount;
    private final PaymentStatus paymentStatus;
    private final String message;
    private final PaymentMode mode;
// we can use builder pattern to make it more readable
    public Booking(String bookingId, User user, Hotel hotel, Room room, LocalDate checkIn, LocalDate checkOut, String transactionId, double amount, PaymentStatus paymentStatus, String message, PaymentMode mode, BookingStatus status) {
        this.bookingId = bookingId;
        this.user = user; // this can be replace with id only
        this.hotel = hotel; // this can be replaced with only id to link Hotel object
        this.room = room;   // this can be replaced with only id to link Room object
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.transactionId = transactionId;
        this.amount = amount;
        this.message = message;
        this.mode = mode;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getMessage() {
        return message;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public void cancel() {
        System.out.println("Booking is cancelled for user "+ this.user +" Hotel "+ hotel + " room "+ room);
    }
}

// ----------- SERVICES -----------

class HotelService {
    private final Map<String, Hotel> hotels = new HashMap<>();

    public void addHotel(Hotel hotel) { hotels.put(hotel.getHotelId(), hotel); }

    public List<Hotel> searchByCity(String city) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel h : hotels.values()) {
            if (h.getCity().equalsIgnoreCase(city)) result.add(h);
        }
        return result;
    }
}





class BookingService {
    private final Map<String, Booking> bookings = new HashMap<>();
    PaymentManagementService service = new PaymentManagementService();


    public Booking bookRoom(User user, Hotel hotel, Room room,
                            LocalDate checkIn, LocalDate checkOut,
                            PaymentMode mode) {
        if (!room.isAvailable()) throw new RuntimeException("Room not available!");
        room.setAvailable(false);


        // Here directly I am paying the mentioned room price, but we can implement calculate the price based on the discount and
        // other factors like implement PriceCalculationStrategy on the basis of room type, user type, discount etc
        PaymentRequest request = new PaymentRequest("U1", room.getPrice(), PaymentMode.CREDIT_CARD);
        PaymentResponse response = service.pay(request);
        System.out.println("TxnId: " + response.getTransactionId() +
                " Status: " + response.getStatus() + " Msg: " + response.getMessage());
       // PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(mode); we can implement payment strategy in future
        Booking booking = new Booking(UUID.randomUUID().toString(), user, hotel, room, checkIn, checkOut, response.getTransactionId(), room.getPrice(), response.getStatus(), response.getMessage(), mode, BookingStatus.CONFIRMED);
        bookings.put(booking.getBookingId(), booking);
        return booking;
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) booking.cancel();

    }

    public List<Booking> getUserBookings(User user) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getUser().getId().equals(user.getId())) result.add(b);
        }
        return result;
    }
}

// ----------- CLIENT (Demo) -----------

public class HotelManagement {
    public static void main(String[] args) {
        // Setup
        HotelService hotelService = new HotelService();
        BookingService bookingService = new BookingService();

        Hotel h1 = new Hotel("H1", "Taj Hotel", "Delhi");
        h1.addRoom(new Room("R1", RoomType.SINGLE, 2000));
        h1.addRoom(new Room("R2", RoomType.DOUBLE, 3000));
        hotelService.addHotel(h1);

        // User
        User u1 = new User("U1", "Alice", "alice@example.com");

        // 1. Search hotel
        List<Hotel> delhiHotels = hotelService.searchByCity("Delhi");
        System.out.println("Hotels in Delhi: " + delhiHotels.size());

        // 2. Book room
        Room roomToBook = delhiHotels.get(0).getRooms().get(0);
        Booking booking = bookingService.bookRoom(u1, h1, roomToBook,
                LocalDate.now(), LocalDate.now().plusDays(2), PaymentMode.CREDIT_CARD);
        System.out.println("Booking Confirmed: " + booking.getBookingId());

        // 3. Cancel booking
        bookingService.cancelBooking(booking.getBookingId());
        System.out.println("Booking Cancelled: " + booking.getBookingId());

        // 4. View history
        System.out.println("User Bookings: " + bookingService.getUserBookings(u1).size());
    }
}

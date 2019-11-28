import java.math.BigDecimal;
import java.security.Provider;
import java.util.Collection;
import java.util.HashMap;
import java.lang.System;

public class MockSystem implements RentalSystem{
    //The system holds all the information on the bike providers, the bikes in the system
    //We will create some bikes and bike providers to be used in testing

    private HashMap<String,Bike> bikes; /*some hashMap of bikes where the key is the bike ID*/
    private HashMap<Location, BikeProvider> providers;
    private HashMap<String,Customer> customers;
    private HashMap<Integer,Booking> bookings;
    private static int BikeIDCounter;
    private static int BookingIDCounter;

    public MockSystem() {
         //Create bunch of stuff here
        //Add provider
        this.bikes = new HashMap<String,Bike>();
        this.providers = new HashMap<Location, BikeProvider>();
        this.customers = new HashMap<String, Customer>();
        this.bookings = new HashMap<Integer, Booking>();
    }


    public void addBike(Bike bike) {
        /*generate unique ID and write new bike to file */
        String ID = createBikeID();
        bikes.put(ID, bike);
    }

    public void addProvider(BikeProvider provider) {
        providers.put(provider.getAddress(), provider);
    }

    public void addBooking(Booking booking) {
        Integer ID = createBookingID();
        this.bookings.put(ID, booking);
    }

    public void addCustomer(Customer customer) { customers.put(customer.getEmail(), customer); }

    public HashMap<String, Bike> getBikes() {
        return this.bikes;
    }

    public Bike findBike(String ID) {
        return bikes.get(ID);
    }

    public Booking findBooking(Integer ID) {
        return bookings.get(ID);
    }

    public BikeProvider getProvider(Location location) {
        return providers.get(location);
    }

    //creates unique id for bike when new bike is added
    static synchronized String createBikeID() {
        String s = String.valueOf(BikeIDCounter++);
        return s;
    }

    //creates unique id for bookings when new booking is added
    static synchronized Integer createBookingID() {
        Integer s = BookingIDCounter++;
        return s;
    }
}

import java.security.Provider;
import java.util.Collection;
import java.util.HashMap;

public class MockSystem implements System{
    //The system holds all the information on the bike providers, the bikes in the system
    //We will create some bikes and bike providers to be used in testing

    private HashMap<String,Bike> bikes; /*some hashMap of bikes where the key is the bike ID*/
    private HashMap<Location, BikeProvider> providers;
    private HashMap<String,Customer> customers;
    private HashMap<Integer,Booking> bookings;
    private static int BikeIDCounter;
    private static Integer BookingIDCounter;

    public MockSystem() {
        //Create bunch of stuff here
        this.BikeIDCounter = 0;
        //Add bikes
        Bike bike1 = new Bike();
        Bike bike2 = new Bike();
        Bike bike3 = new Bike();
        Bike bike4 = new Bike();
        Bike bike5 = new Bike();
        this.addBike(bike1);
        this.addBike(bike2);
        this.addBike(bike3);
        this.addBike(bike4);
        this.addBike(bike5);
        //Add provider
        BikeProvider o_provider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
        BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        this.addProvider(o_provider);
        this.addProvider(p_provider);
    }


    public void addBike(Bike bike) {
        /*generate unique ID and write new bike to file */
        String ID = createID();
        bikes.put(ID, bike);
    }

    public void addProvider(BikeProvider provider) {
        providers.put(provider.getAddress(), provider);
    }

    public void addBooking(Booking booking) {
        bookings.put(BookingIDCounter++, booking);
    }


    //creates unique id for bike when new bike is added
    static synchronized String createID() {
        String s = String.valueOf(BikeIDCounter++);
        return s;
    }
}

import org.junit.jupiter.api.Test;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    //Required Testing Variables
    Customer customer = new Customer("John Doe",
            "07293 987091",
            "JohnDoe@yahoo.com",
            new Location ("EH8 4YU", "54 Brady Avenue"),
            "Password");
    private BikeProvider o_provider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
    private BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
    //Add bikes
    private Bike bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, new BigDecimal(10), o_provider);
    private Bike bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, new BigDecimal(39.95), p_provider);
    private Bike bike3 = new Bike(new BikeType(2), "AllTerrain Lad", Size.S, new BigDecimal(15.50), o_provider);
    private Bike bike4 = new Bike(new BikeType(3), "BeeEmEx", Size.M, new BigDecimal(19.10), p_provider);
    private Bike bike5 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.XXL, new BigDecimal(49.86), o_provider);

    private Bike[] bikes = {bike1,bike2,bike3,bike4,bike5};
    private BikeProvider[] providers = {o_provider,p_provider};

    @Test
    void requestQuotesTest() {
        customer.addBikesAndProviders(providers,bikes);
        DateRange dateRange = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MAY, 1));
        Location location = new Location("EH8 342", "x");
        boolean[] choices = {false,true,false,false,false};
        HashMap<String, Quote> trial = customer.RequestQuotes(dateRange, location, choices, Size.L);
        assertTrue(!(trial==null));
        for (Map.Entry<String, Quote> entry: trial.entrySet()) {
            assertEquals("1", entry.getKey());
        }
    }

    //now test add quote to cart
    //then test book quote

}
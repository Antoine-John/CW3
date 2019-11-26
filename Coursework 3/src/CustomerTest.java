import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer = new Customer("John Doe",
            "07293 987091",
            "JohnDoe@yahoo.com",
            new Location ("EH8 4YU", "54 Brady Avenue"),
            "Password");

    @Test
    void requestQuotesTest() {
        customer.addBikesAndProviders();
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
package uk.ac.ed.bikerental;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


public class TestQuote {

    private BikeProvider o_provider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
    private BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");

    private Bike bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, o_provider);
    private Bike bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, p_provider);

    @Test
    void equalsTest() {
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MAY, 1));
        Quote quote1 = new Quote(bike1, range1);
        Quote quote2 = new Quote(bike1, range1);
        assertTrue(quote1.equals(quote2));
    }
}

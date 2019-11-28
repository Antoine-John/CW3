package uk.ac.ed.bikerental;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TestBike {
    //Testing Overlap function in DateRange
    @Test
    void testEquals1() {
        BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        Bike bike1 = new Bike(new BikeType(0), "x", Size.XS, new BigDecimal(10), p_provider);
        Bike bike2 = new Bike(new BikeType(0), "x", Size.XS, new BigDecimal(10), p_provider);
        assertTrue(bike1.equals(bike2));
    }

    @Test
    void testEquals2() {
        BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        BikeProvider o_provider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
        Bike bike1 = new Bike(new BikeType(0), "x", Size.XS, new BigDecimal(10), p_provider);
        Bike bike2 = new Bike(new BikeType(0), "x", Size.XS, new BigDecimal(10), o_provider);
        assertTrue(!bike1.equals(bike2));
    }

    @Test
    void testEquals3() {
        BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        Bike bike1 = new Bike(new BikeType(0), "x", Size.XS, new BigDecimal(10), p_provider);
        Bike bike2 = new Bike(new BikeType(0), "asdfsfsfasf", Size.XS, new BigDecimal(10), p_provider);
        assertTrue(bike1.equals(bike2));
    }

    @Test
    void testEquals4() {
        BikeProvider p_provider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        Bike bike1 = new Bike(new BikeType(1), "x", Size.XS, new BigDecimal(10), p_provider);
        Bike bike2 = new Bike(new BikeType(0), "asdfsfsfasf", Size.XS, new BigDecimal(10), p_provider);
        assertTrue(!bike1.equals(bike2));
    }



}
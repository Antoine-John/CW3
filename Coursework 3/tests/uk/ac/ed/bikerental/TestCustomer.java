package uk.ac.ed.bikerental;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.lang.System;

import static org.junit.jupiter.api.Assertions.*;

class TestCustomer {
    //Required Testing Variables
    private static BikeProvider oProvider;
    private static BikeProvider pProvider;

    private static Customer customer;

    private static Bike bike1;
    private static Bike bike2;
    private static Bike bike3;
    private static Bike bike4;
    private static Bike bike5;
    private static Bike bike6;
    private static Bike bike7;
    private static Bike bike8;
    private static Bike bike9;
    private static Bike bike10;

    private static HashMap<String, Quote> curr_search;

    @BeforeAll
    static void oneTimeSetUp() {
        oProvider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
        pProvider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");

        bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, new BigDecimal(10), oProvider); //Id=0
        bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, new BigDecimal(39.95), pProvider); //Id = 1...
        bike3 = new Bike(new BikeType(2), "AllTerrain Lad", Size.S, new BigDecimal(15.50), oProvider);
        bike4 = new Bike(new BikeType(3), "BeeEmEx", Size.M, new BigDecimal(19.10), pProvider);
        bike5 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.XXL, new BigDecimal(49.86), oProvider);
        bike6 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.L, new BigDecimal(10), oProvider);
        bike7 = new Bike(new BikeType(1), "HillBilly", Size.L, new BigDecimal(39.95), pProvider);
        bike8 = new Bike(new BikeType(2), "AllTerrain Lad", Size.XXS, new BigDecimal(15.50), oProvider);
        bike9 = new Bike(new BikeType(1), "BeeEmEx", Size.L, new BigDecimal(19.10), pProvider);
        bike10 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.M, new BigDecimal(49.86), oProvider);

        customer = new Customer("John Doe",
                "07293 987091",
                "JohnDoe@yahoo.com",
                new Location ("EH8 4YU", "54 Brady Avenue"),
                "Password");

        customer.system.addBike(bike1);
        customer.system.addBike(bike2);
        customer.system.addBike(bike3);
        customer.system.addBike(bike4);
        customer.system.addBike(bike5);
        customer.system.addBike(bike6);
        customer.system.addBike(bike7);
        customer.system.addBike(bike8);
        customer.system.addBike(bike9);
        customer.system.addBike(bike10);

        customer.system.addProvider(oProvider);
        customer.system.addProvider(pProvider);

        DateRange dateRange = new DateRange(LocalDate.of(2019, Month.NOVEMBER, 1), LocalDate.of(2019, Month.NOVEMBER, 10));
        Location location = new Location("EH8 342", "x");
        boolean[] choices = {true, true, false, false, false};
        DateRange dateRange2 = new DateRange(LocalDate.of(2019, Month.NOVEMBER, 1), LocalDate.of(2019, Month.NOVEMBER, 10));
        bike9.updateRentalPeriods(dateRange2);
        curr_search = customer.RequestQuotes(dateRange, location, choices, Size.L);
    }

    @Test
    @Order(1)
    void requestQuotesTest() {
        ArrayList<String> keys = new ArrayList<String>();
        String[] masterKeys = {"1", "5", "6"};
        assertEquals(3, curr_search.size());
        for (Map.Entry<String, Quote> entry : curr_search.entrySet()) {
            keys.add(entry.getKey());
            //System.out.println(entry.getValue());
        }
        Iterator<String> iter = keys.iterator();
        int idx = 0;
        while (iter.hasNext()) {
            assertTrue(masterKeys[idx].equals(iter.next()));
            idx++;
        }
    }
    @Test
    @Order(2)
    void addQuoteToCartTest() {

        assertTrue(!curr_search.isEmpty());
        customer.AddQuoteToCart(curr_search.get("1"), 2);
        //Test
        assertTrue(customer.getCart() != null);
        if (customer.getCart().getContents().isEmpty()) {
            System.out.println("Cart is empty booking failed");
        } else {
            assertTrue(customer.getCart().getContents().get("1").getBike().equals(bike2));
        }
    }

    //checks adding multiple quotes to cart
    @Test
    @Order(3)
    void addQuoteToCartTest2() {

        assertTrue(!curr_search.isEmpty());
        customer.AddQuoteToCart(curr_search.get("6"), 1);
        //Test
        assertTrue(customer.getCart() != null);
        if (customer.getCart().getContents().isEmpty()) {
            System.out.println("Cart is empty booking failed");
        } else {
            assertTrue(customer.getCart().getContents().get("1").getBike().equals(bike2));
            assertTrue(customer.getCart().getContents().get("6").getBike().equals(bike7));
        }
    }

    /*Testing Booking:
    * Need to check that the system can book using quotes in a cart
    * Need to check*/
    @Test
    @Order(4)
    void bookQuoteTest() {
        //first add  quotes to cart
        customer.AddQuoteToCart(curr_search.get("1"),2);
        customer.AddQuoteToCart(curr_search.get("0"), 1);
        customer.bookQuote(false); //first booking in system id will be 0

        assertNotNull(customer.system.findBooking((Integer) 0));
    }

}
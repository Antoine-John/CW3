package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.lang.System;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemTests {
    //Required Testing Variables
    private static BikeProvider oProvider;
    private static BikeProvider pProvider;
    private static BikeProvider uProvider;

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

    private static MultiDayDiscountPolicy pricePolicy;

    @BeforeAll
    static void oneTimeSetUp() {

        //Sets up test environment with bike providers and bikes
        oProvider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
        pProvider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
        uProvider = new BikeProvider("Smitty's Decrepit Hut ", new Location("EH16 5AY", "18 Holyrood Park Road"), "password");

        pricePolicy = new MultiDayDiscountPolicy();
        BikeType bikeType = new BikeType(1);
        pricePolicy.setDailyRentalPrice(bikeType, new BigDecimal(10));
        pProvider.setDailyRentalRates(bikeType);
        bikeType = new BikeType(0);
        pricePolicy.setDailyRentalPrice(bikeType, new BigDecimal(15));
        pProvider.setDailyRentalRates(bikeType);

        oProvider.system.reset();

        bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, oProvider); //Id=0
        bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, pProvider); //Id = 1...
        bike3 = new Bike(new BikeType(2), "AllTerrain Lad", Size.S, oProvider);
        bike4 = new Bike(new BikeType(3), "BeeEmEx", Size.M, pProvider);
        bike5 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.XXL, oProvider);
        bike6 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.L, pProvider);
        bike7 = new Bike(new BikeType(1), "HillBilly", Size.L, pProvider);
        bike8 = new Bike(new BikeType(2), "AllTerrain Lad", Size.XXS, oProvider);
        bike9 = new Bike(new BikeType(1), "BeeEmEx", Size.L, pProvider);
        bike10 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.M, oProvider);

        customer = new Customer("John Doe",
                "07293 987091",
                "JohnDoe@yahoo.com",
                new Location("EH8 4YU", "54 Brady Avenue"),
                "Password");

        //provider system state
        oProvider.system.addProvider(oProvider);
        oProvider.system.addProvider(pProvider);
        oProvider.system.addCustomer(customer);

        oProvider.system.addBike(bike1);
        oProvider.system.addBike(bike2);
        oProvider.system.addBike(bike3);
        oProvider.system.addBike(bike4);
        oProvider.system.addBike(bike5);
        oProvider.system.addBike(bike6);
        oProvider.system.addBike(bike7);
        oProvider.system.addBike(bike8);
        oProvider.system.addBike(bike9);
        oProvider.system.addBike(bike10);

        DateRange dateRange2 = new DateRange(LocalDate.of(2019, Month.NOVEMBER, 1), LocalDate.of(2019, Month.NOVEMBER, 10));
        //Change rental period of bike 9 to check that it is not added
        bike9.updateRentalPeriods(dateRange2);
        oProvider.newPartnership(pProvider.getAddress());
        //ensure 'system' is shared between provider and customer
        customer.system = oProvider.system;
        System.out.println(oProvider.system.getBikes().size());
    }

    @Test
    @Order(1)
    void requestQuotesTest() {
        //Request Quotes is called and the result is stored in curr_search
        DateRange dateRange = new DateRange(LocalDate.of(2019, Month.NOVEMBER, 1), LocalDate.of(2019, Month.NOVEMBER, 10));
        Location location = new Location("EH8 342", "x");
        boolean[] choices = {true, true, false, false, false};
        curr_search = customer.RequestQuotes(dateRange, location, choices, Size.L);
        //A new array list of keys is created used for adding all keys in curr_search
        ArrayList<String> keys = new ArrayList<String>();
        //Master set of keys to check against obtained keys
        String[] masterKeys = {"1", "5", "6"};
        //Check that the return size of list of quotes is correct
        assertEquals(3, curr_search.size());
        for (Map.Entry<String, Quote> entry : curr_search.entrySet()) {
            System.out.print("Current Key: ");
            System.out.println(entry.getKey());
            keys.add(entry.getKey());
        }
        Iterator<String> iter = keys.iterator();
        int idx = 0;
        while (iter.hasNext()) {
            //Check that all keys are accounted for
            assertTrue(masterKeys[idx].equals(iter.next()));
            idx++;
        }
    }
    @Test
    @Order(2)
    void addQuoteToCartTest() {
        //Try adding 2 quotes of the same type to cart
        assertFalse(curr_search.isEmpty());
        customer.AddQuoteToCart(curr_search.get("1"), 2);
        //Test
        assertNotNull(customer.getCart());
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
        //Try adding another quote to cart of different type
        assertTrue(!curr_search.isEmpty());
        customer.AddQuoteToCart(curr_search.get("5"), 1);
        //Test
        assertTrue(customer.getCart() != null);
        if (customer.getCart().getContents().isEmpty()) {
            System.out.println("Cart is empty booking failed");
        } else {
            assertTrue(customer.getCart().getContents().get("1").getBike().equals(bike2));
            assertTrue(customer.getCart().getContents().get("6").getBike().equals(bike7));
        }
    }

    @Test
    @Order(4)
    void bookQuoteTest() {
        //Book quote and ensure booking created is not null
        customer.bookQuote(false); //first booking in system id will be 0
        assertNotNull(customer.system.findBooking((Integer) 0));
    }

    @Test
    @Order(5)
    void bookingCostTest() {
        //check that the cost of booking is correct
        System.out.println(customer.system.findBooking(0));
        assertEquals(new BigDecimal(315), customer.system.findBooking(0).getTotalCost());
    }

    @Test
    @Order(6)
    void newPartnerShipTest() {
        //Check that a new partnership can be recorded in the system by using only one of the partners involved
        //Checks that original provider has the bike provider partner in its list of partners and vice versa
        assertTrue(oProvider.getPartners().contains(pProvider));
        assertTrue(oProvider.system.getProvider(pProvider.getAddress()).getPartners().contains(oProvider));
    }

    @Test
    @Order(7)
    void returnBikeToOriginalProviderPartnerTest() {
        //test that a booking has been registered as returned to partner
        assertNotNull(customer.system.findBooking((Integer) 0));
        resetBooking(oProvider.system.findBooking(0));
        oProvider.registerReturnBooking(0);
        //Check that the booking state has been changed to returned
        assertEquals(State.RETURNED, oProvider.system.findBooking(0).getBookingState());
        for (Map.Entry<String, Quote> entry: oProvider.system.findBooking(0).getQuotes().entrySet()) {
            //check that all the bikes in the booking have their status updated
            assertEquals(Status.WITHPARTNER, entry.getValue().getBike().getStatus());
        }
    }

    @Test
    @Order(8)
    void returnBikeToOriginalProviderTest() {
        //similar test to return to partner
        pProvider.system = oProvider.system;
        assertNotNull(customer.system.findBooking((Integer) 0));
        resetBooking(pProvider.system.findBooking(0));
        pProvider.registerReturnBooking(0);
        assertEquals(State.RETURNED, oProvider.system.findBooking(0).getBookingState());
        for (Map.Entry<String, Quote> entry: oProvider.system.findBooking(0).getQuotes().entrySet()) {
            assertEquals(Status.AVAILABLE, entry.getValue().getBike().getStatus());
        }
    }

    @Test
    @Order(9)
    void invalidBikeReturnTest() {
        //Checks that booking status doesn't change if a provider tries to return bikes that aren't theirs
        uProvider.system = oProvider.system;
        resetBooking(uProvider.system.findBooking(0));
        assertNotNull(customer.system.findBooking((Integer) 0));
        uProvider.registerReturnBooking(0);
        assertEquals(State.APPROVED, uProvider.system.findBooking(0).getBookingState());
    }

    //updates a booking to be reused in testing
    void resetBooking(Booking booking) {
        for (Map.Entry<String, Quote> entry:booking.getQuotes().entrySet()) {
            entry.getValue().getBike().UpdateBikeStatus(Status.RENTEDOUT);
        }
        booking.updateBookingState(State.APPROVED);
    }


}
package uk.ac.ed.bikerental;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

class TestMultiDayDiscountPolicy {

    private static int[] days;
    private static float[] discount;
    private static DateRange dateRange;

    private static BikeProvider pProvider;

    private static Bike bike1;
    private static Bike bike2;
    private static Bike bike3;
    private static Bike bike4;
    private static Bike bike5;

    private static Collection<Bike> collection;
    private static MultiDayDiscountPolicy pricePolicy;

    @BeforeAll
    static void oneTimeSetUp() {
        pProvider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");

        pricePolicy = new MultiDayDiscountPolicy();
        BikeType bikeType = new BikeType(1);
        pricePolicy.setDailyRentalPrice(bikeType, new BigDecimal(10));
        pProvider.setDailyRentalRates(bikeType);
        bikeType = new BikeType(0);
        pricePolicy.setDailyRentalPrice(bikeType, new BigDecimal(15));
        pProvider.setDailyRentalRates(bikeType);

        days = new int[]{1,3,7,14};
        discount = new float[]{0f, 0.05f, 0.1f, 0.15f};
        pricePolicy.setDaysDiscount(days, discount);
        dateRange = new DateRange(LocalDate.of(2018, 1, 7),
                LocalDate.of(2018, 1, 10));

        bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, pProvider); //Id=0
        bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, pProvider); //Id = 1...
        bike3 = new Bike(new BikeType(2), "AllTerrain Lad", Size.S, pProvider);
        bike4 = new Bike(new BikeType(3), "BeeEmEx", Size.M, pProvider);
        bike5 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.XXL, pProvider);
        collection = new ArrayList<Bike>();
        collection.add(bike1);
        collection.add(bike2);
        collection.add(bike3);
        collection.add(bike4);
        collection.add(bike5);
    }

    @Test
    void calculatePriceTest() {
        //Very basic test case for calculating price
        //checks known value against calculated value
        BigDecimal test = pricePolicy.calculatePrice(collection, dateRange);
        System.out.println(test);
        assertTrue(test.compareTo(new BigDecimal(71.25)) == 0);

    }

}

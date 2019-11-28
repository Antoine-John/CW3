package uk.ac.ed.bikerental;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

class TestMultiDayDiscountPolicy {

    private static MultiDayDiscountPolicy multDiscPol;
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

    @BeforeAll
    static void oneTimeSetUp() {
        multDiscPol = new MultiDayDiscountPolicy();
        days = new int[]{1,3,7,14};
        discount = new float[]{0f, 0.05f, 0.1f, 0.15f};
        multDiscPol.setDaysDiscount(days, discount);
        dateRange = new DateRange(LocalDate.of(2018, 1, 7),
                LocalDate.of(2018, 1, 10));

        bike1 = new Bike(new BikeType(0), "Li'l Road Warrior", Size.XS, new BigDecimal(10), pProvider); //Id=0
        bike2 = new Bike(new BikeType(1), "Mountain Mama", Size.L, new BigDecimal(30), pProvider); //Id = 1...
        bike3 = new Bike(new BikeType(2), "AllTerrain Lad", Size.S, new BigDecimal(20), pProvider);
        bike4 = new Bike(new BikeType(3), "BeeEmEx", Size.M, new BigDecimal(45), pProvider);
        bike5 = new Bike(new BikeType(4), "Beefy Boy Unicycle", Size.XXL, new BigDecimal(15), pProvider);
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
        BigDecimal test = multDiscPol.calculatePrice(collection, dateRange);
        assertTrue(test.compareTo(new BigDecimal(114.00)) == 0);

    }

}

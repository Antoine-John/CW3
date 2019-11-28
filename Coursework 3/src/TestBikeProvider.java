
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class TestBikeProvider {

	//Test Variables
	private static Customer customer;

	private static BikeProvider oProvider;
	private static BikeProvider pProvider;
	private static BikeProvider uProvider;

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

	private static Booking booking;

	private static HashMap<String, Quote> quotes;

	/*
	@BeforeAll
	private static void oneTimeSetUp() {
		oProvider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
		pProvider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
		uProvider = new BikeProvider("Smitty's Decrepit Hut ", new Location("EH16 5AY", "18 Holyrood Park Road"), "password");
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
				new Location("EH8 4YU", "54 Brady Avenue"),
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
		customer.system.getProvider(oProvider.getAddress()).newPartnership(pProvider.getAddress());
	}
	/*
	@BeforeEach
	private void setUp() {
		//booking = new Booking(customer, oProvider, quotes, true, null);
	}*/

	@Test
	private void newPartnerShipTest() {
		oProvider = new BikeProvider("Al's Bike Rental Shop", new Location("EH9 3BP", "53 Forresthill Avenue"), "password");
		pProvider = new BikeProvider("Capone's Bikes For Rent", new Location("EH10 2WM", "7 Roseburn Terrace"), "password");
		uProvider = new BikeProvider("Smitty's Decrepit Hut ", new Location("EH16 5AY", "18 Holyrood Park Road"), "password");
		assertTrue(oProvider.getPartners().contains(pProvider));
		assertTrue(pProvider.getPartners().contains(oProvider));
	}
}

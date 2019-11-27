import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;


public class ReturnBikeTest {
	
	static Customer testCustomer1 = new Customer(null, null, null, null, null);
	static BikeProvider testProvider1 = new BikeProvider("Mike's Bikes", new Location("EH4 9DG", "5 Peat Street"), "password");
	static BikeProvider testProvider2 = new BikeProvider("Michael's Cycles", new Location("EH5 7RD", "2 Node Road"), "drowssap");
	static BikeProvider testProvider3 = new BikeProvider("Buy Our Bikes", new Location("EH6 5QP", "9 Placeholder Avenue"), "123456");
	static Booking testBooking1 = new Booking(testCustomer1, testProvider1, new HashMap<String,Quote>(), false, LocalDate.of(1, 2, 20));
	static Booking testBooking2 = new Booking(testCustomer1, testProvider1, new HashMap<String,Quote>(), false, LocalDate.of(3, 4, 20));
	static Booking testBooking3 = new Booking(testCustomer1, testProvider1, new HashMap<String,Quote>(), false, LocalDate.of(5, 6, 20));
	

	public static void main() {
		testProvider1.NewPartnership(testProvider2);
		testBooking1.state = State.COLLECTED;
		testBooking2.state = State.COLLECTED;
		testBooking3.state = State.COLLECTED;
	}
	
	@Test
	void ReturnToProviderTest() {
		testProvider1.registerReturnBooking(testBooking1);
		assertTrue(testBooking1.getBookingState() == State.RETURNED);
	}
	
	@Test
	void ReturnToPartnerTest() {
		testProvider2.registerReturnBooking(testBooking2);
		assertTrue(testBooking2.getBookingState() == State.RETURNED);
	}
	
	@Test
	void ReturnWhenNotAffiliatedTest() {
		testProvider3.registerReturnBooking(testBooking3);
		assertFalse(testBooking3.getBookingState() == State.RETURNED);
	}
}

//import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public class Booking {
	int bookingid;
	Customer customer;
	BikeProvider provider;
	Collection<Quote> quotes;
	boolean collectionType;
	LocalDate bookingDate;
	State state;
	
	public Booking (Customer customer, BikeProvider provider, Collection<Quote> quotes, boolean collectionType, LocalDate bookingDate) {
		//generate unique booking ID
		this.customer = customer;
		this.provider = provider;
		this.quotes = quotes;
		this.collectionType = collectionType;
		this.bookingDate = bookingDate;
	}
	
	public void updateBookingState (State state) {
		this.state = state;
	}
}

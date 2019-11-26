//import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

public class Booking {
	Integer bookingid;
	Customer customer;
	private BikeProvider provider;
	private Collection<Quote> quotes;
	boolean collectionType;
	LocalDate bookingDate;
	private State state;
	
	public Collection<Quote> getQuotes() {
		return this.quotes;
	}
	
	public BikeProvider getProvider() {
		return this.provider;
	}
	
	public State getBookingState() {
		return this.state;
	}
	
	public Booking (Customer customer, BikeProvider provider, Collection<Quote> quotes, boolean collectionType, LocalDate bookingDate) {
		//generate unique booking ID
		this.customer = customer;
		this.provider = provider;
		this.quotes = quotes;
		this.collectionType = collectionType;
		this.bookingDate = bookingDate;
		this.state = State.PENDING;
	}

	public LocalDate getCollectionDate() {
		Iterator<Quote> iter = quotes.iterator();
		return iter.next().getRentalPeriod().getStart();
	}

	public void sendToProvider() {
		//System sends booking info to provider
	}
	
	public void updateBookingState (State state) {
		this.state = state;
	}
}

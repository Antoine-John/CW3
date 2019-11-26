//import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Booking {
	Integer bookingid;
	Customer customer;
	private BikeProvider provider;
	private HashMap<String,Quote> quotes;
	boolean collectionType;
	LocalDate bookingDate;
	private State state;
	
	public HashMap<String, Quote> getQuotes() {
		return this.quotes;
	}
	
	public BikeProvider getProvider() {
		return this.provider;
	}
	
	public State getBookingState() {
		return this.state;
	}
	
	public Booking (Customer customer, BikeProvider provider, HashMap<String,Quote> quotes, boolean collectionType, LocalDate bookingDate) {
		//generate unique booking ID
		this.customer = customer;
		this.provider = provider;
		this.quotes = quotes;
		this.collectionType = collectionType;
		this.bookingDate = bookingDate;
		this.state = State.PENDING;
	}

	public LocalDate getCollectionDate() {
		for (Map.Entry<String, Quote> entry: quotes.entrySet()) {
			return entry.getValue().getRentalPeriod().getStart();
		}
		return null;
	}

	public void sendToProvider() {
		//System sends booking info to provider
	}
	
	public void updateBookingState (State state) {
		this.state = state;
	}

	public Integer getID() {
		return this.bookingid;
	}
}

package uk.ac.ed.bikerental;//import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Booking {
	//Booking Attributes
	Customer customer;
	private BikeProvider provider;
	private HashMap<String,Quote> quotes;
	boolean collectionType;
	LocalDate bookingDate;
	private State state;
	private BigDecimal totalCost;

	//Booking Constructor
	public Booking (Customer customer, BikeProvider provider, HashMap<String,Quote> quotes, boolean collectionType, LocalDate bookingDate, BigDecimal totalCost) {
		this.customer = customer;
		this.provider = provider;
		this.quotes = quotes;
		this.collectionType = collectionType;
		this.bookingDate = bookingDate;
		this.totalCost = totalCost;
		this.state = State.PENDING;
	}

	//Getters/Setters
	public HashMap<String, Quote> getQuotes() {
		return this.quotes;
	}
	
	public BikeProvider getProvider() {
		return this.provider;
	}
	
	public State getBookingState() {
		return this.state;
	}

	public BigDecimal getTotalCost(){
		return this.totalCost;
	}

	public LocalDate getCollectionDate() {
		for (Map.Entry<String, Quote> entry: quotes.entrySet()) {
			return entry.getValue().getRentalPeriod().getStart();
		}
		return null;
	}

	//Booking methods
	public void sendToProvider() {
		//System sends booking info to provider
	}
	
	public void updateBookingState (State state) {
		this.state = state;
	}

	//For printing booking
	public String toString() {
		String returnString = "Booking:\nCustomer: ";
		returnString += this.customer.getEmail();
		returnString += "\nProvider: ";
		returnString += this.provider.name;
		returnString+= "\nAll Quotes:";
		for (Map.Entry<String,Quote> entry: this.quotes.entrySet()) {
			returnString += entry.getValue().toString();
		}
		returnString += "\nCollection Type:";
		if (this.collectionType) {
			returnString += " Provider Will Deliver";
		} else {
			returnString += " Customer Will Pick Up";
		}
		returnString += "\nDate of Booking: ";
		returnString += this.bookingDate.toString();
		returnString += "\nBooking State: ";
		returnString += this.state.toString();
		return returnString;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Booking booking = (Booking) o;
		return this.customer.equals(booking.customer) &&
				this.provider.equals(booking.getProvider()) &&
				this.quotes.equals(booking.getQuotes()) &&
				(this.collectionType == booking.collectionType) &&
				this.bookingDate.equals(booking.getCollectionDate()) &&
				this.state.equals(booking.getBookingState());
	}
}

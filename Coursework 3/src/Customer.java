import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Customer {
	
	String name;
	String contactNumber;
	String emailAddress;
	Location address;
	String password;
	Collection<Booking> activeBookings;
	Collection<Quote> cart;
	
	public Customer(String name, String contactNumber, String emailAddress, Location address, String password) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.password = password;
	}

	void UpdateAddress(String postcode, String addrLn){
		this.address = new Location(postcode, addrLn);
	}
	
	Collection<Quote> RequestQuotes(DateRange daterange, Location location, boolean[] types, Size size) {
		Collection<Quote> searchResults = null;
		Bike[] allBikes = null;

		searchResults = SearchQuotes(allBikes, daterange, location, types, size);
		if (searchResults.size() == 0) {
			searchResults = SearchQuotes(allBikes, daterange.ExpandRange(daterange), location, types, size);
		}
		
		return searchResults;
	}
	
	Collection<Quote> SearchQuotes(Bike[] allBikes, DateRange daterange, Location location, boolean[] types, Size size){
		Collection<Quote> searchResults = null;
		for (int i = 0; i < allBikes.length; i++) {
			Bike currentBike = allBikes[i];
			if (location.IsNearTo(currentBike.getProvider().address) == true && size == currentBike.getSize() && currentBike.dateClashCheck(daterange) == true && currentBike.typeMatches(types) == true) {
				searchResults.add(new Quote(currentBike, daterange));
			}
		}
		return searchResults;
	}

	public void AddQuoteToCart(Quote quote, int quantity) {
		
	}
	
	public void RemoveQuoteFromCart(int something) {
		
	}
	
	public void BookQuote(boolean collectType) {
		Iterator<Quote> iter = cart.iterator();
		LocalDate bookingDate = LocalDate.now();
		Booking newBooking = new Booking(this, iter.next().getBike().getProvider(), cart, collectType, bookingDate);
		this.activeBookings.add(newBooking);
		//need to notify provider
		if (collectType) {
			//BikeDeliverable deliverable = new BikeDeliverable(newBooking);
			
		}
		//empty the cart
	}
}

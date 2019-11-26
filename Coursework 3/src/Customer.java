import java.time.LocalDate;
import java.util.*;

public class Customer {

	private String name;
	private String contactNumber;
	private String emailAddress;
	private Location address;
	private String password;
	private Collection<Booking> activeBookings;
	private Cart cart = new Cart();

	MockSystem system = new MockSystem();
	
	public Customer(String name, String contactNumber, String emailAddress, Location address, String password) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.password = password;
	}

	void UpdateAddress(String postcode, String address) {
		this.address = new Location(postcode, address);
	}
	
	HashMap<String, Quote> RequestQuotes(DateRange daterange, Location location, boolean[] types, Size size) {
		HashMap<String, Quote> searchResults = null;
		HashMap<String, Bike> allBikes = system.getBikes();

		searchResults = SearchQuotes(allBikes, daterange, location, types, size);
		if (searchResults.size() == 0) {
			searchResults = SearchQuotes(allBikes, daterange.ExpandRange(), location, types, size);
		}
		
		return searchResults;
	}
	
	HashMap<String, Quote> SearchQuotes(HashMap<String, Bike> allBikes, DateRange daterange, Location location, boolean[] types, Size size){
		HashMap<String, Quote> searchResults = null;
		for (Map.Entry<String, Bike> entry: allBikes.entrySet()) {
			Bike currentBike = entry.getValue();
			if (location.IsNearTo(currentBike.getProvider().getAddress()) && size == currentBike.getSize() && currentBike.dateClashCheck(daterange) && currentBike.typeMatches(types)) {
				searchResults.put(entry.getKey(), new Quote(currentBike, daterange));
			}
		}
		return searchResults;
	}

	public void AddQuoteToCart(Quote quote, int quantity) {
		
	}
	
	public void RemoveQuoteFromCart(int something) {
		
	}
	
	public void BookQuote(boolean collect) {
		//When a quote is booked you need to update the DateRange List in bikes
		LocalDate bookingDate = LocalDate.now();
		Booking newBooking = new Booking(this, cart.getProvider(), cart.getContents(), collect, bookingDate);
		this.activeBookings.add(newBooking);
		system.addBooking(newBooking);
		newBooking.sendToProvider();
		for (Map.Entry<String, Quote> entry: cart.getContents().entrySet()) {
			system.findBike(entry.getKey()).updateRentalPeriods(entry.getValue().getRentalPeriod());
		}

		//need to notify provider
		if (collect) {
			//BikeDeliverable deliverable = new BikeDeliverable(newBooking);
			DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
			DeliveryService newDelivery = newFactory.getDeliveryService();
			Deliverable newDeliverable = new BikeDeliverable(newBooking);
			newDelivery.scheduleDelivery(newDeliverable, newBooking.getProvider().getAddress(), address, newBooking.getCollectionDate());
			//Need to change booking date to actual pickup date not date of booking
		}
		//empty the cart
	}
}

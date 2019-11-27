import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.lang.System;

public class Customer {

	private String name;
	private String contactNumber;
	private String emailAddress;
	private Location address;
	private String password;
	private Collection<Booking> activeBookings;
	private Cart cart = new Cart();
	private HashMap<String, Quote> tempSearch;

	MockSystem system = new MockSystem();

	//Used for testing
	public void addBikesAndProviders(BikeProvider[] providers, Bike[] bikes) {
		for (BikeProvider provider : providers) {
			system.addProvider(provider);
		}

		for (Bike bike : bikes) {
			system.addBike(bike);
		}
		//ideally this takes a list of bikes/providers and then adds to system accordingly
	}

	public String getEmail() {
		return this.emailAddress;
	}
	
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
		if (searchResults == null) {
			searchResults = SearchQuotes(allBikes, daterange.ExpandRange(), location, types, size);
		}

		tempSearch = searchResults;
		return searchResults;
	}
	
	HashMap<String, Quote> SearchQuotes(HashMap<String, Bike> allBikes, DateRange daterange, Location location, boolean[] types, Size size){
		HashMap<String, Quote> searchResults = new HashMap<String, Quote>();
		for (Map.Entry<String, Bike> entry: allBikes.entrySet()) {
			Bike currentBike = entry.getValue();
			if (location.IsNearTo(currentBike.getProvider().getAddress()) && size.equals(currentBike.getSize()) && currentBike.dateClashCheck(daterange) && currentBike.typeMatches(types)) {
				searchResults.put(entry.getKey(), new Quote(currentBike, daterange));
			}
		}
		return searchResults;
	}

	public void AddQuoteToCart(Quote quote, int quantity) {
		for (int i = 0; i < quantity; i++) {
			for (Map.Entry<String,Quote> entry: tempSearch.entrySet()) {
				if (entry.getValue().equals(quote)) {
					cart.add(entry.getKey(), entry.getValue());
					tempSearch.remove(entry.getKey());
					break;
				}
			}
		}
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

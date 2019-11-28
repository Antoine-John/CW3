import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.lang.System;

public class Customer {
	//Customer Attributes
	private String name;
	private String contactNumber;
	private String emailAddress;
	private Location address;
	private String password;
	private Collection<Booking> activeBookings;

	//Used for search/booking
	private Cart cart;
	private HashMap<String, Quote> tempSearch;

	//Mock system to get bikes/providers/bookings
	MockSystem system = new MockSystem();

	//Customer constructor
	public Customer(String name, String contactNumber, String emailAddress, Location address, String password) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.password = password;
		this.tempSearch = new HashMap<String, Quote>();
		this.cart = new Cart();
		this.activeBookings = new ArrayList<Booking>();
	}

	//Getters/Setters
	public Cart getCart() {
		return this.cart;
	}
	public String getEmail() {
		return this.emailAddress;
	}
	public void UpdateAddress(String postcode, String address) {
		this.address = new Location(postcode, address);
	}
	public void addActiveBooking(Booking booking) {
		this.activeBookings.add(booking);
	}
	public void removeActiveBooking(Booking booking) {
		Iterator<Booking> iter = activeBookings.iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(booking)) {
				iter.remove();
				break;
			}
		}
	}

	//Customer methods
	public HashMap<String, Quote> RequestQuotes(DateRange daterange, Location location, boolean[] types, Size size) {
		HashMap<String, Quote> searchResults = null;
		HashMap<String, Bike> allBikes = system.getBikes();

		searchResults = SearchQuotes(allBikes, daterange, location, types, size);
		if (searchResults == null) {
			searchResults = SearchQuotes(allBikes, daterange.ExpandRange(), location, types, size);
		}

		tempSearch = searchResults;
		return searchResults;
	}
	
	private HashMap<String, Quote> SearchQuotes(HashMap<String, Bike> allBikes, DateRange daterange, Location location, boolean[] types, Size size){
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
	
	public void RemoveQuoteFromCart(String bikeID) {
		Quote toRemove = cart.getContents().get(bikeID);
		cart.remove(bikeID);
		/* Once bike is removed from cart we can add it back to the temp search result in case
		the customer changes their mind and wants to add the quote back to the cart*/
		tempSearch.put(bikeID, toRemove);
	}
	
	public void bookQuote(boolean collect) {
		//When a quote is booked you need to update the DateRange List in bikes
		LocalDate bookingDate = LocalDate.now();
		Booking newBooking = new Booking(this, cart.getProvider(), cart.getContents(), collect, bookingDate, cart.getCartCost());
		this.activeBookings.add(newBooking);
		system.addBooking(newBooking);
		newBooking.sendToProvider();
		for (Map.Entry<String, Quote> entry: cart.getContents().entrySet()) {
			Bike currBike = system.findBike(entry.getKey());
			currBike.updateRentalPeriods(entry.getValue().getRentalPeriod());
			currBike.UpdateBikeStatus(Status.RENTEDOUT);
		}

		//need to notify provider
		newBooking.sendToProvider();

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

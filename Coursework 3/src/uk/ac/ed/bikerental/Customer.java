package uk.ac.ed.bikerental;

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
		Iterator<Booking> itr = activeBookings.iterator();
		while (itr.hasNext()) {
			if (itr.next().equals(booking)) {
				itr.remove();
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
			if (location.isNearTo(currentBike.getProvider().getAddress()) && size.equals(currentBike.getSize()) && currentBike.dateClashCheck(daterange) && currentBike.typeMatches(types)) {
				searchResults.put(entry.getKey(), new Quote(currentBike, daterange));
			}
		}
		return searchResults;
	}

	public void AddQuoteToCart(Quote quote, int quantity) {
		for (int i = 0; i < quantity; i++) {
			//for however many quotes that are the same...
			for (Map.Entry<String,Quote> entry: tempSearch.entrySet()) {
				//iterate through available bikes as determined by current search
				if (entry.getValue().equals(quote)) {
					//add to cart if quote exists in current search
					cart.add(entry.getKey(), entry.getValue());
					//remove from current search to not accidentally add the same quote multiple times
					tempSearch.remove(entry.getKey());
					break;
				}
			}
		}
	}
	
	public void RemoveQuoteFromCart(String bikeID) {
		//Customer changes their mind and removes a quote
		Quote toRemove = cart.getContents().get(bikeID);
		cart.remove(bikeID);
		/* Once bike is removed from cart we can add it back to the temp search result in case
		the customer changes their mind and wants to add the quote back to the cart*/
		tempSearch.put(bikeID, toRemove);
	}
	
	public void bookQuote(boolean collect) {

		LocalDate bookingDate = LocalDate.now();
		Booking newBooking = new Booking(this, cart.getProvider(), cart.getContents(), collect, bookingDate, cart.getCartCost());
		this.activeBookings.add(newBooking);
		system.addBooking(newBooking);
		newBooking.sendToProvider();
		for (Map.Entry<String, Quote> entry: cart.getContents().entrySet()) {
			Bike currBike = system.findBike(entry.getKey());
			//When a quote is booked you need to update the DateRange List in bikes
			currBike.updateRentalPeriods(entry.getValue().getRentalPeriod());
			//Update bike status
			currBike.UpdateBikeStatus(Status.RENTEDOUT);
		}

		//need to notify provider
		newBooking.sendToProvider();

		if (collect) {
			DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
			DeliveryService newDelivery = newFactory.getDeliveryService();
			Deliverable newDeliverable = new BikeDeliverable(newBooking);
			newDelivery.scheduleDelivery(newDeliverable, newBooking.getProvider().getAddress(), address, newBooking.getCollectionDate());
			//Need to change booking date to actual pickup date not date of booking
		}
		//once booking is done customer can start a new cart
		cart = new Cart();
	}
}

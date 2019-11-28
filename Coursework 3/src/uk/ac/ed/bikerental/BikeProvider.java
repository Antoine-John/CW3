package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.*;

public class BikeProvider {
	//Provider Attributes
	String name;
	private Location address;
	private Collection<BikeProvider> partners;
	BigDecimal depositRate;
	private String password;

	//Provider constructor
	public BikeProvider(String name, Location address, String password) {
		this.name = name;
		this.address = address;
		this.password = password;
		this.partners = new ArrayList<BikeProvider>();
	}

	//MockSystem will be used for testing and will contain info on customers, booking etc
	MockSystem system = new MockSystem();
	//Create a logger for warnings
	private Logger logger = Logger.getLogger(MultiDayDiscountPolicy.class.getName());

	//Getters/Setters
	public Location getAddress() {
		return this.address;
	}
	
	public Collection<BikeProvider> getPartners() {
		return this.partners;
	}

	public void setDepositRate(BigDecimal depositRate) {
		this.depositRate = depositRate;
	}

	//methods
	public void newPartnership(Location partnerLocation) {
		//add partner to this provider
		this.partners.add(system.getProvider(partnerLocation));
		//find partner in system and add to their partners
		system.getProvider(partnerLocation).getPartners().add(this);
	}

	public void registerReturnBooking(Integer ID) {
		Booking booking = system.findBooking(ID);
		//Check if bike is being returned to the original provider or a partner
		if (booking.getProvider().getAddress().equals(this.address)) {
			registerReturnProvider(ID, booking);
		} else if (this.partners.contains(booking.getProvider())) {
			registerReturnPartner(ID, booking);
		} else {
			logger.setLevel(Level.WARNING);
			logger.warning("You are not authorised to update this booking.");
		}
	}

	public void registerReturnProvider(Integer ID, Booking booking) {
		//Bike is returned to original provider. Status of bikes and booking state is update
		for (Map.Entry<String, Quote> entry: booking.getQuotes().entrySet()) {
			Bike currentBike = system.findBike(entry.getKey());
			currentBike.UpdateBikeStatus(Status.AVAILABLE);
			currentBike.removeRentalPeriods(entry.getValue().getRentalPeriod());
		}
		system.findBooking(ID).updateBookingState(State.RETURNED);
		//When a bike is returned we should also want to remove any rental periods that are no longer important from the bike collection

	}
	
	public void registerReturnPartner(Integer ID, Booking booking) {
		//Bike is returned to partner of original provider. Status of bikes and booking state is updated
		//Bike is schedule for delivery back to original provider.
		for (Map.Entry<String, Quote> entry: booking.getQuotes().entrySet()) {
			Bike currentBike = system.findBike(entry.getKey());
			//System.out.println(currentBike);
			currentBike.UpdateBikeStatus(Status.WITHPARTNER);
			currentBike.removeRentalPeriods(entry.getValue().getRentalPeriod());
		}
		system.findBooking(ID).updateBookingState(State.RETURNED);

		//Make delivery of bikes back to original provider
		DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
		newFactory.setupMockDeliveryService();
		DeliveryService newDelivery = newFactory.getDeliveryService();
		Deliverable newDeliverable = new BikeDeliverable(booking);
		//For simplicity we assume the date of return to original provider is the current date
		LocalDate returnDate = LocalDate.now();
		newDelivery.scheduleDelivery(newDeliverable, address, booking.getProvider().getAddress(), returnDate);
	}
}

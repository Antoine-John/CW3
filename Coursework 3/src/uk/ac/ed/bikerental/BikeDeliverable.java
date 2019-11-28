package uk.ac.ed.bikerental;

import java.util.*;

public class BikeDeliverable implements Deliverable{
	
	//get booking
	Booking booking;
	Collection<Bike> bikes;
	
	public BikeDeliverable (Booking booking) {
		this.booking = booking;
		
		HashMap<String, Bike> bikes = new HashMap<String, Bike>();
		for (Map.Entry<String,Quote> entry: booking.getQuotes().entrySet()) {
			bikes.put(entry.getKey(), entry.getValue().getBike());
		}
	}
	
	public void onPickup() {
		//notify customer and provider
		booking.updateBookingState(State.ON_THE_WAY);
		updateBikeStatus(Status.INDELIVERY);
	}
	
	public void onDropoff() {
		booking.updateBookingState(State.COLLECTED);
		updateBikeStatus(Status.RENTEDOUT);
	}
	
	private void updateBikeStatus(Status status) {
		Iterator<Bike> iter = bikes.iterator();
		while (iter.hasNext()) {
			iter.next().UpdateBikeStatus(status);
		}
	}
}

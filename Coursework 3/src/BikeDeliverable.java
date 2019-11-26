import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BikeDeliverable implements Deliverable{
	
	//get booking
	Booking booking;
	Collection<Bike> bikes;
	
	public BikeDeliverable (Booking booking) {
		
		this.booking = booking;
		
		Collection<Bike> bikes = new ArrayList<Bike>();
		Iterator<Quote> iter = booking.quotes.iterator();
		while (iter.hasNext()) {
			bikes.add(iter.next().getBike());
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

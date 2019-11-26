import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

public class BikeProvider {
	String name;
	private Location address;
	Collection<BikeProvider> partners;
	BigDecimal depositRate;
	String password;
	
	public Location getAddress() {
		return this.address;
	}
	
	public BikeProvider(String name, Location address, String password) {
		this.name = name;
		this.address = address;
		this.password = password;
	}
	
	public void NewPartnership(BikeProvider partner) {
		partners.add(partner);
	}

	public void registerReturnProvider(Booking booking) {
		//Bike is returned to original provider. Status of bikes and booking state is updated
		Iterator<Quote> iter = booking.getQuotes().iterator();
		while (iter.hasNext()) {
			iter.next().getBike().UpdateBikeStatus(Status.AVAILABLE);
		}
		booking.updateBookingState(State.RETURNED);
	}
	
	public void registerReturnPartner(Booking booking) {
		//Bike is returned to partner of original provider. Status of bikes and booking state is updated
		//Bike is schedule for delivery back to original provider.
		Iterator<Quote> iter = booking.getQuotes().iterator();
		while (iter.hasNext()) {
			iter.next().getBike().UpdateBikeStatus(Status.WITHPARTNER);
		}
		booking.updateBookingState(State.RETURNED);
		DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
		DeliveryService newDelivery = newFactory.getDeliveryService();
		Deliverable newDeliverable = new BikeDeliverable(booking);
		//For simplicity we assume the date of return to original provider is the current date
		LocalDate returnDate = LocalDate.now();
		newDelivery.scheduleDelivery(newDeliverable, address, booking.getProvider().getAddress(), returnDate);
	}
}

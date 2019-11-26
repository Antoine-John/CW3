import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BikeProvider {
	String name;
	private Location address;
	Collection<BikeProvider> partners;
	BigDecimal depositRate;
	private String password;

	MockSystem system = new MockSystem();

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
		//Bike is returned to original provider. Status of bikes and booking state is update
		for (Map.Entry<String, Quote> entry: booking.getQuotes().entrySet()) {
			Bike currentBike = system.findBike(entry.getKey());
			currentBike.UpdateBikeStatus(Status.AVAILABLE);
			currentBike.removeRentalPeriods(entry.getValue().getRentalPeriod());
		}
		system.findBooking(booking.getID()).updateBookingState(State.RETURNED);
		//When a bike is returned we should also want to remove any rental periods that are no longer important from the bike collection

	}
	
	public void registerReturnPartner(Booking booking) {
		//Bike is returned to partner of original provider. Status of bikes and booking state is updated
		//Bike is schedule for delivery back to original provider.
		for (Map.Entry<String, Quote> entry: booking.getQuotes().entrySet()) {
			Bike currentBike = system.findBike(entry.getKey());
			currentBike.UpdateBikeStatus(Status.WITHPARTNER);
			currentBike.removeRentalPeriods(entry.getValue().getRentalPeriod());
		}
		system.findBooking(booking.getID()).updateBookingState(State.RETURNED);
		DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
		DeliveryService newDelivery = newFactory.getDeliveryService();
		Deliverable newDeliverable = new BikeDeliverable(booking);
		//For simplicity we assume the date of return to original provider is the current date
		LocalDate returnDate = LocalDate.now();
		newDelivery.scheduleDelivery(newDeliverable, address, booking.getProvider().getAddress(), returnDate);
	}
}

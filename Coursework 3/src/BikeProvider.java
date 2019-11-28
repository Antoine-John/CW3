import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.*;

public class BikeProvider {
	String name;
	private Location address;
	Collection<BikeProvider> partners;
	BigDecimal depositRate;
	private String password;

	MockSystem system = new MockSystem();
	private Logger logger = Logger.getLogger(MultiDayDiscountPolicy.class.getName());

	public Location getAddress() {
		return this.address;
	}
	
	public BikeProvider(String name, Location address, String password) {
		this.name = name;
		this.address = address;
		this.password = password;
		this.partners = new ArrayList<BikeProvider>();
	}
	
	public Collection<BikeProvider> getPartners() {
		return this.partners;
	}

	public void newPartnership(Location partnerLocation) {
		this.partners.add(system.getProvider(partnerLocation));
		system.getProvider(partnerLocation).getPartners().add(this);
	}

	public void registerReturnBooking(Integer ID) {
		Booking booking = system.findBooking(ID);
		if (booking.getProvider().getAddress().equals(this.address)) {
			//System.out.println("1");
			registerReturnProvider(ID, booking);
		} else if (this.partners.contains(booking.getProvider())) {
			//System.out.println("2");
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

		DeliveryServiceFactory newFactory = new DeliveryServiceFactory();
		DeliveryService newDelivery = newFactory.getDeliveryService();
		Deliverable newDeliverable = new BikeDeliverable(booking);
		//For simplicity we assume the date of return to original provider is the current date
		LocalDate returnDate = LocalDate.now();
		newDelivery.scheduleDelivery(newDeliverable, address, booking.getProvider().getAddress(), returnDate);
	}
}

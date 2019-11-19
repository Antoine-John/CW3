import java.util.Collection;

public class Customer {
	String name;
	String contactNumber;
	String emailAddress;
	String deliveryAddress;
	String deliveryPostcode;
	Collection<Booking> activeBookings;
	Collection<Quote> cart;
}

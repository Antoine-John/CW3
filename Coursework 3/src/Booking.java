import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class Booking {
	int bookingid;
	Customer customer;
	BikeProvider provider;
	Collection<Bike> bikes;
	boolean collectionType;
	Date bookingDate;
	DateRange rentPeriod;
	BigDecimal totalCost;
	BigDecimal totalDeposit;
	State state;
}

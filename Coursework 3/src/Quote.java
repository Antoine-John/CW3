import java.math.BigDecimal;

public class Quote {
	private Bike bike;
	DateRange rentalPeriod;
	BigDecimal totalCost;
	BigDecimal totalDeposit;
	
	public Quote(Bike currentbike, DateRange daterange) {
		bike = currentbike;
		rentalPeriod = daterange;
		totalCost = bike.getDailyRentalRate().multiply(new BigDecimal(daterange.getDuration()));
		totalDeposit = null;
	}
	
	public Bike getBike() {
		return this.bike;
	}
}

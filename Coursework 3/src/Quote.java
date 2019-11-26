import java.math.BigDecimal;

public class Quote {
	private Bike bike;
	private DateRange rentalPeriod;
	BigDecimal totalCost;
	BigDecimal totalDeposit;
	
	public Quote(Bike currentbike, DateRange daterange) {
		bike = currentbike;
		rentalPeriod = daterange;
		totalCost = bike.getDailyRentalRate().multiply(new BigDecimal(daterange.getDuration()));
		totalDeposit = null;
	}

	public DateRange getRentalPeriod() {
		return this.rentalPeriod;
	}
	
	public Bike getBike() {
		return this.bike;
	}
}

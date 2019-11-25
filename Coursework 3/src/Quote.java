import java.math.BigDecimal;

public class Quote {
	Bike bike;
	DateRange rentalPeriod;
	BigDecimal totalCost;
	BigDecimal totalDeposit;
	
	public Quote(Bike currentbike, DateRange daterange) {
		bike = currentbike;
		rentalPeriod = daterange;
		totalCost = bike.dailyRentalRate.multiply(new BigDecimal(daterange.getDuration()));
		totalDeposit = null;
	}
}

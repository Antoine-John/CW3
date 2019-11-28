package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Quote {
	//Attributes
	private Bike bike;
	private DateRange rentalPeriod;
	BigDecimal totalCost;
	BigDecimal totalDeposit;

	//Constructor
	public Quote(Bike currentBike, DateRange daterange) {
		bike = currentBike;
		rentalPeriod = daterange;
		totalCost = bike.getBikeType().dailyRentalRate.multiply(new BigDecimal(daterange.getDuration()));
		totalDeposit = null;
	}

	//getters/setters
	public DateRange getRentalPeriod() {
		return this.rentalPeriod;
	}
	
	public Bike getBike() {
		return this.bike;
	}

	public BigDecimal getTotalCost() { return this.totalCost;}

	//make quote easily printable
	public String toString() {
		String returnString = "\n-----------------------------------------";
		returnString += bike.toString();
		returnString += "\nTotal Cost: ";
		returnString += new DecimalFormat("#0.##").format(totalCost);
		returnString += "\n-----------------------------------------\n";
		return returnString;
	}


	@Override
	public boolean equals (Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Quote quote = (Quote) o;
		return this.bike.equals(quote.getBike()) &&
				this.rentalPeriod.equals(quote.getRentalPeriod()) &&
				this.totalCost.equals(quote.getTotalCost());
	}
}

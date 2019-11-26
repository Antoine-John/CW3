//import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
//import java.io.IOException;
import java.util.logging.*;

public class MultiDayDiscountPolicy implements PricingPolicy {
	public int[] days = {0}; //the days are the lower bound for each day interval
	public float[] discount = {0};
	
	Logger logger = Logger.getLogger(MultiDayDiscountPolicy.class.getName());

	public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange date) {
		Iterator<Bike> bikesItr = bikes.iterator(); //iterate through bike collection
		BigDecimal price = new BigDecimal(0); //return this as the total price of bikes with discount accounted for
		float discount = getDiscountRate(date);
		while (bikesItr.hasNext()) {
			BigDecimal temp = bikesItr.next().getPrice().multiply(new BigDecimal(discount)); //update price
			price = price.add(temp);
		}
		return price; 
	}
	
	public float getDiscountRate(DateRange date) {
		int duration = date.getDuration(); //duration in days of booking
		int idx = 0; //which discount to get
		for (int i = 0; i < days.length; i++) { //iterate through days to see which discount applies
			if (duration < days[i]) {
				idx = i-1; //update idx
				break;
			}
			idx = i; //update idx
		}
		return this.discount[idx];
	}

	public void setDailyRentalPrice(BikeType bikeType, BigDecimal price) {
		
	}

	public void setDaysDiscount(int[] days, float[] discount) {
		boolean validDayDiscount = true;
		//days set need to be in order
		for (int i = 0; i < days.length; i++) {
			if (!(days[i] < days[i+1])) {
				validDayDiscount = false;
				logger.setLevel(Level.WARNING);
				logger.warning("Warning: Days are not in ascending order, discount policy not updated.");
			}
		}
		//size of day and discount need to be the same
		if (days.length != discount.length) {
			validDayDiscount = false;
			logger.setLevel(Level.WARNING);
			logger.warning("Warning: Different number of entries for discount rates and days, discount policy not updated.");
		}
		if (validDayDiscount) {
			this.days = days;
			this.discount =  discount;
		}
	}
}

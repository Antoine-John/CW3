import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

public class Bike {
	
	BikeType type;
	String model;
	Size size;
	BigDecimal dailyRentalRate;
	BikeProvider provider;
	Collection<DateRange> rentPeriods;
	Status status;
	BigDecimal replacementValue;
	
	public BigDecimal getPrice () {
		return this.dailyRentalRate;
	}

	public void setPrice (BigDecimal price) {
		this.dailyRentalRate = price;
	}

	public boolean dateClashCheck(DateRange dateRange) {
		
		Iterator<DateRange> iter = rentPeriods.iterator();
		while (iter.hasNext()) {
			if (dateRange.overlaps(iter.next())) {
				return false;
			}
		}
		return true;
	}

	public boolean typeMatches(boolean[] types) {
		int toCheck;
		switch (type.type) {
			case "Road": toCheck = 0;
						break;
			case "Mountain": toCheck = 1;
						break;
			case "Hybrid": toCheck = 2;
						break;
			case "BMX": toCheck = 3;
						break;
			case "Other": toCheck = 4;
						break;
			default:	return false;
		}
		return types[toCheck];
	}
	
	public void UpdateBikeStatus(Status newstatus) {
		this.status = newstatus;
	}
	
} 	
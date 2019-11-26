import java.math.BigDecimal;
import java.util.*;
import java.lang.System;

public class Bike {
	
	private BikeType type;
	private String model;
	private Size size;
	private BigDecimal dailyRentalRate;
	private BikeProvider provider;
	private Collection<DateRange> rentPeriods;
	private Status status;
	
	public Bike(BikeType type, String model, Size size, BigDecimal dailyRentalRate, BikeProvider provider) {
		this.type = type;
		this.model = model;
		this.size = size;
		this.dailyRentalRate = dailyRentalRate;
		this.provider = provider;
		this.rentPeriods = new ArrayList<DateRange>();
		this.status = status.AVAILABLE;
	}
	
	public BikeProvider getProvider() {
		return this.provider;
	}
	
	public BigDecimal getPrice() {
		return this.dailyRentalRate;
	}
	
	public Size getSize() {
		return this.size;
	}
	
	public BigDecimal getDailyRentalRate() {
		return this.dailyRentalRate;
	}

	public BikeType getBikeType() { return this.type; }

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

	public void updateRentalPeriods(DateRange dateRange) {
		rentPeriods.add(dateRange);
	}

	/*This function removes any rental periods that are not needed, either
	* because the rental period has ended or the booking has been cancelled*/
	public void removeRentalPeriods(DateRange dateRange) {
		Iterator<DateRange> iter = rentPeriods.iterator();
		while(iter.hasNext()) {
			if (iter.equals(dateRange)) {
				iter.remove();
				break;
			}
		}
	}
	@Override
	public boolean equals (Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Bike bike = (Bike) o;
		return  this.getBikeType().equals(bike.getBikeType()) &&
				this.getSize().equals(bike.getSize()) &&
				this.getProvider().equals(bike.getProvider());
	}
	
} 	
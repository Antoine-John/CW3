package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.*;
import java.lang.System;

public class Bike {

	//Bike Attributes
	private BikeType type;
	private String model;
	private Size size;
	private BikeProvider provider;
	private Collection<DateRange> rentPeriods;
	private Status status;

	//Bike constructor
	public Bike(BikeType type, String model, Size size, BikeProvider provider) {
		this.type = type;
		this.model = model;
		this.size = size;
		for (Map.Entry<String, BigDecimal> entry: provider.getDailyRentalRates().entrySet()){
			if (entry.getKey() == this.type.getType()) {
				this.type.dailyRentalRate = entry.getValue();
				break;
			}
		}
		this.provider = provider;
		this.rentPeriods = new ArrayList<DateRange>();
		this.status = status.AVAILABLE;
	}

	//Print bike information
	public String toString() {
		String returnString = "";
		returnString += "\nBike Type: ";
		returnString += this.type.getType();
		returnString += "\nModel: ";
		returnString += model;
		returnString += "\nProvider: ";
		returnString += this.provider.name;

		return returnString;
	}
	
	//Getters/Setters
	public BikeProvider getProvider() {
		return this.provider;
	}
	
	public BigDecimal getPrice() {
		return this.type.dailyRentalRate;
	}
	
	public Size getSize() {
		return this.size;
	}

	public BikeType getBikeType() { return this.type; }

	public Status getStatus() {
		return this.status;
	}

	public void UpdateBikeStatus(Status newstatus) {
		this.status = newstatus;
	}

	public void updateRentalPeriods(DateRange dateRange) {
		rentPeriods.add(dateRange);
	}

	//Check if a bike is available in a certain DateRange
	public boolean dateClashCheck(DateRange dateRange) {	
		Iterator<DateRange> iter = rentPeriods.iterator();
		while (iter.hasNext()) {
			if (dateRange.overlaps(iter.next())) {
				return false;
			}
		}
		return true;
	}

	//Returns true if current bike type is among list of wanted types
	public boolean typeMatches(boolean[] types) {
		int toCheck;
		switch (type.getType()) {
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
	
	/*This function removes any rental periods that are not needed, either
	* because the rental period has ended or the booking has been cancelled*/
	public void removeRentalPeriods(DateRange dateRange) {
		Iterator<DateRange> itr = rentPeriods.iterator();
		while(itr.hasNext()) {
			if (itr.next().equals(dateRange)) {
				itr.remove();
			}
		}
	}

	//Need to only check type, size and provider when checking if any two bikes are the same
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
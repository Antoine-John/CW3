package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.lang.System;

public class BikeType {
	//BikeType attributes
	private String type;
	BigDecimal replacementValue;
	BigDecimal dailyRentalRate;

	//Defined set of bike types and associated replacement values
	private String types[] = {"Road", "Mountain", "Hybrid", "BMX", "Other"};
	private BigDecimal replacementValues[] = {new BigDecimal(10), new BigDecimal(10), new BigDecimal(10), new BigDecimal(10), new BigDecimal(10)};

	//Constructor
	public BikeType(int n){
		this.type = types[n];
		this.replacementValue = replacementValues[n];
		this.dailyRentalRate = new BigDecimal(0);
	}

	public BigDecimal getReplacementValue() {
		return this.replacementValue;
	}

	//Return bike types
	public String[] getTypes() {
		return this.types;
	}

	public String getType() {
		return this.type;
	}

	//Print BikeType
	public String toString() {
		String result = "";
		result += "Replacement Value: ";
		result += this.replacementValue;
		result += " Type: ";
		result += this.type;
		return result;
	}

	//Test if two bike types are equal
	@Override
	public boolean equals (Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BikeType bikeType = (BikeType) o;
		return  this.type == bikeType.type;
	}
}

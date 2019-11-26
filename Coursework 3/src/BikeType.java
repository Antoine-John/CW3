import java.math.BigDecimal;
import java.lang.System;

public class BikeType {
	String type;
	BigDecimal replacementValue;
	
	static String types[] = {"Road", "Mountain", "Hybrid", "BMX", "Other"};
	static BigDecimal replacementValues[] = {new BigDecimal(10), new BigDecimal(10), new BigDecimal(10), new BigDecimal(10), new BigDecimal(10)};
	
	public BikeType(int n){
		this.type = types[n];
		this.replacementValue = replacementValues[n];
	}

	public void print() {
		System.out.print("Replacement Value: ");
		System.out.print(this.replacementValue);
		System.out.print(" Type: ");
		System.out.println(this.type);
	}

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

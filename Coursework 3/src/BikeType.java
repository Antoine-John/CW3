import java.math.BigDecimal;

public class BikeType {
	String type;
	BigDecimal replacementValue;
	
	static String types[] = {"Road", "Mountain", "Hybrid", "BMX", "Other"};
	static BigDecimal replacementValues[] = {null, null, null, null, null};
	
	public BikeType(int n){
		this.type = types[n];
		this.replacementValue = replacementValues[n];
	}
}

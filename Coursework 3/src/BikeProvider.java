import java.math.BigDecimal;
import java.util.Collection;

public class BikeProvider {
	String name;
	Location address;
	Collection<BikeProvider> partners;
	BigDecimal depositRate;
	String password;
	
	public BikeProvider(String name, Location address, String password) {
		this.name = name;
		this.address = address;
		this.password = password;
	}
	
	public void NewPartnership(BikeProvider partner) {
		partners.add(partner);
	}
}

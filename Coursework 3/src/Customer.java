import java.util.Collection;

public class Customer {
	
	String name;
	String contactNumber;
	String emailAddress;
	Location address;
	String password;
	Collection<Booking> activeBookings;
	Collection<Quote> cart;
	
	public Customer(String name, String contactNumber, String emailAddress, Location address, String password) {
		this.name = name;
		this.contactNumber = contactNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.password = password;
	}

	void UpdateAddress(String postcode, String addrLn){
		this.address = new Location(postcode, addrLn);
	}
	
	Collection<Quote> RequestQuotes(DateRange daterange, Location location, boolean[] types, Size size) {
		Collection<Quote> searchResults = null;
		Bike[] allBikes = null;

		searchResults = SearchQuotes(allBikes, daterange, location, types, size);
		if (searchResults.size() == 0) {
			searchResults = SearchQuotes(allBikes, daterange.ExpandRange(daterange), location, types, size);
		}
		
		return searchResults;
	}
	
	Collection<Quote> SearchQuotes(Bike[] allBikes, DateRange daterange, Location location, boolean[] types, Size size){
		Collection<Quote> searchResults = null;
		for (int i = 0; i < allBikes.length; i++) {
			Bike currentBike = allBikes[i];
			if (location.IsNearTo(currentBike.provider.address) == true && size == currentBike.size && currentBike.dateClashCheck(daterange) == true && currentBike.typeMatches(types) == true) {
				searchResults.add(new Quote(currentBike, daterange));
			}
		}
		return searchResults;
	}
}

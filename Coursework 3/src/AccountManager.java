public class AccountManager {

	public static void createNewCustomer(String name, String contactNumber, String emailAddress, String postcode, String addrLn, String password) {
		new Customer(name, contactNumber, emailAddress, new Location(postcode, addrLn), password);
	}
	
	public static void createNewProvider(String name, String postcode, String addrLn, String password) {
		new BikeProvider(name, new Location(postcode, addrLn), password);
	}
}

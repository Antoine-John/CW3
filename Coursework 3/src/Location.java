public class Location {
	String postcode;
	String address;
	
	public Location(String postcode, String address) {
		this.postcode = postcode;
		this.address = address;
	}
	
	public boolean IsNearTo(Location other) {
		if (other.postcode.substring(0,2).equals(this.postcode.substring(0,2))) {
			return true;
		}
		return false;
	}
}

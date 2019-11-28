package uk.ac.ed.bikerental;

public class Location {
    //Attributes
    private String postcode;
    private String address;

    //Constructor
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    public boolean isNearTo(Location other) {
        //returns true if first two characters of postcode are the same
        if (other.postcode.substring(0,2).equals(this.postcode.substring(0,2))) {
			return true;
		}
		return false;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    
    // You can add your own methods here
}

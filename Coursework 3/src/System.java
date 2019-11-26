import java.security.Provider;
import java.util.Collection;
import java.util.HashMap;

public interface System {
    //The system holds all the information on the bike providers, the bikes in the system
    //We will create some bikes and bike providers to be used in testing

    public void addBike(Bike bike);
    public void addProvider(BikeProvider provider);

}

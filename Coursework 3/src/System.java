import java.security.Provider;
import java.util.Collection;
import java.util.HashMap;

public class System {
    //The system holds all the information on the bike providers, the bikes in the system
    //We will create some bikes and bike providers to be used in testing

    private HashMap<String,Bike> bikes; /*some hashMap of bikes where the key is the bike ID*/
    private HashMap<Location, Provider> providers;
    private HashMap<String,Bike> customers;
    private HashMap<int,Bike> bookings;
    private static int IDCounter;

    public System(String src) {
        /*get IDCounter - set this to be first line in file*/
        /* open and read file from source and put into hashMap */

    }

    public void addBike(Bike bike) {
        /*generate unique ID and write new bike to file */
        String ID = createID();
        bikes.put(ID, bike);
    }

    //creates unique id for bike when new bike is added
    static synchronized String createID() {
        String s = String.valueOf(IDCounter++);
        return s;
    }
}

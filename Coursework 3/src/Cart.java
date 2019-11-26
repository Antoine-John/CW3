import java.util.HashMap;
import java.util.logging.*;

public class Cart {
    private HashMap<String, Quote> contents;
    private BikeProvider currentProvider;

    Logger logger = Logger.getLogger(Cart.class.getName());

    public Cart() {
        this.contents = new HashMap<String, Quote>();
        this.currentProvider = null;
    }

    public void add(String bikeID, Quote quote) {
        if (this.currentProvider == null | this.currentProvider.equals(quote.getBike().getProvider())) {
            this.currentProvider = quote.getBike().getProvider();
            this.contents.put(bikeID, quote);
        } else {
            logger.setLevel(Level.WARNING);
            logger.warning("Warning: Different bike providers, cart not updated");
            //At this point a prompt will appear on UI for customer which will allow for the option for starting a new cart.
        }
    }

    public HashMap<String, Quote> getContents() {
        return this.contents;
    }

    public BikeProvider getProvider() {
        return this.currentProvider;
    }

    public void reset() {
        this.contents.clear();
        this.currentProvider = null;
    }


}

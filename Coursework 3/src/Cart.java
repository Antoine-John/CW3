import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import java.lang.System;

public class Cart {
    private HashMap<String, Quote> contents;
    private BikeProvider currentProvider;
    private BigDecimal cartCost;

    Logger logger = Logger.getLogger(Cart.class.getName());

    public Cart() {
        this.contents = new HashMap<String, Quote>();
        this.currentProvider = null;
        this.cartCost = new BigDecimal(0);
    }

    public void remove(String bikeID) {
        contents.remove(bikeID);
        if (contents.isEmpty()) {
            currentProvider = null;
        }
    }

    public BigDecimal getCartCost() {
        this.cartCost = calculateCost();
        return this.cartCost;
    }

    private BigDecimal calculateCost() {
        BigDecimal cost = new BigDecimal(0);
        for (Map.Entry<String, Quote> entry: contents.entrySet()) {
            cost = cost.add(entry.getValue().getTotalCost());
        }
        return cost;
    }

    public boolean add(String bikeID, Quote quote) {
        if (this.currentProvider == null || this.currentProvider.equals(quote.getBike().getProvider())) {
            this.currentProvider = quote.getBike().getProvider();
            this.contents.put(bikeID, quote);
            return true;
        } else {
            logger.setLevel(Level.WARNING);
            logger.warning("Warning: Different bike providers, cart not updated");
            //At this point a prompt will appear on UI for customer which will allow for the option for starting a new cart.
            return false;
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

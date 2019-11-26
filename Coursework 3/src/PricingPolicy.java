import java.math.BigDecimal;
import java.util.Collection;

interface PricingPolicy {
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal price);
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange date);
}
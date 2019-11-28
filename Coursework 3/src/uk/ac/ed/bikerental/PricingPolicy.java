package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

interface PricingPolicy {
    public void setDailyRentalPrice(BikeType bike, BigDecimal price);
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange date);
}
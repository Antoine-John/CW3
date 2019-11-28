package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
    private LocalDate start, end;
    
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalDate getStart() {
        return this.start;
    }
    
    public LocalDate getEnd() {
        return this.end;
    }

    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    /*
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }*/
    public int getDuration() {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public boolean overlaps(DateRange date) {
        /*checks if two date ranges overlap. Visually:
        *      |--------DR1--------|
        * --------------------------------------
        *              |-----------DR2--------|*/
        if (ChronoUnit.DAYS.between(date.getEnd(), this.start) <= 0) {
            if (ChronoUnit.DAYS.between(this.end, date.getStart()) <= 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

    public DateRange ExpandRange() {
        //If no quotes are found we can use this to expand the range of the search
        LocalDate newStart = this.getStart().minusDays(3);
        LocalDate newEnd = this.getEnd().plusDays(3);
        DateRange expandedDateRange = new DateRange(newStart, newEnd);
        return expandedDateRange;
    }
}

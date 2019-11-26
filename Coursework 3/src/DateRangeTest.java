import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {
    //Testing Overlap function in DateRange
    @Test
    void testOverLaps1() {
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MAY, 1));
        assertTrue(range1.overlaps(range2));
    }

    @Test
    void testOverLaps2() {
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MARCH, 1));
        assertTrue(range1.overlaps(range2));
    }
    @Test
    void testOverLaps3() {
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.JUNE, 1), LocalDate.of(2014, Month.JULY, 1));
        assertFalse(range1.overlaps(range2));
    }

    @Test
    void testExpand() {
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.JANUARY, 4), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 4));
        assertTrue(range2.getStart().equals(range1.ExpandRange().getStart()) && range2.getEnd().equals(range1.ExpandRange().getEnd()));
    }

    @Test
    void testGetDuration() {
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.JANUARY, 4), LocalDate.of(2014, Month.APRIL, 1));
        assertEquals(87, range1.getDuration());
    }

}
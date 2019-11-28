package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverLapsTrue1() {
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MAY, 1));
        assertTrue(range1.overlaps(range2));
    }

    @Test
    void testOverLapsTrue2() {
        DateRange range1 = new DateRange(LocalDate.of(2014, Month.JANUARY, 1), LocalDate.of(2014, Month.APRIL, 1));
        DateRange range2 = new DateRange(LocalDate.of(2014, Month.FEBRUARY, 1), LocalDate.of(2014, Month.MARCH, 1));
        assertTrue(range1.overlaps(range2));
    }
    @Test
    void testOverLapsFalse() {
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

package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;

public class TestLocation {

    @Test
    void IsNearToTest() {
        Location location1 = new Location("EH8 234", "asfsdf");
        Location location2 = new Location("EH5 345", "kshnek");
        assertTrue(location1.isNearTo(location2));
    }


}


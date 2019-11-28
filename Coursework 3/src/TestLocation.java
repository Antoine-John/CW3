import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestLocation {

    @Test
    void IsNearToTest() {
        Location location1 = new Location("EH8 234", "asfsdf");
        Location location2 = new Location("EH5 345", "kshnek");
        assertTrue(location1.IsNearTo(location2));
    }


}

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import static org.junit.jupiter.api.Assertions.*;

public class ShotTest {

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "100,100,106,99,false"})
    void testsShot(int x, int y, int expectedX, int expectedY, boolean isVisible){
        Shot shot = new Shot(x, y);
        assertEquals(expectedX, shot.getX());
        assertEquals(expectedY, shot.getY());
        assertFalse(shot.isVisible());  //Si es falso nos da como correcta


    }

}

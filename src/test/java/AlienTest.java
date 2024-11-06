import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import space_invaders.sprites.Alien;
import static org.junit.jupiter.api.Assertions.*;

public class AlienTest {
  //@Test
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.CsvSource(value={
          "0,0,0,0",
          "0,-1,0,0",
          "358,351,358,350",
          "-1,0,0,0",
          "359,350,358,350"})
  void testsInitAlien1(int x, int y, int expectedX, int expectedY){
    Alien alien = new Alien(x,y);
    assertEquals(expectedX, alien.getX());
    assertEquals(expectedY, alien.getY());

  }
  @Test
  void testsInitAlien2(){
    Alien alien = new Alien(0,0);
    assertEquals(0, alien.getX());
    assertEquals(0, alien.getY());

  }
}

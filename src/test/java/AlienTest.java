import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Alien.Bomb;
import static org.junit.jupiter.api.Assertions.*;

public class AlienTest {

  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.CsvSource(value={
          "0,0,0,0",
          "0,-1,0,0",
          "-1,0,0,0",
          "358,351,358,350",
          "359,350,358,350"})
  void testsInitAlien1(int x, int y, int expectedX, int expectedY){
    Alien alien = new Alien(x,y);
    assertEquals(expectedX, alien.getX());
    assertEquals(expectedY, alien.getY());
    assertTrue(alien.isVisible());
    assertFalse(alien.isDying());

  }
  /*@Test
  void testsInitAlien2(){
    Alien alien = new Alien(0,0);
    assertEquals(0, alien.getX());
    assertEquals(0, alien.getY());

  }*/

  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.CsvSource(value={
          "1,1",
          "-1,-1",
          "0,0"})
  void testsAlienAct(int dir, int expectedX){   //LOS ALIENS SE MUEVEN SOLO UNA CASILLA NO??
    Alien alien = new Alien(0,0);
    alien.act(dir);
    assertEquals(expectedX, alien.getX());
  }

  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.CsvSource(value={
          "0,0,0,0",
          "0,-1,0,0",
          "358,351,358,350",
          "-1,0,0,0",
          "359,350,358,350"})
  void testsInitBomb(int x, int y, int expectedX, int expectedY){
    Alien alien = new Alien(x,y);
    Bomb bomb = alien.new Bomb(x,y);
    assertEquals(expectedX, bomb.getX());
    assertEquals(expectedY, bomb.getY());
    assertTrue(bomb.isDestroyed());

  }

}

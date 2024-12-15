import main.Board;
import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;
import java.awt.event.KeyEvent;

public class TestsIntegracion {

    @Test
    void test_Update(){
        Board board = new Board();

        Player player = board.getPlayer();
        player.setDx(2);
        System.out.println("msg1- Player DX:"+player.getDx()+", X:"+player.getX()+"; Expected X: 171");
        player.act();
        System.out.println("retMsg1- Player X: " + player.getX());

        System.out.println("-----------------");
        Shot shot = new Shot(player.getX(), player.getY());
        board.setShot(shot);
        System.out.println("msg2- Shot X:"+shot.getX()+", Y:"+shot.getY()+"; Expected X:177 , Y:275");
        board.update_shots();
        System.out.println("retMsg2- Shot X: " + shot.getX() + ", Y: " + shot.getY());

        System.out.println("-----------------");
        Alien alien =board.getAliens().getFirst();
        System.out.println("msg3- Alien(0) direccion:"+board.getDirection()+", X:"+alien.getX()+", Y:"+alien.getY()+"; Expected x:149, Y:5");
        board.update_aliens();
        System.out.println("retMsg3- Alien(0) X: " + alien.getX() + ", Y: " + alien.getY());

        System.out.println("-----------------");
        Alien.Bomb bomb= alien.getBomb();
        bomb.setDestroyed(false);
        bomb.setX(alien.getX());
        bomb.setY(alien.getY());
        System.out.println("msg5- Alien(0)Bomb Y:"+bomb.getY()+"; Expected Y:6");
        board.update_bomb();
        System.out.println("retMsg5- Alien(0)Bomb Y: "+bomb.getY());
    }

}

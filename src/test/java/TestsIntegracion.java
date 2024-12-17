import main.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

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

    /**
     * Test integración mockito BOARD
     */

    private Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
    }


    @Test
    void testUpdateShots_HitsAlien() {
        // Crear mocks para el disparo y el alien
        Shot shotMock = Mockito.mock(Shot.class);
        Alien alienMock = Mockito.mock(Alien.class);

        // Configurar el mock del disparo
        when(shotMock.isVisible()).thenReturn(true);
        when(shotMock.getX()).thenReturn(50);
        when(shotMock.getY()).thenReturn(50);

        // Configurar el mock del alien
        when(alienMock.isVisible()).thenReturn(true);
        when(alienMock.getX()).thenReturn(40);
        when(alienMock.getY()).thenReturn(40);

        // Configurar el tablero con los mocks
        board.setShot(shotMock);

        List<Alien> aliens = new ArrayList<>();
        aliens.add(alienMock);
        board.setAliens(aliens);

        // Llamar al método update_shots
        board.update_shots();

        // Verificar que el disparo "muere" después del impacto
        verify(shotMock).die();

        // Verificar que el alien se marca como muriendo y se hace invisible
        verify(alienMock).setDying(true);
        verify(alienMock).setVisible(false);
    }

    @Test
    void testUpdateAliens_MovementAndDirection() {
        // Crear mocks para los alienígenas
        Alien alienMock1 = Mockito.mock(Alien.class);
        Alien alienMock2 = Mockito.mock(Alien.class);

        // Configuración inicial
        when(alienMock1.getX()).thenReturn(350);
        when(alienMock2.getX()).thenReturn(20);
        when(alienMock1.isVisible()).thenReturn(true);
        when(alienMock2.isVisible()).thenReturn(true);

        // Configurar el tablero
        List<Alien> aliens = new ArrayList<>();
        aliens.add(alienMock1);
        aliens.add(alienMock2);
        board.setAliens(aliens);

        // Ejecutar el método
        board.update_aliens();

        // Verificar interacciones
        verify(alienMock1).act(anyInt());
        verify(alienMock2).act(anyInt());
        assertEquals(-1, board.getDirection());
    }

    @Test
    void testUpdateBomb_PlayerHit() {
        // Crear mocks para el jugador, el alien y la bomba
        Player playerMock = Mockito.mock(Player.class);
        Alien alienMock = Mockito.mock(Alien.class);
        Alien.Bomb bombMock = Mockito.mock(Alien.Bomb.class);

        // Configuración inicial
        when(playerMock.getX()).thenReturn(50);
        when(playerMock.getY()).thenReturn(50);
        when(playerMock.isVisible()).thenReturn(true);

        when(alienMock.getBomb()).thenReturn(bombMock);
        when(bombMock.getX()).thenReturn(55);
        when(bombMock.getY()).thenReturn(55);
        when(bombMock.isDestroyed()).thenReturn(false);

        // Configurar el tablero
        board.setPlayer(playerMock);
        List<Alien> aliens = new ArrayList<>();
        aliens.add(alienMock);
        board.setAliens(aliens);

        // Ejecutar el método
        board.update_bomb();

        // Verificar interacciones
        verify(playerMock).setDying(true);
        verify(bombMock).setDestroyed(true);
    }

    @Test
    void testUpdateShots_NoHit() {
        // Crear mocks para el disparo y el alien
        Shot shotMock = Mockito.mock(Shot.class);
        Alien alienMock = Mockito.mock(Alien.class);

        // Configurar el mock del disparo (no está en el rango del alien)
        when(shotMock.isVisible()).thenReturn(true);
        when(shotMock.getX()).thenReturn(100);
        when(shotMock.getY()).thenReturn(100);

        // Configurar el mock del alien
        when(alienMock.isVisible()).thenReturn(true);
        when(alienMock.getX()).thenReturn(40);
        when(alienMock.getY()).thenReturn(40);

        // Configurar el tablero con los mocks
        board.setShot(shotMock);
        List<Alien> aliens = new ArrayList<>();
        aliens.add(alienMock);
        board.setAliens(aliens);

        // Llamar al método update_shots
        board.update_shots();

        // Verificar que el disparo no muere porque no hay impacto
        verify(shotMock, never()).die();

        // Verificar que el alien no se marca como muriendo ni se hace invisible
        verify(alienMock, never()).setDying(true);
        verify(alienMock, never()).setVisible(false);
    }

    @Test
    void testInitBoardWithMocks() {
        // Crear mocks para los componentes del tablero
        Player playerMock = Mockito.mock(Player.class);
        Shot shotMock = Mockito.mock(Shot.class);
        Alien alienMock = Mockito.mock(Alien.class);

        // Crear una lista de aliens mockeados
        List<Alien> aliens = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            aliens.add(alienMock);
        }

        // Configurar el tablero
        board.setPlayer(playerMock);
        board.setShot(shotMock);
        board.setAliens(aliens);

        // Verificar que los componentes están inicializados correctamente
        assertNotNull(board.getPlayer());
        assertNotNull(board.getShot());
        assertEquals(24, board.getAliens().size());

        // Verificar que los mocks no fueron modificados
        verifyNoInteractions(playerMock);
        verifyNoInteractions(shotMock);
        verifyNoInteractions(alienMock);
    }

}

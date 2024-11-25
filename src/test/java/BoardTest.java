import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.BeforeEach;
public class BoardTest {
    private Board board;
    /**
     * Test de caja negra de gameInit
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "false,false,'150;168;186;204;222;240','5;23;41;59',24"})
    void testGameInit(boolean jugador, boolean disparo, String filasCoord, String columCoord, int numAlien){
        Board board = new Board();

        // Verifica si el jugador y disparo están inicializados
        assertEquals(jugador, board.getPlayer() == null);
        assertEquals(disparo, board.getShot() == null);

        // Verifica la cantidad de alienígenas
        assertEquals(numAlien, board.getAliens().size());

        // Convierte las coordenadas de fila y columna a listas de enteros
        List<Integer> filasCoordArray = Stream.of(filasCoord.split(";"))
                .map(Integer::parseInt)
                .toList();
        List<Integer> columCoordArray = Stream.of(columCoord.split(";"))
                .map(Integer::parseInt)
                .toList();

        // Verifica las posiciones de los alienígenas en la cuadrícula
        for (int i = 0; i < columCoordArray.size(); i++) {
            for (int j = 0; j < filasCoordArray.size(); j++) {
                Alien alien = board.getAliens().get(i * filasCoordArray.size() + j);
                assertEquals((int) filasCoordArray.get(j), alien.getX());
                assertEquals((int) columCoordArray.get(i), alien.getY());
            }
        }

    }

    /**
     * Test de caja negra de update
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "24,Game Won!,false", //NumMuertos, mensaje, timer.isRunning
            "0,'',true"
            })
    void testsUpdate(int alienMuertos, String msg, boolean timer){
        Board board = new Board();
        board.setDeaths(alienMuertos);
        board.update();
        if(alienMuertos==24) {//Comprobaremos en caso de que hayan muerto los 24 alien
            assertEquals(alienMuertos, board.getDeaths());
            assertEquals("Game won!",board.getMessage());
            assertEquals(timer, board.getTimer().isRunning());
        }else{
            assertEquals( timer, board.getTimer().isRunning());
            assertEquals(msg, board.getMessage());
        }
    }

    /**
     * Tests de caja negra de update_Bombs
     */
    //Generacion de nueva bomba (Deberiamos poder modificar Chance, para poder asegurar que si o si va a sacar una bomba)
    @Test
    public void test_update_Bombs_nuevaBomba(){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); //Ponemos que empiece como destruida
        board.update_bomb(true);
        assertEquals(false, bomb.isDestroyed());
        //assertEquals(board.getPlayer().isDying(), false);
        assertEquals(alien.getX(), bomb.getX());
        assertEquals(alien.getY()+1, bomb.getY());
    }

    //La bomba llega al suelo
    @Test
    public void test_update_Bombs_bombaSuelo(){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        Alien.Bomb bomb = alien.getBomb(); //Cogemos su bomba
        bomb.setDestroyed(false); //Ponemos que empiece como no destruida
        bomb.setX(0);
        bomb.setY(290);
        board.update_bomb(true);
        assertEquals(true, bomb.isDestroyed());
        //assertEquals(board.getPlayer().isDying(), false);
        assertEquals(0,bomb.getX());
        assertEquals(290,bomb.getY());
    }

    //La bomba alcanza al jugador
    @Test
    public void test_update_Bombs_jugadorAlcanzado(){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        Alien.Bomb bomb = alien.getBomb(); //Cogemos su bomba
        bomb.setDestroyed(false); //Ponemos que empiece como no destruida
        bomb.setX(169);
        bomb.setY(274);
        Player player = board.getPlayer();
        player.setX(169);
        player.setY(280);
        board.update_bomb(true);
        assertEquals(true, bomb.isDestroyed());
        assertEquals(true,board.getPlayer().isDying());
        assertEquals(169, bomb.getX());
        assertEquals(274,bomb.getY());
    }

    //Test caja negra UpdateBombs
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "10,279,169,280,10,280",
            "180,282,169,280,180,283",
            "169,10,169,280,169,11"
    })
    void tests_update_Bombs_BombaDesciende(int bombX, int bombY, int playerX, int playerY, int bombNewX, int bombNewY){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        Alien.Bomb bomb = alien.getBomb(); //Cogemos su bomba
        bomb.setDestroyed(false); //Ponemos que empiece como no destruida

        bomb.setX(bombX);
        bomb.setY(bombY);
        Player player = board.getPlayer();
        player.setX(playerX);
        player.setY(playerY);
        board.update_bomb(true);
        assertEquals(false,bomb.isDestroyed());
        assertEquals(false, board.getPlayer().isDying());
        assertEquals(bombNewX,bomb.getX());
        assertEquals(bombNewY,bomb.getY());
    }

    /**
     *  Caja negra Update Shots
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "150,150,150,150,150,150,1,false,false",
            "150,150,20,1,20,0,0,false,true",
            "150,150,170,150,170,149,0,true,true",
            "150,150,150,170,150,169,0,true,true",
            "150,150,150,130,150,129,0,true,true"
    })
    void tests_update_Shots(int alienX, int alienY, int shotX, int shotY, int shotNewX, int shotNewY, int newDeaths, boolean newVisibleShot, boolean newVisibleAlien){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        Shot shot = new Shot(shotX, shotY);
        alien.setX(alienX);
        alien.setY(alienY);
        shot.setVisible(true);

        board.update_shots();
        assertEquals(shotNewX, shot.getX());
        assertEquals(shotNewY, shot.getY());
        assertEquals(newDeaths, board.getDeaths());
        assertEquals(newVisibleShot, shot.isVisible() );
        assertEquals(newVisibleAlien, alien.isVisible());

    }

    /**
     *  Caja negra Update aliens
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "0,100,true,-1,0,115,1,true,''",
            "358,100,true,1,358,115,-1,true,''",
            "0,275,true,-1,0,290,1,false,'Invasion!'",
            "358,275,true,1,358,290,-1,false,'Invasion!'",
            

    })
    void tests_update_aliens(int alienX, int alienY, boolean alienVisible, int direccion, int alienNewX, int alienNewY, int newDireccion, boolean newInGame, String msg){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(alienVisible);
        alien.setDying(false);
        board.setInGame(true);
        alien.setX(alienX);
        alien.setY(alienY);
        board.setDirection(direccion);

        board.update_aliens();

        assertEquals(alienNewX, alien.getX());
        assertEquals(alienNewY, alien.getY());
        assertEquals(newDireccion, board.getDirection());

        assertEquals(newInGame, board.isInGame());
        assertEquals(msg, board.getMessage());

    }

    /**
     * PRUEBAS CAJA BLANCA
     */

    /**
     * Test de caja Blanca de update
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "1, '', true, true",
            "5, 'Game Won!',false, false"}) //NumMuertos, mensaje, timer.isRunning
    void tests_CajaBlanca_Update(int alienMuertos, String msg, boolean timer, boolean NewInGame){
        Board board = new Board();
        board.setInGame(true);
        board.setMessage("");
        board.getTimer().start();
        board.setDeaths(alienMuertos);
        board.update();
        assertEquals(NewInGame, board.isInGame());
        assertEquals(board.getTimer().isRunning(), timer);
        assertEquals(board.getMessage(), msg);
    }

    /**
     *  Prueba con mas de un alien
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "false, true, false, 10 ,10 ,10 ,10 ,10 ,10, 10, 10, true, false",
            "false, false, true, 10, 10,5,15,10,10,10,10, true, false",
            "false, false, true, 50,50,55,55,10,10,50,50, true, false",
            "false, true, false, 10,10,10,10,10,10,10,11, false, false",
            "false, true, false, 10,285,10,10,10,10,10,286,false, false"

    })
    void tests_CajaBlanca_update_Bombs(boolean alienVisible, boolean bombDestroyed, boolean playerVisible, int bombX, int bombY, int playerX, int playerY, int alienX, int alienY,
                                                      int bombNewX, int bombNewY, boolean newBombDestroyed, boolean newPlayerDying){
        Board board = new Board();
        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(alienVisible);
        alien.setDying(false);  //Y que no este destruido
        alien.setX(alienX);
        alien.setY(alienY);

        Alien.Bomb bomb = alien.getBomb(); //Cogemos su bomba
        bomb.setDestroyed(bombDestroyed);
        bomb.setX(bombX);
        bomb.setY(bombY);

        Player player = board.getPlayer();
        player.setVisible(playerVisible);
        player.setX(playerX);
        player.setY(playerY);

        board.update_bomb(true);
        assertEquals(newBombDestroyed,bomb.isDestroyed());
        assertEquals(newPlayerDying, player.isDying());
        assertEquals(bombNewX, bomb.getX());
        assertEquals(bombNewY, bomb.getY());
    }

    /**
     * Test Caja Blanca Update_shots
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "false, false, 10, 10,10,10,false,0,10,false",
            "true, false, 10,10,10,10, false, 0,6, false",
            "true, true,50,50,55,55, false,0,46,false",
            "true, true, 55,55,50,50,true,-1,51,true",
            "true, false,10,-10,55,55,false,0,-10, true",
            "true, true,-50,-50,-55,-55,true,-1,-50, true"
    })
    void tests_CajaBlanca_update_Shots(boolean shotIsVisible, boolean alienIsVisible, int shotX, int shotY, int alienX, int alienY,
                                       boolean newAlienIsDying, int newDeaths, int newShotY, boolean newShotIsDying){
        Board board = new Board();
        board.setDeaths(0);

        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(alienIsVisible); //Ponemos que el alien sea visible
        alien.setDying(false);  //Y que no este destruido
        alien.setX(alienX);
        alien.setY(alienY);

        Shot shot = new Shot(shotX, shotY);
        shot.setVisible(shotIsVisible);
        shot.setDying(false);

        board.update_shots();

        assertEquals(newShotY, shot.getY());
        assertEquals(newDeaths, board.getDeaths());
        assertEquals(newAlienIsDying, alien.isDying());
        assertEquals(newShotIsDying, shot.isDying());

    }

    /**
     * Test Caja Blanca GameInit
     */
    @Test
    void test_CajaBlanca_GameInit(){
        Board board = new Board();
        // Verifica si el jugador y disparo están inicializados
        assertEquals(false, board.getPlayer() == null);
        assertEquals(false, board.getShot() == null);
        // Verifica la cantidad de alienígenas
        assertEquals(24, board.getAliens().size());
    }

    /**
     * Test Caja Blanca Update_aliens
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "10,300,1,10,300,0",
            "10,300,1,10,315,0",
            "330,300,1,330,300,1",
            "330,300,1,330,300,1"

    })
    void tests_CajaBlanca_update_aliens(int alienX, int alienY, int direccion, int alienNewX, int alienNewY, int newDireccion){
        Board board = new Board();
        board.setInGame(true);

        Alien alien = board.getAliens().get(0); //Cogemos uno de los aliens
        alien.setVisible(true);
        alien.setDying(false);
        alien.setX(alienX);
        alien.setY(alienY);
        alien.setDx(direccion);

        board.update_aliens();

        assertEquals(alienNewX, alien.getX());
        assertEquals(alienNewY, alien.getY());
        assertEquals(newDireccion, alien.getDx());
    }



}

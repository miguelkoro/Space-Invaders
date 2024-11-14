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

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.BeforeEach;
public class BoardTest {
    private Board board;

   /* @BeforeEach
    public void setUp(){
        board= new Board();
    }

    @Test
    public void UpdateInit(){
        //assertEquals(board.deaths,0); //No es publica la variable
        Class c = board.getClass();
        Field fields[] = c.getDeclaredFields();
        for(Field f: fields){
            System.out.println("Nombre " + f.getName());
            System.out.println("Tipo dato " + f.getType());
            System.out.println("Tipo modificador " + Modifier.toString(f.getModifiers()));
        }
    }*/

    /**
     * Test de caja negra de gameInit
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "false,false,'150;168;186;204;222;240','5;23;41;59',24"})
    void testGameInit(boolean jugador, boolean disparo, String filasCoord, String columCoord, int numAlien){
        Board board = new Board();
        //assertEquals(numAlien, board.getAliens().size());


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
            assertEquals(board.getMessage(), "Game Won!");
            assertEquals(board.getTimer().isRunning(), timer);
        }else{
            assertEquals(board.getTimer().isRunning(), timer);
            assertEquals(board.getMessage(), msg);
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
        board.update_bomb();
        assertEquals(bomb.isDestroyed(), false);
        //assertEquals(board.getPlayer().isDying(), false);
        assertEquals(bomb.getX(), alien.getX());
        assertEquals(bomb.getY(), alien.getY()+1);
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
        board.update_bomb();
        assertEquals(bomb.isDestroyed(), true);
        //assertEquals(board.getPlayer().isDying(), false);
        assertEquals(bomb.getX(), 0);
        assertEquals(bomb.getY(), 290);
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
        board.update_bomb();
        assertEquals(bomb.isDestroyed(), true);
        assertEquals(board.getPlayer().isDying(), true);
        assertEquals(bomb.getX(), 169);
        assertEquals(bomb.getY(), 274);
    }

    //La bomba baja un pixel
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
        board.update_bomb();
        assertEquals(bomb.isDestroyed(), false);
        assertEquals(board.getPlayer().isDying(), false);
        assertEquals(bomb.getX(), bombNewX);
        assertEquals(bomb.getY(), bombNewY);
    }


}

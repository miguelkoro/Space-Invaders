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

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "false,false,'150;168;186;204;222;240','5;23;41;59',24",
            "false,false,'150;168;186;204;222;240','0;0;0;0',24",
            "false,false,'0;0;0;0;0;0','5;23;41;59',24",
            "true,false,'150;168;186;204;222;240','5;23;41;59',24",
            "false,true,'150;168;186;204;222;240','5;23;41;59',24",
            "false,false,'150;168;186;204;222;240','5;23;41;59',10"})
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

}

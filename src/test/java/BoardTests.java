import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import main.Board;
//import org.junit.jupiter.api.BeforeEach;
public class BoardTests {
    private Board board;

//    @BeforeEach
    public void setUp(){
        board= new Board();
    }

//    @test
    public void UpdateInit(){
        //assertEquals(board.deaths,0); //No es publica la variable
        Class c = board.getClass();
        Field fields[] = c.getDeclaredFields();
        for(Field f: fields){
            System.out.println("Nombre " + f.getName());
            System.out.println("Tipo dato " + f.getType());
            System.out.println("Tipo modificador " + Modifier.toString(f.getModifiers()));
        }
    }

}

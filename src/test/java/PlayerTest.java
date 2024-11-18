import space_invaders.sprites.Player;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
public class PlayerTest {


    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "169,280"})
    void testInitPlayer(int x, int y){
        Player player = new Player();
        assertEquals(x, player.getX());
        assertEquals(y, player.getY());
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "2,0,2",
            "-2,343,341",
            "-2,0,0",
            "2,343,343",
            "0,0,0"})
    void testAct(int dx, int x, int nuevoX){
        Player player = new Player();
        player.setDx(dx); //Ponemos la velocidad del jugador
        player.setX(x);   //Ponemos la posicion en X
        player.act();
        assertEquals(nuevoX, player.getX());
    }


    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "37,-2", //VK_LEFT
            "39, 2",  //VK_RIGHT
            "38,0"})     //VK_UP
    void testKeyPressed(int keyCode, int dxEsperado){
        Player player = new Player();

        // Crear el evento KeyEvent usando el código de tecla
        KeyEvent e = new KeyEvent(
                new java.awt.Component() {}, // Componente ficticio
                KeyEvent.KEY_PRESSED,        // Tipo de evento
                System.currentTimeMillis(),   // Timestamp
                0,                            // Modificadores (ninguno)
                keyCode,                      // Código de tecla
                KeyEvent.CHAR_UNDEFINED       // Carácter
        );

        player.keyPressed(e);
        assertEquals(dxEsperado, player.getDx());
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "37,0", //VK_LEFT
            "39,0",  //VK_RIGHT
            "38,10"})     //VK_UP
    void testKeyReleased(int keyCode, int dxEsperado){
        Player player = new Player();
        player.setDx(10);
        // Crear el evento KeyEvent usando el código de tecla
        KeyEvent e = new KeyEvent(
                new java.awt.Component() {}, // Componente ficticio
                KeyEvent.KEY_PRESSED,        // Tipo de evento
                System.currentTimeMillis(),   // Timestamp
                0,                            // Modificadores (ninguno)
                keyCode,                      // Código de tecla
                KeyEvent.CHAR_UNDEFINED       // Carácter
        );

        player.keyReleased(e);
        assertEquals(dxEsperado, player.getDx());
    }

    /**
     * TESTS DE CAJA BLANCA
     */
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "0,2",
            "4,2",
            "328,328"})
    void test_CajaBlanca_Act(int x, int nuevoX){
        Player player = new Player();
        player.setDx(2); //Ponemos la velocidad del jugador
        player.setX(x);   //Ponemos la posicion en X
        player.act();
        assertEquals(nuevoX, player.getX());
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "37,2", //VK_LEFT
            "38,0", //VK_UP
            "39, 2"})  //VK_RIGHT
    void test_CajaBlanca_KeyPressed(int keyCode, int dxEsperado){
        Player player = new Player();

        // Crear el evento KeyEvent usando el código de tecla
        KeyEvent e = new KeyEvent(
                new java.awt.Component() {}, // Componente ficticio
                KeyEvent.KEY_PRESSED,        // Tipo de evento
                System.currentTimeMillis(),   // Timestamp
                0,                            // Modificadores (ninguno)
                keyCode,                      // Código de tecla
                KeyEvent.CHAR_UNDEFINED       // Carácter
        );

        player.keyPressed(e);
        assertEquals(dxEsperado, player.getDx());
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource(value={
            "37,0", //VK_LEFT
            "38,10", //VK_UP
            "39,0"})   //VK_RIGHT
    void test_CajaBlanca_KeyReleased(int keyCode, int dxEsperado){
        Player player = new Player();
        player.setDx(10);
        // Crear el evento KeyEvent usando el código de tecla
        KeyEvent e = new KeyEvent(
                new java.awt.Component() {}, // Componente ficticio
                KeyEvent.KEY_PRESSED,        // Tipo de evento
                System.currentTimeMillis(),   // Timestamp
                0,                            // Modificadores (ninguno)
                keyCode,                      // Código de tecla
                KeyEvent.CHAR_UNDEFINED       // Carácter
        );

        player.keyReleased(e);
        assertEquals(dxEsperado, player.getDx());
    }

}



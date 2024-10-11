package space_invaders.sprites;

import main.Commons;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width;

    public Player() {

        initPlayer();
    }
    /**
     * Crea un nuevo jugador, le asigna su imagen en la interfaz y lo ubica en el centro de la pantalla
     * */
    private void initPlayer() {

        var playerImg = "src/main/resources/images/player.png";
        var ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = 270;
        setX(START_X);

        int START_Y = 280;
        setY(START_Y);
    }

    /**
     * Mueve la posición del jugador a la izquierda o a la derecha.
     * Si el jugador ha alcanzado el borde de la pantalla y se intenta mover fuera de la pantalla, lo mantendrá quieto en el borde.
     * */
    public void act() {

        x += dx;

        if (x >= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }
    /**
     * Comprueba qué tecla está pulsada y:
     * - Si se pulsa la tecla flecha a la izquierda, guarda el movimiento hacia la izquierda
     * - Si se pulsa la tecla flecha a la derecha, guarda el movimiento hacia la derecha
     * Cualquier otra tecla de movimiento no produce acción
     * @param e tecla presionada
     * */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }
    /**
     * Comprueba si la tecla e ya no está pulsada:
     * - Si se suelta la tecla flecha a la izquierda, reinicia el desplazamiento de la izquierda a 0
     * - Si se pulsa la tecla flecha a la derecha, reinicia el desplazamiento de la derecha a 0
     * Cualquier otra tecla de movimiento no produce acción
     * @param e tecla presionada
     * */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }
}

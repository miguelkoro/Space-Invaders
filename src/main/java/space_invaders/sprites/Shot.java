package space_invaders.sprites;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    public Shot() {
    }
    /**
     * Inicializa un nuevo objeto disparo en las coordenadas indicadas
     * @param x coordenada X de la posición del nuevo disparo
     * @param y coordenada Y de la posición del nuevo disparo
     * */
    public Shot(int x, int y) {

        initShot(x, y);
    }
    /**
     * Inicializa un nuevo objeto disparo en las coordenadas indicadas y le asigna la imagen correspondiente en la interfaz
     * @param x coordenada X de la posición del nuevo disparo
     * @param y coordenada Y de la posición del nuevo disparo
     * Si alguna de las coordenadas indicadas es mayor al máximo permitido, se le asignará el valor máximo permitido.
     * Si se introducen valores negativos de coordenada, se asignará el mínimo permitido (0).
     * */
    private void initShot(int x, int y) {

        var shotImg = "src/main/resources/images/shot.png";
        var ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        int H_SPACE = 6;
        setX(x + H_SPACE);

        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}


package space_invaders.sprites;

import main.Commons;

import java.awt.Image;

public class Sprite {

    private boolean visible;
    private Image image;
    private boolean dying;

    int x;
    int y;
    int dx;

    public Sprite() {

        visible = true;
    }
    /**
     * Cambia la visibilidad del elemento a falso
     * */
    public void die() {

        visible = false;
    }
    /**
     * Comprueba la visibilidad del elemento
     * */

    public boolean isVisible() {

        return visible;
    }
    /**
     * Cambia la visibilidad del elemento al valor indicado
     * @param visible
     * */

    public void setVisible(boolean visible) {

        this.visible = visible;
    }
    /**
     * Asigna una imagen al elemento para identificarlo en la interfaz
     * @param image Archivo de imagen a asignar
     * */
    public void setImage(Image image) {

        this.image = image;
    }
    /**
     * Devuelve la imagen usada para identificar el elemento en la interfaz
     * @return image
     * */
    public Image getImage() {

        return image;
    }
    /**
     * Asigna la coordenada X del elemento
     * @param x
     * */
    public void setX(int x) {

        this.x = x;
    }

    /**
     * Asigna la coordenada Y del elemento
     * @param y
     * */
    public void setY(int y) {

        this.y = y;
    }

    /**
     * Devuelve la coordenada Y del elemento
     * @return Y
     * */
    public int getY() {

        return y;
    }

    /**
     * Devuelve la coordenada X del elemento
     * @return X
     * */
    public int getX() {

        return x;
    }

    /**
     * Asigna el valor de eliminación de un elemento
     * @param dying true or false
     * */
    public void setDying(boolean dying) {

        this.dying = dying;
    }
    /**
     * Devuelve el valor de eliminación de un elemento
     * @return valor de eliminación
     * */
    public boolean isDying() {

        return this.dying;
    }

    //CLASES AÑADIDAS

    /**
     * Devuelve el valor de la velocidad en el eje x
     * @return valor de dx
     */
    public int getDx(){
        return this.dx;
    }

    /**
     * Asigna el valor de la velocidad en el eje x
     * @param dx Nuevo valor de dx
     */
    public void setDx(int dx){
        this.dx=dx;
    }

    /**
     *
     * METODOS NUEVOS PARA CONTROLAR QUE NO SE SALGAN LAS COORDENADAS DE LOS MARGENES
     *
     */

    public int IniciarEnMargenX(int x){
        if(x > Commons.BOARD_WIDTH){
            return Commons.BOARD_WIDTH;
        }else if(x < 0){
            return 0;
        }else{
            return x;
        }
    }
    public int IniciarEnMargenY(int y){
        if(y > Commons.BOARD_HEIGHT){
            return Commons.BOARD_HEIGHT;
        }else if(y < 0){
            return 0;
        }else{
            return y;
        }
    }
}

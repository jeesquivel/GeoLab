package Interface;


import javafx.scene.canvas.Canvas;

/**
 *
 * @author Jeison
 */
public class Lienzo extends Canvas{



    private boolean cambiar = false, dragged = false;

    public Lienzo(){
        super();
    }

    public Lienzo(double ancho, double alto){
        super(ancho, alto);

    }


}
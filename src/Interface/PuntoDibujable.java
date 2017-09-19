/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import geolab.negocios.Punto;
import javafx.scene.shape.Circle;

/**
 *
 * @author Jeison Esquivel
 */
public class PuntoDibujable extends Circle{

    private Punto centro;
    private static int RADIO=5;

    /**
     * Constructor
     */
    public PuntoDibujable(){
        super();
        this.centro=new Punto(1,3,7);
    }

    /**
     * Constructor
     * @param centro centro del punto
     */
    public PuntoDibujable( Punto centro) {
        super(centro.getX(), centro.getY(), RADIO);
        this.centro = centro;
    }

    /**
     * Obtener centro del punto
     * @return centro
     */
    public Punto getCentro() {
        return centro;
    }

    /**
     * Asigna un punto al centro del punto Dibujable
     * @param centro centro del punto dibujable
     */
    public void setCentro(Punto centro) {
        this.centro = centro;
        this.setCenterX(centro.getX());
        this.setCenterY(centro.getY());
    }

}

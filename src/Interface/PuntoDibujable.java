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
 * @author cancita
 */
public class PuntoDibujable extends Circle{

    Punto centro;
    private static int RADIO=5;

    public PuntoDibujable(){
        super(0,0,5);
        this.centro=new Punto(0,0);

    }


    public PuntoDibujable( Punto centro) {
        super(centro.getX(), centro.getY(), RADIO);
        this.centro = centro;
    }


    public Punto getCentro() {
        return centro;
    }

    public void setCentro(Punto centro) {
        this.centro = centro;
        this.setCenterX(centro.getX());
        this.setCenterY(centro.getY());
    }

    public static double getRADIO() {
        return RADIO;
    }

    public static void setRADIO(int RADIO) {
        PuntoDibujable.RADIO = RADIO;
    }
}

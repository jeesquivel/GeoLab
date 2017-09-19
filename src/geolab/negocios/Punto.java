/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geolab.negocios;

/**
 *
 * @author Jeison Esquivel
 */
public class Punto implements Objeto{
    
    private double x,y;

    /**
     * Ccnstructor
     * @param x CORDENADA x
     * @param y ORDENADA y
     * @param i GRueSOR DEL PUNTO
     */
    public Punto(double x, double y, int i){
    this.x = x;
    this.y = y;
    }

    /**
     * @param x cordenada x
     * @param y cordenaad y
     */
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Obtener valor x
     * @return double
     */
    public double getX() {
        return x;
    }

    /**
     * Obtener valor y
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * Metodo para imprimir el objeto Punto por medio de un string
     * @return string
     */
    @Override
    public String toString() {
        return "Punto{" + "x=" + x + ", y=" + y + '}';
    }
    
    
    
    
    
    
    
    
}

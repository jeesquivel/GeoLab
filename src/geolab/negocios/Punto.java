/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geolab.negocios;

/**
 *
 * @author jeison
 */
public class Punto implements Objeto{
    
    private double x,y;


    public Punto(double x, double y, int i){
    this.x = x;
    this.y = y;
    }
    
    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    
    

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Punto{" + "x=" + x + ", y=" + y + '}';
    }
    
    
    
    
    
    
    
    
}

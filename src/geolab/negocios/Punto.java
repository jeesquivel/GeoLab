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
    
    private int x,y;


    public Punto(){
    x=0;
    y=0;
    }
    
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
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

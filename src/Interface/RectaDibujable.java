package Interface;

import geolab.negocios.Punto;
import geolab.negocios.Recta;
import javafx.scene.shape.Line;

public class RectaDibujable extends Line {
    public PuntoDibujable getP1() {
        return P1;
    }

    public PuntoDibujable getP2() {
        return P2;
    }

    private PuntoDibujable P1;
    private PuntoDibujable P2;
    public RectaDibujable(){
        super();
    }

    public RectaDibujable(PuntoDibujable p1, PuntoDibujable p2){
        super(p1.getCentro().getX(),p1.getCentro().getY(),p2.getCentro().getX(),p2.getCentro().getY());
        this.P1=p1;
        this.P2=p2;
    }

    /**
     * verifica si un punto esta en la recta
     * @param punto
     * @return
     */
    public boolean estaInRecta(PuntoDibujable punto){
        if (punto==P1 || punto==P2){
            System.out.println("esta en recta");
            return true;
       }
        else
            System.out.println("esta en recta");
        return false;
    }


    public void setPuntoNuevo(PuntoDibujable punto){
        if (punto!=P1)
            P2=punto;
        else
            P1=punto;
    }

    public PuntoDibujable puntoDiferente(PuntoDibujable punto){
        if (punto==P1)
           return  P2;
        else
            return P1;
    }


    @Override
    public String toString() {
        return "RectaDibujable{" +
                "P1=" + P1 +
                ", P2=" + P2 +
                '}';
    }
}

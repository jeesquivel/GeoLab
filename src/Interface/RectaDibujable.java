package Interface;

import geolab.negocios.Punto;
import geolab.negocios.Recta;
import javafx.scene.shape.Line;

/**
 * Created by Jeison on 18 sep 2017.
 */
public class RectaDibujable extends Line{


    public Recta getRecta() {
        return recta;
    }

    public Recta recta;

    public RectaDibujable() {
    }

    public RectaDibujable(Punto p1, Punto p2, PuntoDibujable pDibujable1, PuntoDibujable pDibujable2) {
        super(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        recta=new Recta(pDibujable1.getCentro(),pDibujable2.getCentro());
    }


    public void setRecta(Recta recta,Punto p1, Punto p2) {
        this.recta = recta;
        this.setEndX(recta.getpFinal().getX());
        this.setEndY(recta.getpFinal().getY());
        this.setStartX(recta.getpInicio().getX());
        this.setStartY(recta.getpInicio().getY());
        recta.pInicio=p1;
        recta.pFinal=p2;

    }


    public boolean isInicio(Punto punto){
        return recta.pInicio==punto;
    }

    public Punto puntoOpuesto(Punto punto){
        if (punto==recta.getpInicio()){
            return recta.getpFinal();
        }else
            return punto;
    }

    public boolean PuntoInRecta(PuntoDibujable punto){
        if (recta.getpInicio()==punto.getCentro() || recta.getpFinal()==punto.getCentro())
            return true;
        else
            return false;
    }
}
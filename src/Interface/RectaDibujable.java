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

    public RectaDibujable(PuntoDibujable p1, PuntoDibujable p2) {
        super(p1.getCentro().getX(),p1.getCentro().getY(),p2.getCentro().getX(),p2.getCentro().getY());
        recta=new Recta(p1.getCentro(),p2.getCentro());
    }


    public void setRecta(Recta recta) {
        this.recta = recta;
        this.setEndX(recta.getpFinal().getX());
        this.setEndY(recta.getpFinal().getY());
        this.setStartX(recta.getpInicio().getX());
        this.setStartY(recta.getpInicio().getY());
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
package Interface;

import geolab.negocios.Punto;
import geolab.negocios.Recta;
import javafx.scene.shape.Line;

/**
 * Created by Jeison on 18 sep 2017.
 */
public class RectaDibujable extends Line{

    public Recta recta;

    /**
     * Obtiene la recta
     * @return recta
     */
    public Recta getRecta() {
        return recta;
    }

    /**
     * Constructor
     */
    public RectaDibujable() {
    }

    /**
     * Constructor
     * @param p1 Punto inicial
     * @param p2 Punto Final
     * @param pDibujable1 PuntoDibujable sobre la recta
     * @param pDibujable2 PuntoDibujable sobre la recta
     */
    public RectaDibujable(Punto p1, Punto p2, PuntoDibujable pDibujable1, PuntoDibujable pDibujable2) {
        super(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        recta=new Recta(pDibujable1.getCentro(),pDibujable2.getCentro());
    }


    /**
     * Asigna una recta a la Recat dibujable
     * @param recta recta
     * @param p1 punto sobre la recta inicio
     * @param p2 punto sobre la recta
     */
    public void setRecta(Recta recta,Punto p1, Punto p2) {
        this.recta = recta;
        this.setEndX(recta.getpFinal().getX());
        this.setEndY(recta.getpFinal().getY());
        this.setStartX(recta.getpInicio().getX());
        this.setStartY(recta.getpInicio().getY());
        recta.setpInicio(p1);
        recta.setpFinal(p2);
    }

    /**
     *  Verifica que un punto es inicial a una recta
     * @param punto
     * @return true or false
     */
    public boolean isInicio(Punto punto){
        return recta.getpInicio()==punto;
    }

    /**
     * Retorna el punto opuesto al seleccionado sobre la recta
     * @param punto punto
     * @return punto opuesto
     */
    public Punto puntoOpuesto(Punto punto){
        if (punto==recta.getpInicio()){
            return recta.getpFinal();
        }else
            return punto;
    }

    /**
     * Verifica si un punto forma parte de la recta
     * @param punto punto dibujable
     * @return true or false
     */
    public boolean PuntoInRecta(PuntoDibujable punto){
        if (recta.getpInicio()==punto.getCentro() || recta.getpFinal()==punto.getCentro())
            return true;
        else
            return false;
    }
}
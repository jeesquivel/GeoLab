package geolab.negocios;

import Interface.PuntoDibujable;

public class Recta implements  Objeto {
    Punto pInicio;
    Punto pFinal;




    public Recta(PuntoDibujable puntoDibujable, PuntoDibujable dibujable){};

    public Recta(Punto pInicio, Punto pFinal) {
        this.pInicio = pInicio;
        this.pFinal = pFinal;
    }

    public Punto getpInicio() {
        return pInicio;
    }

    public void setpInicio(Punto pInicio) {
        this.pInicio = pInicio;
    }

    public Punto getpFinal() {
        return pFinal;
    }

    public void setpFinal(Punto pFinal) {
        this.pFinal = pFinal;
    }
}

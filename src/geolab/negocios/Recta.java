package geolab.negocios;


public class Recta implements Objeto {
   public Punto pInicio;
   public Punto pFinal;

   public Punto extremo1;
    public Punto extremo2;

    public Recta(){};

    public Recta(Punto pInicio, Punto pFinal) {
        this.pInicio = pInicio;
        this.pFinal = pFinal;
    }

    public Punto getpInicio() {
        return pInicio;
    }


    public Punto getpFinal() {
        return pFinal;
    }

}

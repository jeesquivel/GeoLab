package geolab.negocios;

/**
 *  @author : Jeison Esquivel
 *  @version no se
 */
public class Recta implements Objeto {
    private Punto pInicio;
    private Punto pFinal;

    /**
     *  Asigna punto de inicio de la recta
     * @param pInicio Punto
     */
    public void setpInicio(Punto pInicio) {
        this.pInicio = pInicio;
    }

    /**
     *  Asigna punto  final de la recta
     * @param pFinal Punto
     */
    public void setpFinal(Punto pFinal) {
        this.pFinal = pFinal;
    }


    /**
     * Constructor por default
     */
    public Recta(){};


    /**
     *  Constructor
     * @param pInicio Puto de inicio
     * @param pFinal Punto de finalizacion
     */
    public Recta(Punto pInicio, Punto pFinal) {
        this.pInicio = pInicio;
        this.pFinal = pFinal;
    }

    /**
     * Obtiene el punto de inicio de la recta
     * @return Punto
     */
    public Punto getpInicio() {
        return pInicio;
    }

    /**
     * Obtiene el punto final de la recta
     * @return Punto
     */
    public Punto getpFinal() {
        return pFinal;
    }

}

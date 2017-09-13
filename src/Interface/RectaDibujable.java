package Interface;

import geolab.negocios.Recta;
import javafx.scene.shape.Line;

public class RectaDibujable extends Line {

    Recta rectaDibujable;

    public RectaDibujable(){
        super();
    }

    public RectaDibujable(PuntoDibujable puntoDibujable, PuntoDibujable dibujable){
        super();
    }

    public RectaDibujable(Recta rectaDibujable) {
        super();
        this.rectaDibujable.getpFinal();
        this.rectaDibujable.getpInicio();
    }
}

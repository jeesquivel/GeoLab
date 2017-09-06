package Interface;

import javafx.scene.layout.Pane;

public class LienzoPane extends Pane {

    private final Lienzo lienzo;

    public LienzoPane() {
        lienzo = new Lienzo(this.getWidth(), this.getHeight());
        getChildren().add(lienzo);
    }

    public LienzoPane(double ancho, double alto) {
        lienzo = new Lienzo(ancho, alto);
        getChildren().add(lienzo);
    }

    public Lienzo getLienzo() {
        return lienzo;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = snapSize(getWidth()) - x - snappedRightInset();
        final double h = snapSize(getHeight()) - y - snappedBottomInset();
        lienzo.setLayoutX(x);
        lienzo.setLayoutY(y);
        lienzo.setWidth(w);
        lienzo.setHeight(h);
    }
}
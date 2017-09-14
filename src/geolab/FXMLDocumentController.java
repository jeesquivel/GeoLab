package geolab;


import Interface.PuntoDibujable;
import Interface.RectaDibujable;
import geolab.negocios.Punto;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 *
 * @author chino
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public Label labelBarraEstado;
    @FXML
    public Pane PaneLienzo;

    private PuntoDibujable puntoSeleccionado;
    private ObservableList<RectaDibujable> rectasDibujadas=null;

    public ArrayList<PuntoDibujable> puntosRecta=new ArrayList<>();



    private enum BarraHerramienta{
        MOVER,
        PUNTOS,
        LINEA
    }

    private BarraHerramienta barraHerramientaSelecccionadaActualmente=BarraHerramienta.MOVER;

    @FXML
    public void handleLienzoMouseClick(MouseEvent event) {


        if ( barraHerramientaSelecccionadaActualmente==BarraHerramienta.PUNTOS)
            PaneLienzo.getChildren().add(new PuntoDibujable(new Punto(event.getX(),event.getY(),5)));
        
        else{
            if (barraHerramientaSelecccionadaActualmente==BarraHerramienta.MOVER){
                if ( puntoSeleccionado!=null )
                    puntoSeleccionado.setFill(Color.BLACK);
                    puntoSeleccionado=null;
            }
        }

        if ( barraHerramientaSelecccionadaActualmente==BarraHerramienta.LINEA) {
            puntoSeleccionado = seleccionaPunto(event.getX(), event.getY(), PaneLienzo);
            if (puntoSeleccionado != null) {
                if (!puntosRecta.contains(puntoSeleccionado)) {
                    puntosRecta.add(puntoSeleccionado);
                    puntoSeleccionado = null;
                }
            }
            if (puntosRecta.size() == 2) {
                RectaDibujable nuevaRecta=new RectaDibujable(puntosRecta.get(0), puntosRecta.get(1));
                PaneLienzo.getChildren().add(nuevaRecta);
                puntosRecta.clear();

            }
        }

        labelBarraEstado.setText("cliked");
    }


    /**
     * si el punto esta en una recta entonces al moverla redibuja la recta con el nuevo punto
     * @param punto
     */
    public void CambiarPuntoRecta(PuntoDibujable punto){
        if (rectasDibujadas.size()!=0){
           for ( RectaDibujable i: rectasDibujadas){
                PuntoDibujable P1= i.puntoDiferente(punto);
                PaneLienzo.getChildren().remove(i);
           }
        }
    }

    @FXML
    public void handleMenuArchivoCerrar(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    public void handleLienzoMouseDragged(MouseEvent event) {
        labelBarraEstado.setText("dibujando...!");

        if(barraHerramientaSelecccionadaActualmente == BarraHerramienta.MOVER
                && puntoSeleccionado != null) {
            int x =  valorMedio(0, (int) event.getX(),(int) PaneLienzo.getWidth());
            int y =  valorMedio(0, (int) event.getY(), (int)PaneLienzo.getHeight());

            rectasDibujadas=seleccionarRectas(puntoSeleccionado);
            Punto nuevoPunto= new Punto(x,  y);
            puntoSeleccionado.setCentro(nuevoPunto);
            CambiarPuntoRecta(puntoSeleccionado);

            puntoSeleccionado.setFill(Color.RED);



            double xc =pantallaCordenadasX(event,puntoSeleccionado);
            double yc=pantallaCordenadasY(event,puntoSeleccionado);
            double xPanel=CordenadasPantallaX(event,xc);
            double yPanel=CordenadasPantallaY(event,yc);

            labelBarraEstado.setText("Matematicas: ("+xc+"," +yc+")    CordenadasPanel: ("+xPanel+","+yPanel+")");


        }
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public int valorMedio(int x, int y, int z){
        if ( x<=y & y<=z ) return y;
        else
            if (x<=z & z<y) return z;
        else
            return x;
    }


    @FXML
    public void handlePaneLienzoOnDragDetectedAction(MouseEvent event) {
        labelBarraEstado.setText("Drag Detected");
        if(barraHerramientaSelecccionadaActualmente == BarraHerramienta.MOVER){
            puntoSeleccionado = seleccionaPunto(event.getX(), event.getY(), PaneLienzo);
            if (puntoSeleccionado!=null)
                rectasDibujadas=seleccionarRectas(puntoSeleccionado);
        }
    }

    private  PuntoDibujable seleccionaPunto(double x, double y, Pane PaneLienzo){
        ObservableList listaObjetos= PaneLienzo.getChildren();
        int cont=0;
        PuntoDibujable punto=null;
        while (punto==null && cont<listaObjetos.size()){
            Object objeto =listaObjetos.get(cont);
            if ( objeto instanceof PuntoDibujable  && distancia((PuntoDibujable) objeto,x,y)<15){
                punto=(PuntoDibujable) objeto;
                punto.setFill(Color.BLACK);
            }
            cont++;
        }
        return punto;
    }


    private  ObservableList seleccionarRectas(PuntoDibujable punto){
        ObservableList listaObjetos= PaneLienzo.getChildren();
        ObservableList rectas = null;
        int cont=0;
        while (punto==null && cont<listaObjetos.size()){
            Object objeto =listaObjetos.get(cont);
            if ( objeto instanceof RectaDibujable  && ((RectaDibujable) objeto).estaInRecta(punto)){
                rectas.add(objeto);
            }
            cont++;
        }
        return rectas;
    }





    private double distancia(PuntoDibujable objeto, double x, double y) {
        return distancia(objeto.getCenterX(),objeto.getCenterY(),x,y) ;
    }

    private double distancia(double x, double y, double x1, double y1){
        return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
    }


    @FXML
    public void handleHerramientaPunto(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.PUNTOS;
        labelBarraEstado.setText("punto...!");
    }

    @FXML
    public void handleHerramientaFlecha(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.MOVER;
        labelBarraEstado.setText("Flecha...!");
    }

    @FXML
    public void handleHerramientaLinea(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.LINEA;
        labelBarraEstado.setText("linea...!");
    }


    @FXML
    public double pantallaCordenadasX(MouseEvent event, PuntoDibujable punto){
        double x  = punto.getCenterX();
        double ancho= PaneLienzo.getWidth();
        double nueva;

        if (x<= ancho/2)
            nueva=-ancho/2+x;
        else
            nueva=x-ancho/2;

        return nueva;

    }
    @FXML
    public double pantallaCordenadasY(MouseEvent event, PuntoDibujable punto){
        double y  = punto.getCenterY();
        double ancho= PaneLienzo.getHeight();
        double nueva;
        if (y<= ancho/2)
            nueva=ancho/2-y;
        else
            nueva=-y+ancho/2;

       return nueva;

    }



    @FXML
    public double CordenadasPantallaX(MouseEvent event, double x){
        double ancho= PaneLienzo.getWidth();
        return x+ancho/2;
    }

    @FXML
    public double CordenadasPantallaY(MouseEvent event, double y){
        double ancho= PaneLienzo.getHeight();
        return ancho/2-y;
    }

    @FXML
    public void DibujarLinea(Pane PaneLienzo){
        Line ejeX = new Line(PaneLienzo.getPrefWidth()/2,0,PaneLienzo.getPrefWidth()/2,PaneLienzo.getPrefHeight());
        Line ejeY = new Line(0,PaneLienzo.getPrefHeight()/2,PaneLienzo.getPrefWidth(),PaneLienzo.getPrefHeight()/2);


        PaneLienzo.getChildren().add(ejeX);
        PaneLienzo.getChildren().add(ejeY);
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DibujarLinea(PaneLienzo);
    }

}
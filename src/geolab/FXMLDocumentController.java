package geolab;


import Interface.PuntoDibujable;
import Interface.RectaDibujable;
import geolab.negocios.Objeto;
import geolab.negocios.Punto;
import geolab.negocios.Recta;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.w3c.dom.css.Rect;

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


    private ArrayList<PuntoDibujable> puntosSeleccionados=new ArrayList<>();


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
            if (puntoSeleccionado!=null){
                puntosSeleccionados.add(puntoSeleccionado);
                puntoSeleccionado=null;
                if (puntosSeleccionados.size()==2){
                    PaneLienzo.getChildren().add(new RectaDibujable(puntosSeleccionados.get(0), puntosSeleccionados.get(1)));
                    puntosSeleccionados.clear();
                }
            }


        }

        labelBarraEstado.setText("cliked");
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

            Punto anterior=puntoSeleccionado.getCentro();
            Punto nuevoPunto= new Punto(x,  y);
            ObtenerRectasPunto(anterior,nuevoPunto);
            puntoSeleccionado.setCentro(nuevoPunto);


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


    private double distancia(PuntoDibujable objeto, double x, double y) {
        return distancia(objeto.getCenterX(),objeto.getCenterY(),x,y) ;
    }

    private double distancia(double x, double y, double x1, double y1){
        return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
    }

    /*
        para las rectas
     */

    /**
     * retorna las rectas que pasan por ese punto
     * @param
     * @return
     */
    public void  ObtenerRectasPunto(Punto puntoAnterior,Punto PuntoNuevo){
        ObservableList listaObjetos= PaneLienzo.getChildren();
        int cont=0;
        while (cont<listaObjetos.size()){
            Object objeto =listaObjetos.get(cont);
            if ( objeto instanceof RectaDibujable){
                PuntoDibujable puntoD=new PuntoDibujable(puntoAnterior);
                if (((RectaDibujable) objeto).PuntoInRecta(puntoD)){
                    Punto pOpuesto= ((RectaDibujable) objeto).puntoOpuesto(puntoAnterior);
                    if (((RectaDibujable) objeto).isInicio(puntoD.getCentro())){
                        Recta recta=new Recta(PuntoNuevo,pOpuesto);
                        recta=ajustarRecta(recta);
                        ((RectaDibujable) objeto).setRecta(recta);
                    }
                    else {
                        Recta recta=new Recta(((RectaDibujable) objeto).getRecta().getpInicio(), PuntoNuevo);
                        ((RectaDibujable) objeto).setRecta(ajustarRecta(recta));
                    }
                }
            }
            cont++;
        }

    }


    public Recta ajustarRecta(Recta recta){
            Punto inicioP= recta.getpFinal();
            Punto finalP=recta.getpInicio();
            Punto vDirector=new Punto(inicioP.getX()-finalP.getX(),inicioP.getY()-finalP.getY());
            Punto inicioN = null,finalN = null;

            /*
                Si x=0
             */
            double y1= inicioP.getY()+(-inicioP.getX()/vDirector.getX())*vDirector.getY();
            if (0<=y1 && y1<=PaneLienzo.getHeight()){
                inicioN= new Punto(0,y1);
            }else{
                double x;
                if (y1>PaneLienzo.getHeight()){
                     x=inicioP.getX()+((PaneLienzo.getHeight()-inicioP.getY())/vDirector.getY())*vDirector.getX();
                    inicioN= new Punto(x,PaneLienzo.getHeight());
                }else{
                     x=inicioP.getX()+((-inicioP.getY())/vDirector.getY())*vDirector.getX();
                    inicioN= new Punto(x,0);
                }

            }
            /*
                Si x=max
             */
        double y2= inicioP.getY()+((PaneLienzo.getWidth()-inicioP.getX())/vDirector.getX())*vDirector.getY();
        if (0<=y2 && y2<=PaneLienzo.getHeight()){
            finalN= new Punto(PaneLienzo.getWidth(),y2);
        }else{
            double x;
            if (y2>PaneLienzo.getHeight()){
                x=inicioP.getX()+((PaneLienzo.getHeight()-inicioP.getY())/vDirector.getY())*vDirector.getX();
                y2=PaneLienzo.getHeight();
                finalN= new Punto(x,y2);
            }else{
                x=inicioP.getX()+((-inicioP.getY())/vDirector.getY())*vDirector.getX();
                y2=0;
                finalN= new Punto(x,y2);
            }
            
        }
        return new Recta(inicioN,finalN);

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
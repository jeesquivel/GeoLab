package geolab;


import Interface.PuntoDibujable;
import Interface.RectaDibujable;
import geolab.negocios.Punto;
import geolab.negocios.Recta;
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
    private ArrayList<PuntoDibujable> puntosSeleccionados=new ArrayList<>();
    private enum BarraHerramienta{
        MOVER,
        PUNTOS,
        LINEA
    }
    private BarraHerramienta barraHerramientaSelecccionadaActualmente=BarraHerramienta.MOVER;

    /**
     * Selecciona los objetos del pane dependiento la herramienta seleccionada
     * @param event Evento del mouse
     */
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
                if (!puntosSeleccionados.contains(puntoSeleccionado)){
                    puntosSeleccionados.add(puntoSeleccionado);
                if (puntosSeleccionados.size()==2){
                    Punto punto1= puntosSeleccionados.get(0).getCentro();
                    Punto punto2= puntosSeleccionados.get(1).getCentro();
                    Recta recta= new Recta(punto1, punto2);
                    recta= ajustarRecta(recta);
                    PaneLienzo.getChildren().add(new RectaDibujable(recta.getpInicio(),recta.getpFinal(),puntosSeleccionados.get(0),puntosSeleccionados.get(1)));
                    puntosSeleccionados.clear();
                }}
                puntoSeleccionado=null;
            }
        }
        labelBarraEstado.setText("cliked");
    }


    /**
     * Cierra el programa por medio de  opcion dentro de la barra de menu en archivo
     * @param event Evento de accion
     */
    @FXML
    public void handleMenuArchivoCerrar(ActionEvent event) {
        Platform.exit();
    }


    /**
     * Ejecuta las acciones al mover el mmouse... mueve puntos  y
     * si los punto s pertenecen a una recta, entonces actualiza la recta
     * @param event Mouse Event
     */
    @FXML
    public void handleLienzoMouseDragged(MouseEvent event) {
        labelBarraEstado.setText("dibujando...!");
        if(barraHerramientaSelecccionadaActualmente == BarraHerramienta.MOVER
                && puntoSeleccionado != null) {
            int x =  valorMedio(0, (int) event.getX(),(int) PaneLienzo.getWidth());
            int y =  valorMedio(0, (int) event.getY(), (int)PaneLienzo.getHeight());
            Punto anterior=puntoSeleccionado.getCentro();
            Punto nuevoPunto= new Punto(x,  y);
            modificarRectas(anterior,nuevoPunto);
            puntoSeleccionado.setCentro(nuevoPunto);
            puntoSeleccionado.setFill(Color.RED);
            double xc =pantallaCordenadasX(event,puntoSeleccionado);
            double yc=pantallaCordenadasY(event,puntoSeleccionado);
            double xPanel=CordenadasPantallaX(event,xc);
            double yPanel=CordenadasPantallaY(event,yc);
            labelBarraEstado.setText("Matematicas: ("+xc+"," +yc+")         CordenadasPanel: ("+xPanel+","+yPanel+")");
        }
    }


    /**
     * Retorna el valor medio entre 3 valores
     * @param x entero
     * @param y entero
     * @param z entero
     * @return un entero entre x -- z
     */
    public int valorMedio(int x, int y, int z){
        if ( x<=y & y<=z ) return y;
        else
            if (x<=z & z<y) return z;
        else
            return x;
    }


    /**
     * Selecciona un punto al dibujar en el linezo con el mouse presionado
     * @param event mouse event
     */
    @FXML
    public void handlePaneLienzoOnDragDetectedAction(MouseEvent event) {
        labelBarraEstado.setText("Drag Detected");
        if(barraHerramientaSelecccionadaActualmente == BarraHerramienta.MOVER){
            puntoSeleccionado = seleccionaPunto(event.getX(), event.getY(), PaneLienzo);
        }
    }

    /**
     * Selecciona un punto, si al presionar el mouse sobre el pane este esta dentro del punto...
     * @param x cordenada x del mouse
     * @param y cordenada y del mouse
     * @param PaneLienzo paneLienzo
     * @return un punto o Null
     */

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

    /**
     *  Calcula la distancia entre dos puntos
     * @param objeto Punto seleccionado por el mouse
     * @param x cordenada x
     * @param y cordenada y
     * @return distancia entre dos puntos
     */
    private double distancia(PuntoDibujable objeto, double x, double y) {
        return distancia(objeto.getCenterX(),objeto.getCenterY(),x,y) ;
    }

    /**
     * Calcula la distancia entre dos puntos dadas sus respectivas cordenadas
     * @param x double
     * @param y double
     * @param x1 double
     * @param y1 double
     * @return distancia entre dos puntos
     */
    private double distancia(double x, double y, double x1, double y1){
        return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
    }

    /*
        para las rectas
     */

    /**
     * Modifica las rectas al mover los puntos contenidos en ellas
     * @param puntoAnterior Punto viejo de la recta
     * @param PuntoNuevo Punto nuevo donde pasará la recta
     */
    public void modificarRectas(Punto puntoAnterior, Punto PuntoNuevo){
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
                        ((RectaDibujable) objeto).setRecta(recta,PuntoNuevo,pOpuesto);
                    }
                    else {
                        Punto puntoInicio=((RectaDibujable) objeto).getRecta().getpInicio();
                        Recta recta=new Recta(puntoInicio, PuntoNuevo);
                        recta=ajustarRecta(recta);
                        ((RectaDibujable) objeto).setRecta(recta,puntoInicio,PuntoNuevo);
                    }
                }
            }
            cont++;
        }
    }

    /**
     * Ajusta la recta para que se extienda a lo largo del panel por medio de ecuacion parametrica
     * ya que sino entonces quedará copmo segmento
     * @param recta recta, que por ahora sería segmento
     * @return recta ajustada sobre el panel
     */
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


    /**
     * Habilita la herramientoa de Dibujar  puntos
     * @param event Action event
     */
    @FXML
    public void handleHerramientaPunto(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.PUNTOS;
        labelBarraEstado.setText("punto...!");
        puntosSeleccionados.clear();
    }

    /**
     * Habilita la herramienta de mover
     * @param event ActionEvent
     */
    @FXML
    public void handleHerramientaFlecha(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.MOVER;
        labelBarraEstado.setText("Flecha...!");
        puntosSeleccionados.clear();

    }

    /**
     * Hablita la herramienta de dibujar una recta
     * @param event
     */
    @FXML
    public void handleHerramientaLinea(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.LINEA;
        labelBarraEstado.setText("linea...!");
        puntosSeleccionados.clear();

    }

    /**
     * Convierte las cordenadax del panel a unas cordenadas x cartesianas
     * @param event Evento del mouse
     * @param punto Punto seleccionado por el mouse
     * @return
     */
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

    /**
     * Convierte las cordenadas y delñ panel a unas  y cordenadas cartesianas
     * @param event Evento del mouse
     * @param punto Punto seleccionado por el mouse
     * @return
     */
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

    /**
     * pasa de cordenada x cartesiana a cordenadas x del panel
     * @param event
     * @param x
     * @return
     */
    @FXML
    public double CordenadasPantallaX(MouseEvent event, double x){
        double ancho= PaneLienzo.getWidth();
        return x+ancho/2;
    }

    /**
     * pasa de cordenada "y" cartesiana a cordenadas "y" del panel
     * @param event
     * @param y
     * @return
     */
    @FXML
    public double CordenadasPantallaY(MouseEvent event, double y){
        double ancho= PaneLienzo.getHeight();
        return ancho/2-y;
    }


    /**
     * Dibuja los ejes cordenados sobre el panelLienzo
     * @param PaneLienzo el panel
     */
    @FXML
    public void DibujarLinea(Pane PaneLienzo){
        Line ejeX = new Line(PaneLienzo.getPrefWidth()/2,0,PaneLienzo.getPrefWidth()/2,PaneLienzo.getPrefHeight());
        Line ejeY = new Line(0,PaneLienzo.getPrefHeight()/2,PaneLienzo.getPrefWidth(),PaneLienzo.getPrefHeight()/2);
        ejeX.setStroke(Color.BLUE);
        ejeY.setStroke(Color.BLUE);
        PaneLienzo.setStyle("-fx-background: #FFFFFF;");
        PaneLienzo.getChildren().add(ejeX);
        PaneLienzo.getChildren().add(ejeY);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DibujarLinea(PaneLienzo);
    }

}
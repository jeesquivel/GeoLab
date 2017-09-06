/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geolab;


import Interface.PuntoDibujable;
import geolab.negocios.Punto;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
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


    private PuntoDibujable puntoSeleccionado=null;

    private enum BarraHerramienta{
        FLECHA,
        PUNTOS,
        LINEA
    }

    private BarraHerramienta barraHerramientaSelecccionadaActualmente=BarraHerramienta.FLECHA;

    @FXML
    public void handleLienzoMouseClick(MouseEvent event) {
        if ( barraHerramientaSelecccionadaActualmente==BarraHerramienta.PUNTOS)
            PaneLienzo.getChildren().add(new PuntoDibujable(new Punto(event.getX(),event.getY(),5)));
        if( barraHerramientaSelecccionadaActualmente==BarraHerramienta.FLECHA & puntoSeleccionado!=null){
            puntoSeleccionado.setCentro(new Punto(event.getX(),event.getY(),5));
        }

        labelBarraEstado.setText("cliked...");
    }


    @FXML
    public void handleMenuArchivoCerrar(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    public void handleLienzoMouseDragged(MouseEvent event) {
        labelBarraEstado.setText("dibujando...!");
    }


    @FXML
    public void handleLienzoOnDraggedAction(MouseEvent event) {

        labelBarraEstado.setText("dibujando...!");
        if ( barraHerramientaSelecccionadaActualmente==BarraHerramienta.FLECHA ){
            puntoSeleccionado=seleccionaPunto(event.getX(),event.getY(),PaneLienzo);
        }

    }

    private  PuntoDibujable seleccionaPunto(double x, double y, Pane PaneLienzo){
        ObservableList listaObjetos= PaneLienzo.getChildren();
        int cont=0;
        PuntoDibujable punto = new PuntoDibujable();

        while (punto==null && cont<listaObjetos.size()){
            Object objeto =listaObjetos.get(cont);
            if ( objeto instanceof PuntoDibujable  && distancia((PuntoDibujable) objeto,x,y)<5){
                puntoSeleccionado=(PuntoDibujable) objeto;
            }

        }

        return punto;

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
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.FLECHA;
        labelBarraEstado.setText("Flecha...!");
    }

    @FXML
    public void handleHerramientaLinea(ActionEvent event){
        barraHerramientaSelecccionadaActualmente=BarraHerramienta.LINEA;

        labelBarraEstado.setText("linea...!");
    }



    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

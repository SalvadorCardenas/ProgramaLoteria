package com.example.proyecto2022tap2;

import Models.Conexion;
import Vistas.Loteria;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.fxml.FXML;

import java.io.IOException;

public class Main extends Application {

    Conexion Conexion = new Conexion();

    private MenuBar menuBarMain;                                    //Declarar todo lo que ocupo
    private Menu menCompetencia1, menCompetencia2;
    private MenuItem mitLoteria, mitParseador;
    private VBox vBox;


    @Override
    public void start(Stage ventanaStage) throws IOException {
        //    Parent root = FXMLLoader.load(getClass().getResource("sample"));
        // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        //   Scene scene = new Scene(fxmlLoader.load(), 320, 240);
     //   vBox = new VBox();
     //   menuBarMain = new MenuBar();
     //   menCompetencia1 = new Menu("Competencia 1");
     //   menCompetencia2 = new Menu("Competencia 2");
     //   menuBarMain.getMenus().addAll(menCompetencia1, menCompetencia2);             //Inicializar Menus

      //  mitLoteria = new MenuItem("Loteria");
       // mitLoteria.setOnAction(actionEvent -> Eventos(1));
     //   menCompetencia1.getItems().addAll(mitLoteria);

   //     vBox.getChildren().add(menuBarMain);

     //   ventanaStage.setTitle("Hello!");
     //   ventanaStage.setScene(new Scene(vBox, 30, 50));
     //   ventanaStage.show();
     //   ventanaStage.setMaximized(true);

        new Loteria();
    }

  //  Conexion.crearConexion();

    public static void main(String[] args) {
        launch();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import LostSquad.Model.Game.Game;
import LostSquad.View.MainScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author vava3
 */
public class Controller {
    
    private final Button exit = new Button("EXIT");
    
    @FXML
    private BorderPane borderPaneRoot;
    
    private static Game game;
    
    private ControlPlayer controlPlayerController;
    
    public BorderPane getBorderPaneRoot(){
        return this.borderPaneRoot;
    }
    
    public void changeScene(String dir,String Place) throws IOException{
        FXMLLoader load = new FXMLLoader(MainScene.class.getResource(dir+".fxml"));
        this.borderPaneRoot.getChildren().remove(0);
        this.borderPaneRoot.setCenter(load.load());
        this.controlPlayerController=load.getController();
        this.controlPlayerController.getScene().prefHeightProperty().bind(borderPaneRoot.getScene().heightProperty());
        this.controlPlayerController.getScene().prefWidthProperty().bind(borderPaneRoot.getScene().widthProperty());
        this.controlPlayerController.changeVue(Place);
        this.controlPlayerController.setBindStats();
        this.controlPlayerController.loadItemListView();
        this.controlPlayerController.refreshMapController();
    }
    
    
    public Game getGame(){
        return Controller.game;
    }
    
    @FXML
    private void play(ActionEvent event) throws IOException{
        game= new Game();
        game.enterPlace("start");
        game.main();
        FXMLLoader load = new FXMLLoader(MainScene.class.getResource("sceneMove.fxml"));
        this.borderPaneRoot.getChildren().remove(0,this.borderPaneRoot.getChildren().size());
        this.borderPaneRoot.setCenter(load.load());
        this.controlPlayerController=load.getController();
        controlPlayerController.setController(this);
        this.controlPlayerController.getScene().prefHeightProperty().bind(borderPaneRoot.getScene().heightProperty());
        this.controlPlayerController.getScene().prefWidthProperty().bind(borderPaneRoot.getScene().widthProperty());    
        this.controlPlayerController.changeVue("Start");
        this.controlPlayerController.setBindStats();      
    }
    
    public void exit(){
        this.borderPaneRoot.getChildren().remove(0);
        AnchorPane gameOver = new AnchorPane();
        ImageView imgOver = new ImageView();
        imgOver.setImage(new Image("/LostSquad/View/AllView/img/gameover.png"));
        gameOver.getChildren().addAll(imgOver,exit);
        this.borderPaneRoot.setCenter(gameOver);
        imgOver.fitHeightProperty().bind(this.borderPaneRoot.getScene().heightProperty());
        imgOver.fitWidthProperty().bind(this.borderPaneRoot.getScene().widthProperty()); 
        exit.setLayoutX((this.borderPaneRoot.getScene().getWidth()/2)-exit.getWidth());
        System.out.println(exit.getWidth());
        exit.setLayoutY((this.borderPaneRoot.getScene().getHeight()/2));
        this.exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
    }
}

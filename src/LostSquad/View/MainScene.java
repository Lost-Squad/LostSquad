/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.View;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author vava3
 */
public class MainScene extends BorderPane{
    
    private FXMLLoader fxmlLoader;
    
    public MainScene(){
        
    }
    
    //creation de scene principale
    public MainScene(String dir){
        try {
            fxmlLoader = new FXMLLoader(MainScene.class.getResource(dir+".fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        }
        catch(IOException ioe) {
            System.err.println("MyBorderPane2 constructor error");
            ioe.printStackTrace();
        }
    }
}


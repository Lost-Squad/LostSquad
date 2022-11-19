/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DescBoxController{
    @FXML
    private Text titleText;
    @FXML
    private Text descText;
    @FXML
    private AnchorPane descBox;
      
    public void setDesc(String n){
        this.descText.setText(n);
    }
    public void setTitle(String n){
        this.titleText.setText(n);
    }
    public AnchorPane getDescBox(){
       return this.descBox;
    }
}

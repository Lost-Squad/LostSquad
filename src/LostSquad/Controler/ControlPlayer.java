/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import LostSquad.Model.Characters.Entity;
import LostSquad.Model.Characters.Npc;
import LostSquad.Model.Characters.Trader;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import LostSquad.Model.Game.Game;
import LostSquad.Model.Room.Door;
import LostSquad.Model.Room.DoorLock;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;


/**
 *
 * @author vava3
 */
public class ControlPlayer {
    
    private final double HEAL_ARMOR_MAX=100.0;
    
    //creation de chaque bordure
    BorderStroke bsGold = new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(3.0));
    BorderStroke bsRed = new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(3.0));
    BorderStroke bsGreen = new BorderStroke(Color.GREENYELLOW, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(3.0));
    BorderStroke bsBlue = new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(3.0));
    BorderStroke bsWhite = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(0.0));
    
    //cr??ation de liste d'??l??ments contenu dans la scene visuel
    private List<Pane> tabLabelDoor = new ArrayList<>();
    private List<Pane> tabLabelEnemy = new ArrayList<>();
    private List<Pane> tabLabelMeuble = new ArrayList<>();
    
    //cette variable correspond au model(game)
    private  static Game game;
    //currentPlace est le nom d'un fichier fxml, ceci correspond a la Place o?? se situe le joueur dans le model
    private static String currentPlace="Start";
    //cette varible correspond au controlParent (class Controller)
    private static Controller control;
    
    //creation d'un converteur pour l'affichage des degats, argents etc... pour les stats du joueur
    StringConverter<Number> converter = new NumberStringConverter();
    
    
    //on r??cup??re i??i tous les fx:id du fxml
    @FXML
    private Pane paneControlPlayer;
    @FXML
    private BorderPane scene;
    @FXML
    private BorderPane paneVueGame;
    @FXML
    private ProgressBar PV;
    @FXML
    private ProgressBar Armor;
    @FXML
    private Label Money;
    @FXML
    private Label minDps;
    @FXML
    private Label maxDps;
    @FXML
    private Label nbLuck;
    @FXML
    private Label nbAccuracy;
    @FXML
    private Label nbAmmo;
    @FXML
    private InventoryListViewController inventoryController;
    @FXML
    private ContainerListViewController containerController;
    @FXML
    private HBox toolbar;
    @FXML 
    private Pane statView;
    @FXML
    private MapViewController mapController;
    
    private DescBoxController descBoxController;
    
    private ProgressBar PVEnemy;
    private ProgressBar ArmorEnemy;
    
    private boolean isSet = false;
    
    //permet au controlleur Parent de r??cup??rer toutes la scene
    public BorderPane getScene(){
        return this.scene;
    }
    
    //configure la variable control ainsi que game cette fonction 
    //est utiliser dans le controlleur parent lorsqu'on clique sur le button play
    public void setController(Controller contr) throws IOException{
        ControlPlayer.control=contr; 
        ControlPlayer.game = ControlPlayer.control.getGame();
        inventoryController.setGame(game);
        mapController.loadGame(game);       
    }

    /*
    On effacer le tableau des DoorLockKey, ensuite on supprime le premier child de paneVueGame (qui correspond ?? la scene visuel)
    puis on lui charge et donne une nouvelle scene, avec un string qui correspond ?? la nouvelle place.
    Ensuite on lance la fontion qui r??cupere chaque ??l??ment de cette nouvelle paneVueGame.
    Et pour finir on ajoute la description box et on set l'affichage avec un string pour le titre et un autre pour la description
    */
    public void changeVue(String dir) throws IOException{
        inventoryController.clearDoorLockList();
        this.paneVueGame.getChildren().remove(0);
        this.paneVueGame.setCenter(FXMLLoader.load(Controller.class.getResource("/LostSquad/View/AllView/"+dir+".fxml")));
        this.setItemVue();
        printDescBox();
        this.descBoxController.setTitle(dir);
        this.descBoxController.setDesc(ControlPlayer.game.getCurrentPlace().getDesc());
        //c'est pour un easter egg
        if(dir.equalsIgnoreCase("Tibo")){
            this.descBoxController.setDesc("WESH");
        }
    }
    /*
    permet de ranger chaque ??l??ment visuel dans des listes correspondantes
    on teste chaque ??l??ment du AnchoPane qui r??presente le visuel si c'est un Pane et que sont id est :
    direc alors chaque ??l??ments seont des directions : porte tunnel escalier ..
    enemy alors tous les ??l??ments sont pr??t ?? combattre
    meuble alors les ??l??ments sont des coffres, bureau.. tous objets pouvant ??tre fouill??
    direcLock sont aussi des ??l??ments de direction mais on ajoute ce pane aussi dans un tableau dans le controlleur de l'inventaire 
    pour pouvoir faire un drag and drop sur toutes les DoorLockKey qui on besoin d'une cl??
    Et pour finir on teste si un de ces ??l??ments ne seraient pas une progresse Bar, ces progress bar repr??sente la vie et l'armure
    de l'ennemie lors des phases de combat
    */
    public void setItemVue(){ 
        this.tabLabelDoor = new ArrayList<>();
        this.tabLabelEnemy = new ArrayList<>();
        this.tabLabelMeuble = new ArrayList<>();
        AnchorPane children = (AnchorPane)this.paneVueGame.getChildren().get(0);
        for(int i=0;i<children.getChildren().size();i++){
            Object obj = children.getChildren().get(i);
            if(obj instanceof Pane ){
                Pane  img = (Pane )obj;
                if(img.getId().equalsIgnoreCase("direc")){
                  this.tabLabelDoor.add(img);  
                }else if(img.getId().equalsIgnoreCase("enemy")){
                  this.tabLabelEnemy.add(img);  
                }else if(img.getId().equalsIgnoreCase("meuble")){
                  this.tabLabelMeuble.add(img);  
                }else if(img.getId().equalsIgnoreCase("direcLock")){
                  this.tabLabelDoor.add(img);
                  inventoryController.addDoorLock(img);
                }          
            }
            if(obj instanceof ProgressBar){//si cette variable existe cela signifie qu'on est dans la scene de Combat
                if(((ProgressBar)obj).getId().equalsIgnoreCase("PVEnemy")){
                    this.PVEnemy = ((ProgressBar)obj);
                }else if(((ProgressBar)obj).getId().equalsIgnoreCase("ArmorEnemy")){
                    this.ArmorEnemy = ((ProgressBar)obj);
                    String dir=((ImageView)(this.tabLabelEnemy.get(0)).getChildren().get(0)).getId();                           
                    Npc targetNpc = (Npc)game.getCurrentPlace().findCharacterByName(dir);
                    if(targetNpc.getKevlar()!=null){
                        this.ArmorEnemy.setProgress((double)targetNpc.getKevlar().getProtection()/100.0);
                    }else{
                        this.ArmorEnemy.setProgress(0);
                    }
                }
            }
        }        
    }
    
    /*
    cr??ation de binding qui r??presente la vie la protection l'argents et si on est dans la sceneAttack
    on pourra voir les d??gats min et max possible, la pr??cision, les chance de faire un critique, et le nombre de munition
    les bindBidirectional sont utilis?? juste pour pouvoir mettre une option convecteur et vu que chaque de ces ??l??ments sont des label
    imposible de modifier la valeur dans le model et m??me si il arrivait cette varible n'a aucune incidence car elle est juste setter et getter
    jamais utiliser dans une condition ou autre.
    */
    public void setBindStats(){
        PV.progressProperty().bind(ControlPlayer.game.getPlayer().getLifeProperty().divide(HEAL_ARMOR_MAX));
        Armor.progressProperty().bind(ControlPlayer.game.getPlayer().getArmorProperty().divide(HEAL_ARMOR_MAX));
        Money.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getMoneyProperty(),converter);
        if(this.minDps!=null){//si cette variable n'est pas null cela signifie qu'on est dans la scene de Combat
            this.minDps.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getMinDps(),converter);
            this.maxDps.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getMaxDps(),converter);
            this.nbLuck.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getNbControl(),converter);
            this.nbAccuracy.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getNbAccuracy(),converter);
            this.nbAmmo.textProperty().bindBidirectional(ControlPlayer.game.getPlayer().getNbMunition(),converter);
        }      
    }
    
    //fonction appeler lorsqu'on clique sur le button exit
    //elle ferme l'application
    @FXML
    private void exit(ActionEvent event){
       Platform.exit();
    }
    
    /*
    lorsqu'on a cliqu?? sur un boutton d'action on ajoute un peu visibilit?? sur les ??l??ments pouvant ??tre utilis?? par l'action
    ainsi ces ??l??ments recoit un setOnMouseClicked pour qu'on puisse cliquer dessus et donc produire l'action en question
    */
    @FXML
    private void btnAction(ActionEvent event) throws IOException{
        /*
        lorqu'on click sur un boutton on remet les bordure des pane du panVueGame non visible
        et un setOnMouseClicked vide permet d'effacer les setOnMouseClicked des panes qui on etait definit par d'autres bouttons
        */
        for(int i=0;i<tabLabelDoor.size();i++){
                    tabLabelDoor.get(i).setBorder(new Border(bsWhite));
                    tabLabelDoor.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                    });
                }
        for(int i=0;i<tabLabelEnemy.size();i++){
            tabLabelEnemy.get(i).setBorder(new Border(bsWhite));
            tabLabelEnemy.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
            });
        }
        for(int i=0;i<tabLabelMeuble.size();i++){
            tabLabelMeuble.get(i).setBorder(new Border(bsWhite));
            tabLabelMeuble.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
            });
        }
        this.printDescBox();
        //on r??cup??re l'id du Button et on teste chaque cas
        switch(((Button)event.getSource()).getId()){
            case "btnGo":
                descBoxController.setDesc("you can interact with the accentuated areas");
                descBoxController.setTitle("Description Action Go");
                for(int i=0;i<tabLabelDoor.size();i++){
                    tabLabelDoor.get(i).setBorder(new Border(bsGold));
                    tabLabelDoor.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        try {
                            if(game.verrifEnemy()){//on peut se d??placer de Place en Place seulement si il y a plus d'enemy
                                this.descBoxController.setTitle("Nope");
                                this.descBoxController.setDesc("kill enemy in room before leaving this place");
                            }else{
                                functGo(dir);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case "btnAttack":
                descBoxController.setDesc("you can attack with the accentuated areas");
                descBoxController.setTitle("Description Action Go");
                for(int i=0;i<tabLabelEnemy.size();i++){
                    tabLabelEnemy.get(i).setBorder(new Border(bsRed));
                    tabLabelEnemy.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        try {
                            Npc targetNpc = (Npc)game.getCurrentPlace().findCharacterByName(dir);
                            if(targetNpc.getLife()<=0){
                                this.descBoxController.setTitle("Alerte");
                                this.descBoxController.setDesc("he is dead");
                            }else{
                                //le changeScene permet d'aller sur la scene de Combat(sceneAttack.fxml)
                                control.changeScene("SceneAttack",dir);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);   
                        }
                    });
                }        
                break;
            case "btnLook":
                /*
                lorqu'on clique simplement sur le bouton une description de la salle actuelle s'affiche
                et lorsque vous cliquez sur un des panes mis en avant sa petite description est affich?? 
                */
                descBoxController.setDesc(game.getCurrentPlace().getLookText());
                descBoxController.setTitle("Description Place");
                for(int i=0;i<tabLabelDoor.size();i++){
                    tabLabelDoor.get(i).setBorder(new Border(bsBlue));
                    tabLabelDoor.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        descBoxController.setDesc(game.getCurrentPlace().findExitByName(dir).getDesc());
                        descBoxController.setTitle(dir);
                    });
                }
                for(int i=0;i<tabLabelEnemy.size();i++){
                    tabLabelEnemy.get(i).setBorder(new Border(bsBlue));
                    tabLabelEnemy.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        descBoxController.setDesc(game.getCurrentPlace().findCharacterByName(dir).getDesc());
                        descBoxController.setTitle(dir);
                    });
                }
                for(int i=0;i<tabLabelMeuble.size();i++){
                    tabLabelMeuble.get(i).setBorder(new Border(bsBlue));
                    tabLabelMeuble.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        System.out.println(dir);
                        descBoxController.setDesc(game.getCurrentPlace().findContainer(dir).getDesc());
                        descBoxController.setTitle(dir);
                    });
                }
                break;
            case "btnLoot":
                descBoxController.setDesc("you can loot with the accentuated areas");
                descBoxController.setTitle("Description Action Loot");
                for(int i=0;i<tabLabelEnemy.size();i++){
                    tabLabelEnemy.get(i).setBorder(new Border(bsGreen));
                    tabLabelEnemy.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        eraseContainerView();
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        Entity enemy = game.getCurrentPlace().findCharacterByName(dir);
                        if(enemy.getLife()<=0 || enemy instanceof Trader)
                        {
                            FXMLLoader containerItemListView = new FXMLLoader(Controller.class.getResource("/LostSquad/View/ContainerListView.fxml"));
                            try {
                                if(isSet == false)
                                {
                                    isSet = true;
                                    toolbar.getChildren().add(containerItemListView.load());
                                    containerController = ((ContainerListViewController)containerItemListView.getController());
                                    if(enemy instanceof Trader )
                                    {
                                        containerController.setContainer(enemy.getContainer(), inventoryController, enemy.getName());
                                    }
                                    else
                                    {
                                        containerController.setContainer(enemy.getContainer(), inventoryController);
                                    }
                                    containerController.getVBoxContainer().prefWidthProperty().bind(toolbar.prefWidthProperty().subtract(statView.prefWidthProperty().add(paneControlPlayer.prefWidthProperty())));
                                    inventoryController.setContainer(containerController);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            toolbar.getChildren().add(new VBox ());
                            try {
                                this.printDescBox();
                            } catch (IOException ex) {
                                Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            this.descBoxController.setTitle(dir);
                            this.descBoxController.setDesc("he is not dead");
                        }
                    });
                }
                for(int i=0;i<tabLabelMeuble.size();i++){
                    tabLabelMeuble.get(i).setBorder(new Border(bsGreen));
                    tabLabelMeuble.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        eraseContainerView();
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        FXMLLoader containerItemListView = new FXMLLoader(Controller.class.getResource("/LostSquad/View/ContainerListView.fxml"));
                        try {
                            if(isSet == false)
                            {
                                isSet = true;
                                toolbar.getChildren().add(containerItemListView.load());
                                containerController = ((ContainerListViewController)containerItemListView.getController());
                                containerController.setContainer(game.getCurrentPlace().findContainer(dir), inventoryController);
                                containerController.getVBoxContainer().prefWidthProperty().bind(toolbar.prefWidthProperty().subtract(statView.prefWidthProperty().add(paneControlPlayer.prefWidthProperty())));
                                inventoryController.setContainer(containerController);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case "btnAttackBis"://ce Button existe seulement dans la scene d'acttack
                this.descBoxController.setDesc("Kill him");
                this.descBoxController.setTitle("Description Enemy");  
                for(int i=0;i<tabLabelEnemy.size();i++){
                    tabLabelEnemy.get(i).setBorder(new Border(bsRed));
                    tabLabelEnemy.get(i).setOnMouseClicked((MouseEvent mouseEvenClick) -> {
                        String dir=((ImageView)((Pane)mouseEvenClick.getSource()).getChildren().get(0)).getId();
                        Npc targetNpc = (Npc)game.getCurrentPlace().findCharacterByName(dir);
                        functAttack(targetNpc);
                    });
                }
                break;
            default:
                System.err.println("error");
        }
    }
    
    /*
    lance la fonction goMain avec un string direction (dir est id de l'imageView d'une porte) cette fonction change
    la position du joueur dans le model.
    Pour changer la vue on r??cuperer le nom de la nouvelle place, le nom de l'ancienne place, et le nom de la porte utilis??
    et on concat c'est trois String pour r??cup??rer le fxml correspondant ?? la nouvelle place. 
    Par exemple vous changer de place et qu'elle s'appelle Metro1, avant votre place ??tait
    Start, et pour vous rendre dans Metro1 vous avez pris la porte forward, la nouveau fxml ?? charger est
    Metro1_Start_forward.fxml
    */
    private void functGo(String dir) throws IOException{
        Door targetedDoor = ControlPlayer.game.getCurrentPlace().findExitByName(dir);
        if(targetedDoor != null){
            String namePlaceCurrent = ControlPlayer.game.getCurrentPlace().getName();
            ControlPlayer.game.goMain(targetedDoor);
            if(targetedDoor instanceof DoorLock){
                if(!((DoorLock)targetedDoor).isLock()){
                    ControlPlayer.currentPlace = targetedDoor.getPlace()+"_"+namePlaceCurrent+"_"+dir;
                    this.changeVue(ControlPlayer.currentPlace);
                    mapController.refreshMap(ControlPlayer.currentPlace);
                }else{
                    descBoxController.setDesc("this door is look, try to use a key then reuse the door ");
                    descBoxController.setTitle("Alert");
                }
            }else{
                ControlPlayer.currentPlace = targetedDoor.getPlace()+"_"+namePlaceCurrent+"_"+dir;
                this.changeVue(ControlPlayer.currentPlace);
                mapController.refreshMap(ControlPlayer.currentPlace);
            }
            
        }else{
            descBoxController.setDesc("il y a une erreur");
            descBoxController.setTitle("Alert");
        }   
    }
    
    /*
    Cette permet d'attaquer un ennemi, on r??duit la vie de l'ennemie dans le model, puis on diminue ses progresses Bar de 
    avec le reste de point de vie/point d'armure qu'on divise par le max afin d'avoir un r??el compris entre 0 et 1.
    Apr??s que le joueur est attaqu?? on v??rifie si il est pas bl??ss?? (ce qui retire 1 point vie a chaque attack),
    on v??rifie si l'ennemie est mort si oui on change de scene sinon l'ennemi attaque et on v??rifie si le joueur est mort.
    */
    private void functAttack(Npc targetNpc){
        if(targetNpc==null){
            descBoxController.setDesc("il y a une erreur");
            descBoxController.setTitle("Alert");  
        }else{
            game.attackMain(targetNpc);
            this.PVEnemy.setProgress((double)targetNpc.getLife()/HEAL_ARMOR_MAX);
            if(targetNpc.getKevlar()!=null){
                this.ArmorEnemy.setProgress((double)targetNpc.getKevlar().getProtection()/HEAL_ARMOR_MAX);
            }else{
                this.ArmorEnemy.setProgress(0);
            }
            game.getPlayer().verifHurt();
            if(targetNpc.getLife()<=0){
                try {
                    //le changeScene permet de revenir sur la scene d'exploration
                    control.changeScene("SceneMove",ControlPlayer.currentPlace);                    
                } 
                catch (IOException ex) {
                        Logger.getLogger(ControlPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            descBoxController.setDesc(game.attackEntity(targetNpc));
            descBoxController.setTitle("Alert");  
            if(game.getPlayer().getLife()<=0){
                ControlPlayer.control.exit();                
            }
        }
    }  
    
    public Pane getPaneControlPlaye(){
        return this.paneControlPlayer;
    } 
    
    public void loadItemListView()
    {
        inventoryController.loadInventory();
    }
    
    public void eraseContainerView()
    {
        toolbar.getChildren().remove(2);
        isSet = false;
    }
    public void refreshMapController(){
        mapController.refreshAllMap(ControlPlayer.currentPlace);
    }
    
    public void printDescBox() throws IOException{       
        FXMLLoader descBox = new FXMLLoader(Controller.class.getResource("/LostSquad/View/descBox.fxml"));
        toolbar.getChildren().set(2,descBox.load());
        descBoxController=descBox.getController();
        descBoxController.getDescBox().prefWidthProperty().bind(this.scene.prefWidthProperty().subtract(this.statView.prefWidthProperty().add(this.paneControlPlayer.prefWidthProperty())));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;


import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.FireArmWithMod;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Grip;
import LostSquad.Model.Container.Bauble;
import LostSquad.Model.Container.Key;
import LostSquad.Model.Container.Cross;
import LostSquad.Model.Container.Ammo;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Barrel;
import LostSquad.Model.Container.Heal;
import LostSquad.Model.Container.Sight;
import LostSquad.Model.Game.Game;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Inventory list controller class
 *
 * @author Daniel Rodríguez Hernando
 */
public class InventoryListViewController {

    private final List<Pane> tabDoorLock = new ArrayList<>();
    
    ObservableList<CustomElement> data = FXCollections.observableArrayList();
    public ObservableList<CustomElement> getData()
    {
        return data;
    }
    
    @FXML
    private VBox vbox_inventory;
    
    public VBox getVBoxInventory()
    {
        return this.vbox_inventory;
    }
    
    @FXML
    private ImageView destination_health;
    @FXML
    private ImageView destination_shield;
    @FXML
    private ImageView destination_center;
    @FXML
    private ImageView destination_top;
    @FXML
    private ImageView destination_left;
    @FXML
    private ImageView destination_right;
    @FXML
    private ImageView destination_bottomL;
    @FXML
    private ImageView destination_bottomR;
    @FXML
    private ListView<CustomElement> itemList; 
    @FXML
    private ProgressBar progressBarInventory;
    
    private static DoubleProperty sizeProperty;
    

    public ListView<CustomElement> getItemList()
    {
        return itemList;
    }
    
    private static Image[] images;
    
    private static Game game;
    
    private static Container inventory;
    public Container getInventory()
    {
        return InventoryListViewController.inventory;
    }
    private static Item equipped_item;
    
    private ContainerListViewController containerController;
    
    //Fonction pour recuperer le controleur avec lequel on veut interagir
    public void setContainer(ContainerListViewController containerController)
    {
        this.containerController = containerController;
    }
    
    //Fonction pour initialiser le controlleur
    public void setGame(Game game)
    {
        InventoryListViewController.game = game;
        InventoryListViewController.inventory = InventoryListViewController.game.getPlayer().getContainer();
        InventoryListViewController.sizeProperty = new SimpleDoubleProperty(InventoryListViewController.inventory.getContent().size());
        images = AuxFunctions.loadImages();
        loadInventory(); 
    }
    
    //Fonction pour rafraichir l'inventaire joueur
    public void loadInventory()
    {
        refreshEquippedItem();
        initListView();
        destination_health.setImage(images[3]);
        this.progressBarInventory.progressProperty().bind(InventoryListViewController.sizeProperty.divide(InventoryListViewController.inventory.getCapacity()));
    }
    
    //Fonction pour initialiser la ListView des items de l'inventaire du joueur
    public void initListView()
    { 
        data.clear();
      
        for(int i =0; i<InventoryListViewController.inventory.getContent().size(); i++)
        {
            Item item = InventoryListViewController.inventory.getContent().get(i);
            data = addToData(item);
        }
        
        itemList.setCellFactory((ListView<CustomElement> itemList1) -> new CustomListCell());
        
        itemList.setItems(data);  
        
        itemList.setCursor(Cursor.OPEN_HAND);
    }
    
    //Fonction pour actualiser l'arme equipé du joueur lors d'un changement
    public void refreshEquippedItem()
    {
        if(equipped_item instanceof FireArmWithMod)
        {
            Image weapon = new Image("/LostSquad/View/AllView/img/bigFireArm.png", 400, 400, true, true);
            destination_center.setImage(weapon);
            destination_center.setLayoutX(0);
            destination_center.setLayoutY(43);
            destination_top.setLayoutX(93);
            destination_top.setLayoutY(31);
            destination_left.setLayoutX(14);
            destination_left.setLayoutY(59);
            destination_right.setLayoutX(205);
            destination_right.setLayoutY(64);
            destination_bottomL.setLayoutX(103);
            destination_bottomL.setLayoutY(107);
            destination_bottomR.setLayoutX(164);
            destination_bottomR.setLayoutY(97);
            showImagesMods();
        }
        else if(equipped_item instanceof FireArm)
        {
            Image weapon = new Image("/LostSquad/View/AllView/img/smallFireArm.png", 400, 400, true, true);
            destination_center.setImage(weapon);
            destination_center.setLayoutX(50);
            destination_center.setLayoutY(35);
            hideImagesMods();
        }
        else if(equipped_item instanceof Weapon && !(equipped_item instanceof FireArm))
        {
            Image weapon = new Image("/LostSquad/View/AllView/img/knife.png", 400, 400, true, true);
            destination_center.setImage(weapon);
            destination_center.setLayoutX(0);
            destination_center.setLayoutY(35);
            hideImagesMods();
        }
        else
        {
            hideImagesMods();
        }
    }
    
    //Fonction pour ajouter des items dans la ListView en fonction de son type
    public ObservableList<CustomElement> addToData(Item item)
    {
        if(item instanceof Ammo) //Munition
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[12]));
        }
        else if(item instanceof Barrel) //Mods - Barrel
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[9]));
        }
        else if(item instanceof Bauble) //Random
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[0]));
        }
        else if(item instanceof Cross) //Mods - Cross
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[10]));
        }
        else if(item instanceof Grip) //Mods - Grip
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[11]));
        }
        else if(item instanceof Heal) //Bandage
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[5]));
        }
        else if(item instanceof Kevlar) //Armure
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[15]));
        }
        else if(item instanceof Key) //Clé
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[16]));
        }
        else if(item instanceof Sight) //Mods - Sight
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[8]));
        }
        else if(item instanceof Weapon && !(item instanceof FireArm || item instanceof FireArmWithMod)) //Arme corps à corps
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[14]));
        }
        else if(item instanceof FireArm && !(item instanceof FireArmWithMod)) //Arme sans mods
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[6]));
        }
        else //Arme avec mods
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), images[7]));
        }
        return data;
    }
    
    //Fonction pour afficher les images des modificateurs d'arme s'ils sont equipés dans l'arme prncipale du joueur
    public void showImagesMods()
    {
        FireArmWithMod equipped_fireArm = (FireArmWithMod)equipped_item;
     
        //Sight
        destination_top.setVisible(true);
        destination_top.setDisable(false);
        if(equipped_fireArm.getSight() != null)
        {
            destination_top.setImage(images[2]);
        }
        else
        {
            destination_top.setImage(images[1]);
        }
        
        //Barrel
        destination_left.setVisible(true);
        destination_left.setDisable(false);
        if(equipped_fireArm.getBarrel() != null)
        {
            destination_left.setImage(images[2]);
        }
        else
        {
            destination_left.setImage(images[1]);
        }
        
        //Cross
        destination_right.setVisible(true);
        destination_right.setDisable(false);
        if(equipped_fireArm.getCross() != null)
        {
            destination_right.setImage(images[2]);
        }
        else
        {
            destination_right.setImage(images[1]);
        }
        
        //Ammo
        destination_bottomL.setVisible(true);
        destination_bottomL.setDisable(false);
        
        //Grip
        destination_bottomR.setVisible(true);
        destination_bottomR.setDisable(false);
        if(equipped_fireArm.getGrip() != null)
        {
            destination_bottomR.setImage(images[2]);
        } 
        else
        {
            destination_bottomR.setImage(images[1]);
        }
    }
    
    //Fonction pour cacher les images des modificateurs d'arme si l'arme equipée est une arme sans mods
    public void hideImagesMods()
    {
        destination_top.setVisible(false);
        destination_top.setDisable(true);
        destination_left.setVisible(false);
        destination_left.setDisable(true);
        destination_right.setVisible(false);
        destination_right.setDisable(true);
        destination_bottomL.setVisible(false);
        destination_bottomL.setDisable(true);
        destination_bottomR.setVisible(false);
        destination_bottomR.setDisable(true);
    }

    //Fonction pour detecter le glissement d'un objet
    @FXML
    private void handleDragDetection(MouseEvent event) {
        if(!(inventory.isEmpty()))
        {
            int id = itemList.getSelectionModel().getSelectedItem().getItemId();
            Item item = InventoryListViewController.inventory.getItem(id);

            Dragboard db = itemList.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(String.valueOf(id));
            cb.putImage(itemList.getSelectionModel().getSelectedItem().getItemImage());
            db.setContent(cb);

            DropShadow ds = new DropShadow( 40, Color.AQUA );

            //On traite chaque objet independement
            if(item instanceof Ammo) 
            {
                if(InventoryListViewController.equipped_item instanceof FireArm)
                {
                    if(InventoryListViewController.equipped_item instanceof FireArmWithMod) //Si c'est une arme avec mods on doit remarquer la partie bas-droite de l'arme equipée
                    {
                        destination_bottomL.setEffect(ds);
                        if(containerController != null)
                        {
                             containerController.getItemList().setEffect(ds); 
                        }
                    }
                    else //Sinon on remarque tout l'arme equipée
                    {
                        destination_center.setEffect(ds);
                        if(containerController != null)
                        {
                             containerController.getItemList().setEffect(ds); 
                        }
                    }
                }
            }
            else if(item instanceof Bauble) 
            {
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Barrel && InventoryListViewController.equipped_item instanceof FireArmWithMod)
            {
                destination_left.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Cross && InventoryListViewController.equipped_item instanceof FireArmWithMod)
            {
                destination_right.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Grip && InventoryListViewController.equipped_item instanceof FireArmWithMod) 
            {
                destination_bottomR.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Sight && InventoryListViewController.equipped_item instanceof FireArmWithMod) 
            {
                destination_top.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Heal) 
            {
                destination_health.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Kevlar) 
            {
                destination_shield.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Key) 
            {
                for(int i = 0; i < tabDoorLock.size(); i++)
                {
                    tabDoorLock.get(i).setEffect(ds);
                    //Fonction pour detecter le glissement de la clé sur la porte
                    tabDoorLock.get(i).setOnDragOver((DragEvent eventUnlock) -> {
                        if (eventUnlock.getDragboard().hasImage() && eventUnlock.getDragboard().hasString()) {
                            eventUnlock.acceptTransferModes(TransferMode.ANY); 
                        }
                        eventUnlock.consume();
                    });
                    //Fonction pour ouvrir une porte si on dépose en elle meme la bonne clé
                    tabDoorLock.get(i).setOnDragDropped((DragEvent eventUnlock) -> {
                        int id_key = Integer.parseInt((eventUnlock.getDragboard().getString()));
                        String id_door = ((ImageView)((Pane)eventUnlock.getGestureTarget()).getChildren().get(0)).getId();
                        InventoryListViewController.game.useMain(id_key, id_door);
                        eventUnlock.consume();
                    });
                }
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
            else if(item instanceof Weapon) 
            {
                destination_center.setEffect(ds);
                if(containerController != null)
                {
                     containerController.getItemList().setEffect(ds); 
                }
            }
        }
        event.consume();
    }

    //Fonction pour revenir à l'état initial de l'interface une fois qu'on a arreté de glisser l'objet 
    @FXML
    private void handleDragDone(DragEvent event) {
        for(int i = 0; i < tabDoorLock.size(); i++)
        {
            tabDoorLock.get(i).setEffect(null);
        }
        if(equipped_item instanceof FireArmWithMod)
        {
            destination_top.setEffect(null);
            destination_left.setEffect(null);
            destination_right.setEffect(null);
            destination_bottomL.setEffect(null);
            destination_bottomR.setEffect(null);
        }
        destination_center.setEffect(null);
        destination_health.setEffect(null);
        destination_shield.setEffect(null);
        if(containerController != null)
        {
             containerController.getItemList().setEffect(null); 
        }
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé à l'arme que le joueur veut equiper
    @FXML
    private void handleImageDragOverCenter(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Weapon)
            {
                if(equipped_item != item)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }
            else if(item instanceof Ammo)
            {
                if(equipped_item != null && equipped_item instanceof FireArm)
                {
                    if(!(equipped_item instanceof FireArmWithMod))
                    {
                        
                        event.acceptTransferModes(TransferMode.ANY);
                    }
                }
            }  
        }
    }

    //Fonction pour deposer un objet dans la partie reservé à l'arme que le joueur veut equiper
    @FXML
    private void handleImageDropCenter(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(item instanceof Weapon)
        {
            if(InventoryListViewController.equipped_item != null)
            {
                data = addToData(equipped_item);
            }
        
            InventoryListViewController.game.useMain(id, -1);
            equipped_item = item;
            data.remove(itemList.getSelectionModel().getSelectedIndex());

            refreshEquippedItem();
        }
        else if(item instanceof Ammo)
        {
            FireArm equipped_fireArm = (FireArm)equipped_item;
            
            if(equipped_fireArm.getAmmo().getName().equals(item.getName()))
            {
                if(!(equipped_fireArm.isOutofAmmo()))
                {
                    data = addToData(equipped_fireArm.getAmmo());
                }
                data.remove(itemList.getSelectionModel().getSelectedIndex());
                InventoryListViewController.game.useMain(id, equipped_item.getId()); 
            }
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }
    
    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé au viseur de l'arme
    @FXML
    private void handleImageDragOverTop(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Sight)
            {
                if(equipped_item != null && equipped_item instanceof FireArmWithMod)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé au viseur de l'arme
    @FXML
    private void handleImageDropTop(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(equipped_item instanceof FireArmWithMod)
        {
            FireArmWithMod equipped_fireArm = (FireArmWithMod)equipped_item;
            if(equipped_fireArm.getSight() != null)
            {
                data = addToData(equipped_fireArm.getSight());
            }
            else
            {
                destination_top.setImage(images[2]);
            }
            InventoryListViewController.game.useMain(id, equipped_item.getId());
            data.remove(itemList.getSelectionModel().getSelectedIndex());
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé au canon de l'arme
    @FXML
    private void handleImageDragOverLeft(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Barrel)
            {
                if(equipped_item != null && equipped_item instanceof FireArmWithMod)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé au canon de l'arme
    @FXML
    private void handleImageDropLeft(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(equipped_item instanceof FireArmWithMod)
        {
            FireArmWithMod equipped_fireArm = (FireArmWithMod)equipped_item;
            if(equipped_fireArm.getBarrel() != null)
            {
                data = addToData(equipped_fireArm.getBarrel());
            }
            else
            {
                destination_left.setImage(images[2]);
            }
            InventoryListViewController.game.useMain(id, equipped_item.getId());
            //equipped_item = InventoryListViewController.game.getPlayer().getItem(id);
            data.remove(itemList.getSelectionModel().getSelectedIndex());
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé à la crosse de l'arme
    @FXML
    private void handleImageDragOverRight(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Cross)
            {
                if(equipped_item != null && equipped_item instanceof FireArmWithMod)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé à la crosse de l'arme
    @FXML
    private void handleImageDropRight(DragEvent event) { 
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(equipped_item instanceof FireArmWithMod)
        {
            FireArmWithMod equipped_fireArm = (FireArmWithMod)equipped_item;
            if(equipped_fireArm.getCross() != null)
            {
                data = addToData(equipped_fireArm.getCross());
            }
            else
            {
                destination_right.setImage(images[2]);
            }
            InventoryListViewController.game.useMain(id, equipped_item.getId());
            data.remove(itemList.getSelectionModel().getSelectedIndex());
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }
    
    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé au chargeur de l'arme
    @FXML
    private void handleImageDragOverBottomL(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Ammo)
            {
                if(equipped_item != null && equipped_item instanceof FireArmWithMod)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé au chargeur de l'arme
    @FXML
    private void handleImageDropBottomL(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(equipped_item instanceof FireArmWithMod)
        {        
            FireArm equipped_fireArm = (FireArm)equipped_item;
            
            if(equipped_fireArm.getAmmo().getName().equals(item.getName()))
            {
                if(!(equipped_fireArm.isOutofAmmo()))
                {
                    data = addToData(equipped_fireArm.getAmmo());
                }
                data.remove(itemList.getSelectionModel().getSelectedIndex());
                InventoryListViewController.game.useMain(id, equipped_item.getId()); 
            }  
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé à la poignée de l'arme
    @FXML
    private void handleImageDragOverBottomR(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Grip)
            {
                if(equipped_item != null && equipped_item instanceof FireArmWithMod)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé à la poignée de l'arme
    @FXML
    private void handleImageDropBottomR(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = InventoryListViewController.inventory.getItem(id);
        if(equipped_item instanceof FireArmWithMod)
        {
            FireArmWithMod equipped_fireArm = (FireArmWithMod)equipped_item;
            if(equipped_fireArm.getGrip() != null)
            {
                data = addToData(equipped_fireArm.getGrip());
            }
            else
            {
                destination_bottomR.setImage(images[2]);
            }
            InventoryListViewController.game.useMain(id, equipped_item.getId());
            data.remove(itemList.getSelectionModel().getSelectedIndex());
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé à l'augmentation de la vie du joueur
    @FXML
    private void handleImageDragOverHealth(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Heal)
            {
                event.acceptTransferModes(TransferMode.ANY);
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé à l'augmentation de la vie du joueur
    @FXML
    private void handleImageDropHealth(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
       
        InventoryListViewController.game.useMain(id, -1);
        data.remove(itemList.getSelectionModel().getSelectedIndex());
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé à l'augmentation du bouclier du joueur
    @FXML
    private void handleImageDragOverShield(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString())
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = InventoryListViewController.inventory.getItem(id);
            if(item instanceof Kevlar)
            {
                event.acceptTransferModes(TransferMode.ANY);
            }   
        }    
    }

    //Fonction pour deposer un objet dans la partie reservé à l'augmentation du bouclier du joueur
    @FXML
    private void handleImageDropShield(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
       
        if(InventoryListViewController.game.getPlayer().getKevlar() != null)
        {
            data = addToData(InventoryListViewController.game.getPlayer().getKevlar());
        }
        InventoryListViewController.game.useMain(id, -1);
        data.remove(itemList.getSelectionModel().getSelectedIndex());
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

    //Fonction pour detecter si on peut deposer un objet ou pas dans la partie reservé au conteneur avec lequel le joueur veut intéragir
    @FXML
    private void handleImageDragOverList(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString() && containerController != null)
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = containerController.getContainer().getItem(id);
            if(InventoryListViewController.inventory.getContent().size() < InventoryListViewController.inventory.getCapacity())
            {
                if(item != null)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet dans la partie reservé au conteneur avec lequel le joueur veut intéragir
    @FXML
    private void handleImageDropOnList(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = containerController.getContainer().getItem(id);
        if(!(containerController.hasOwner())) 
        {
            containerController.getContainer().removeItem(item);
            containerController.getData().remove(containerController.getItemList().getSelectionModel().getSelectedIndex());
            inventory.addItem(item);
            data = addToData(item);
        }
        else //Si le conteneur appartient à un marchent, on achete l'object que le joueur à recupéré de l'inventaire du marchand
        {
            if(InventoryListViewController.game.buyMain(containerController.getTrader(), id))
            {
                containerController.getData().remove(containerController.getItemList().getSelectionModel().getSelectedIndex());
                data = addToData(item);
            }
        }
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }

     //Élément personalisé de la ListeView d'items
    private static class CustomElement {
        private final int item_id;
        private final String item_name;
        private final String item_description;
        private final Image item_image; 
        
        public CustomElement(int id, String name, String description, Image image) {
            super();
            this.item_id = id;
            this.item_name = name;
            this.item_description = description;
            this.item_image = image;
        }
        
        public int getItemId(){
            return this.item_id;
        }
        
        public String getItemName() {
            return this.item_name;
        }
        
        public String getItemDescription() {
            return this.item_description;
        }
        
        public Image getItemImage(){
            return this.item_image;
        }
    }
    
    //Cellule personalisé de la ListeView d'items
    private class CustomListCell extends ListCell<CustomElement> {
        private final HBox content;
        private final Text id;
        private final Text name;
        private final Text description;
        private final ImageView image;

        public CustomListCell() {
            super();
            id = new Text();
            name = new Text();
            description = new Text();
            image = new ImageView();
            VBox vBox = new VBox(name, description);
            content = new HBox(image, vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(CustomElement item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                id.setText(String.valueOf(item.getItemId()));
                name.setText(item.getItemName());
                description.setText(item.getItemDescription()); //String.valueOf(item.getItemId())
                image.setImage(item.getItemImage());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }
    
    //Fonction pour ajouter une porte dans la liste de portes
    public void addDoorLock(Pane pane){
        tabDoorLock.add(pane);
    }
    
    //Fonction pour effacer la liste de portes
    public void clearDoorLockList(){
        tabDoorLock.removeAll(tabDoorLock);
    }
    
    //Fonction pour vendre un object au marchand voulu
    public boolean sellItem(int item, String trader)
    {
        return InventoryListViewController.game.sellMain(trader, item);
    }
    
    public void setSizeProperty(){
        InventoryListViewController.sizeProperty.set(InventoryListViewController.inventory.getContent().size());
    }
}

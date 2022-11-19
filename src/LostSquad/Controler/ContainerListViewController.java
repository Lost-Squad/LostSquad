/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.FireArmWithMod;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Grip;
import LostSquad.Model.Container.Bauble;
import LostSquad.Model.Container.Key;
import LostSquad.Model.Container.Cross;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Ammo;
import LostSquad.Model.Container.Barrel;
import LostSquad.Model.Container.Heal;
import LostSquad.Model.Container.Sight;
import LostSquad.Model.Container.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Container list controller class
 *
 * @author Daniel Rodríguez Hernando
 */
public class ContainerListViewController {
    ObservableList<CustomElement> data = FXCollections.observableArrayList();
    public ObservableList<CustomElement> getData()
    {
        return data;
    }
    
    @FXML
    private VBox vbox_container;
    
    public VBox getVBoxContainer()
    {
        return this.vbox_container;
    }
    
    @FXML
    private Label name;
    @FXML
    private ListView<CustomElement> itemList;
    public ListView<CustomElement> getItemList()
    {
        return itemList;
    }
    
    private Image[] images;
    
    private Container container;
    public Container getContainer()
    {
        return this.container;
    }
    
    private String trader = null;
    public String getTrader()
    {
        return this.trader;
    }
    public boolean hasOwner()
    {
        return trader != null;
    }
    
    private InventoryListViewController inventoryController;
    
    //Fonction pour initialiser le controleur d'un conteneur qui n'appartient pas à un trader
    public void setContainer(Container container, InventoryListViewController inventoryController)
    {
        name.setText(container.getName());
        this.container = container;
        images = AuxFunctions.loadImages();
        initListView();
        this.inventoryController = inventoryController;
    }
    
    //Fonction pour initialiser le controleur d'un conteneur qui n'appartient pas à un trader
    public void setContainer(Container container, InventoryListViewController inventoryController, String trader)
    {
        name.setText(container.getName());
        this.container = container;
        images = AuxFunctions.loadImages();
        initListView();
        this.inventoryController = inventoryController;
        this.trader = trader;   
    }
    
    //Fonction pour initialiser la ListView des items du conteneur
    public void initListView()
    { 
        data.clear();
      
        for(int i =0; i<container.getContent().size(); i++) //On initialise les donnes qui seront dans la ListView
        {
            Item item = container.getContent().get(i);
            data = addToData(item);
        }
        
        //Fonction pour initialiser les cellules personalisées de notre ListView
        itemList.setCellFactory((ListView<CustomElement> itemList1) -> new CustomListCell());
        
        itemList.setItems(data);  
        
        itemList.setCursor(Cursor.OPEN_HAND);
    }
    
    //Fonction pour reinitialiser 
    public void resetContainerView()
    {
        vbox_container.setVisible(false);
        vbox_container.setDisable(true);
        name.setText("");
        itemList.setVisible(false);
        itemList.setDisable(true);
    }
    
    //Fonction pour ajouter des items dans la ListView en fonction de son type
    public ObservableList<CustomElement> addToData(Item item)
    {
        if(item instanceof Ammo) //Munition
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[12]));
        }
        else if(item instanceof Barrel) //Mods - Barrel
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[9]));
        }
        else if(item instanceof Bauble) //Random
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[0]));
        }
        else if(item instanceof Cross) //Mods - Cross
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[10]));
        }
        else if(item instanceof Grip) //Mods - Grip
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[11]));
        }
        else if(item instanceof Heal) //Bandage
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[5]));
        }
        else if(item instanceof Kevlar) //Armure
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[15]));
        }
        else if(item instanceof Key) //Clé
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[16]));
        }
        else if(item instanceof Sight) //Mods - Sight
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[8]));
        }
        else if(item instanceof Weapon && !(item instanceof FireArm || item instanceof FireArmWithMod)) //Arme corps à corps
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[14]));
        }
        else if(item instanceof FireArm && !(item instanceof FireArmWithMod)) //Arme sans mods
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[6]));
        }
        else //Arme avec mods
        {
            data.add(new CustomElement(item.getId(), item.getName(), item.getDescription(), item.getPrice(), images[7]));
        }
        return data;
    }

    //Fonction pour detecter le glissement d'un objet
    @FXML
    private void handleDragDetection(MouseEvent event) {
        if(!(container.isEmpty()))
        {
            int id = itemList.getSelectionModel().getSelectedItem().getItemId();
            Item item = container.getItem(id);

            Dragboard db = itemList.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(String.valueOf(id));
            cb.putImage(itemList.getSelectionModel().getSelectedItem().getItemImage());
            db.setContent(cb);

            DropShadow ds = new DropShadow( 40, Color.AQUA );
            inventoryController.getItemList().setEffect(ds); 
        }
        event.consume();
    }

    //Fonction pour revenir à l'état initial de l'interface une fois qu'on a arreté de glisser l'objet
    @FXML
    private void handleDragDone(DragEvent event) {
        inventoryController.getItemList().setEffect(null);
    }
    
    //Fonction pour detecter si on peut deposer un objet ou pas dans le conteneur
    @FXML
    private void handleImageDragOverList(DragEvent event) {
        if(event.getDragboard().hasImage() && event.getDragboard().hasString() && inventoryController != null)
        {
            int id = Integer.parseInt(event.getDragboard().getString());
            Item item = inventoryController.getInventory().getItem(id);
            if(container.getContent().size() < container.getCapacity())
            {
                if(item != null)
                {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }   
        }
    }

    //Fonction pour deposer un objet venant de l'inventaire du joueur dans le conteneur
    @FXML
    private void handleImageDropOnList(DragEvent event) {
        int id = Integer.parseInt(event.getDragboard().getString());
        Item item = inventoryController.getInventory().getItem(id);
        if(trader == null) 
        {
            inventoryController.getInventory().removeItem(item);
            inventoryController.getData().remove(inventoryController.getItemList().getSelectionModel().getSelectedIndex());
            container.addItem(item);
            data = addToData(item);
        }
        else //Si le joueur veut deposer un objet dans l'inventaire du marchant, on vend l'objet venant du joueur
        {
            if(inventoryController.sellItem(id, trader))
            {
                inventoryController.getData().remove(inventoryController.getItemList().getSelectionModel().getSelectedIndex());
                data = addToData(item);
            }
        }
        itemList.setEffect(null);
        this.inventoryController.setSizeProperty();
    }
    
    //Élément personalisé de la ListeView d'items
    private static class CustomElement {
        private final int item_id;
        private final String item_name;
        private final String item_description;
        private final int item_price;
        private final Image item_image; 
        
        public CustomElement(int id, String name, String description, int price, Image image) {
            super();
            this.item_id = id;
            this.item_name = name;
            this.item_description = description;
            this.item_price = price;
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
        
        public int getItemPrice()
        {
            return this.item_price;
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
        private final ImageView price;

        public CustomListCell() {
            super();
            id = new Text();
            name = new Text();
            description = new Text();
            price = new ImageView();
            image = new ImageView();
            HBox hbox = new HBox(price, description);
            VBox vBox = new VBox(name, hbox);
            content = new HBox(image, vBox);
            content.setSpacing(10);
        }

        @Override
        protected void updateItem(CustomElement item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                id.setText(String.valueOf(item.getItemId()));
                name.setText(item.getItemName());
                if(hasOwner()) //Si le conteneur appartient à un marchand
                {
                    description.setText(String.valueOf(item.getItemPrice())); 
                    Image price_image = new Image("/LostSquad/View/AllView/img/goldCoin5.png", 15, 15, true, true);
                    price.setImage(price_image);
                    price.setVisible(true);
                }
                else
                {
                    description.setText(item.getItemDescription()); 
                    price.setVisible(false);
                }
                image.setImage(item.getItemImage());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
        
        
    }
}

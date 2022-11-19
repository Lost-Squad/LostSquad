/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;

import LostSquad.Model.Game.Game;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MapViewController {
    private static Game game;
    static HashMap<String,int[]> cursorPos;
    static HashMap<String,RoomMap> roomsMap;
    static MapViewController instance;
    static int mapSet = 0;
    
    public MapViewController(){
        if(instance == null){
            instance = this;
        }
    }
    
    @FXML
    private Label zoneName;
    @FXML
    private Pane mapPane;
    @FXML
    private Pane mapDrawPane;
    @FXML
    private ImageView playerPos;
    
    public void loadGame(Game game)
    {
        System.out.println("Load game");
        MapViewController.game = game;
        if(cursorPos == null){
            cursorPos = new HashMap<>();
            cursorPos.put("Start", new int[]{53,78,180} );
            cursorPos.put("Start_Metro1_back", new int[]{53,78,0} );
            cursorPos.put("Metro1_Start_forward", new int[]{53,62,180} );
            cursorPos.put("Metro1_Metro2_back", new int[]{53,68,0} );
            cursorPos.put("Metro2_Metro1_forward", new int[]{53,46,-90} );
            cursorPos.put("stairRoom_Metro2_door", new int[]{84,46,-90} );
            cursorPos.put("stairRoom_stairRoom2_back", new int[]{84,52,180} );
            cursorPos.put("StairRoom_outside_back", new int[]{84,52,180} );
            cursorPos.put("stairRoom2_stairRoom_right", new int[]{84,66,0} );
            cursorPos.put("outside_stairRoom_stairs", new int[]{114,46,-90} );
            cursorPos.put("outside_stairRoom_stairs", new int[]{114,46,-90} );
            cursorPos.put("outside_house_out", new int[]{114,46,90} );
            cursorPos.put("house_outside_house", new int[]{64,34,-90} );
            cursorPos.put("house_house3_home", new int[]{64,38,180} );
            cursorPos.put("house3_house_kitchen", new int[]{63,49,0} );
            cursorPos.put("house3_débarras_back", new int[]{63,60,-180} );
            cursorPos.put("house3_house4_kitchen", new int[]{85,55,90} );
            cursorPos.put("débarras_house3_storage", new int[]{63,72,0} );
            cursorPos.put("house4_house3_heart", new int[]{114,46,-90} );
            cursorPos.put("house4_stairhouse_down", new int[]{114,46,180} );
            cursorPos.put("house4_house2_ark", new int[]{114,46,0} );
            cursorPos.put("house2_house4_ark", new int[]{114,26,180} );
            cursorPos.put("house2_storage_back", new int[]{114,26,90} );
            cursorPos.put("storage_house2_storage", new int[]{114,46,-90} );
            cursorPos.put("stairhouse_house4_stair", new int[]{114,46,-90} );
            cursorPos.put("stairhouse_firstfloor_stairs", new int[]{114,46,-90} );
            cursorPos.put("stairRoom_outside_back", new int[]{84,52,90} ); 
            cursorPos.put("Metro2_stairRoom_back", new int[]{53,46,0} );  
        }
        
        if(roomsMap==null){
            roomsMap = new HashMap<>();
            setMapInfo();
        }
        Image playerImg = new Image("/LostSquad/View/AllView/img/PlayerOnMap.png", 10, 10, true, true);
        playerPos.setImage(playerImg);
        refreshMap(game.getCurrentPlace().getName());
    }
    
    public void setMapInfo(){
        roomsMap.put("Start", new RoomMap(17,18,51,78));
        roomsMap.put("Metro1", new RoomMap(17,18,51,60));
        roomsMap.put("Metro2", new RoomMap(30,20,51,40));
        roomsMap.put("stairRoom", new RoomMap(33,23,81,40));
        roomsMap.put("stairRoom2", new RoomMap(33,23,81,63));
        roomsMap.put("outside", new RoomMap(75,61,114,8));
        roomsMap.put("débarras", new RoomMap(44,23,52,68,1));
        roomsMap.put("house3", new RoomMap(37,23,59,46,1));
        roomsMap.put("house", new RoomMap(30,23,59,24,1));
        roomsMap.put("house4", new RoomMap(55,35,93,34,1));
        roomsMap.put("house2", new RoomMap(55,29,93,7,1));
        roomsMap.put("stairhouse", new RoomMap(37,23,147,7,1));
        roomsMap.put("storage", new RoomMap(44,29,127,68,1));
    }
    
    public void refreshZoneName(){
        zoneName.setText(game.getCurrentPlace().getName());
    }
    
    public void refreshPlayerPos(String viewName){
        playerPos.setLayoutX((cursorPos.get(viewName))[0]);
        playerPos.setLayoutY((cursorPos.get(viewName))[1]);
        playerPos.setRotate((cursorPos.get(viewName))[2]);
    }
    
    public void discover(String roomName){
        if(roomName.equals("house")){
            mapSet = 1;
        }
        if(roomName.equals("outside")){
            mapSet = 0;
        }
        RoomMap room = roomsMap.get(roomName);
        if(room != null){
            System.out.println("This room exist");
            room.isDiscovered = true;
        }
        drawMap();
    }
    
    public void drawMap(){
        clearMap();
        roomsMap.values().stream().filter((room) -> (room.isDiscovered && mapSet == room.mapSet)).forEachOrdered((room) -> {
            mapDrawPane.getChildren().add(room);
        });
        mapDrawPane.getChildren().add(playerPos);
    }
    
    public void clearMap(){
        mapDrawPane.getChildren().clear(); 
    }
    
    public void refreshMap(String viewName){
        refreshZoneName();
        refreshPlayerPos(viewName);
        discover(viewName.split("_")[0]);
    }
    
    public void refreshAllMap(String viewName){
        Image playerImg = new Image("/LostSquad/View/AllView/img/PlayerOnMap.png", 10, 10, true, true);
        playerPos.setImage(playerImg);
        refreshMap(viewName);
    }
    
    public class RoomMap extends Rectangle{
        public boolean isDiscovered = false;
        public int mapSet;
        
        public RoomMap(int x, int y, int lx, int ly){
            this(x,y,lx,ly,0);
        }
        public RoomMap(int x, int y, int lx, int ly, int mapSet){
            super(x,y);
            this.setLayoutX(lx);
            this.setLayoutY(ly);
            this.setFill(Color.rgb(20, 19, 19));
            this.setStroke(Color.YELLOW);
            this.mapSet=mapSet;
        }
    }
    
}

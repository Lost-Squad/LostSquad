package LostSquad.Model.Room;

import LostSquad.Model.Interfaces.Descriptible;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Characters.Entity;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Place implements Serializable, Descriptible{
    // ******************************** Place **********************************//
    // *                              Attribute                                *//
    // *************************************************************************//
    // * Static : 
    // *  => PATH
    // *    The directory where all stored Place are saved.
    // * Local :
    // *  => exit 
    // *    a list of door leading to other places
    // *  => chests 
    // *    a list of Container with every container in the room
    // *  => npc
    // *    the list of every Character in the room.
    // *  => name
    // *    The name of the room. Or his short desc.
    // *  => desc
    // *    Detailed descritption of the room
    // *************************************************************************//
    // *                             Constructor                               *//
    // *************************************************************************//
    // *  => Place()
    // *   build a default room.
    // *  => Place(String name, String desc)
    // *   build a Place with a name and a desc, all other attribute are
    // *   initialized empty list.
    // *  => Place(String path)
    // *   build a Place using a file precedently created with Place.saveToFile
    // *************************************************************************//
    // *                                Method                                 *//
    // *************************************************************************//
    // * File Gestion :
    // *  => void SaveToFile()
    // *   Create or replace the file in the PATH with the same name. Then write
    // *   this object into it.
    // *
    // * Role Play Method :
    // *  => void onEnter() & void setEnterText(String text);
    // *   If onEnterText is initialized byt the "set" method, onEnter() print it.
    // *  => void onLook() &  void setOnLook(String text);
    // *   Same
    // *************************************************************************//
        
        private static final String PATH = System.getProperty("user.dir") + "\\src\\LostSquad\\Model\\Room\\savedRoom\\";
        
	private List<Door> exit;
	private List<Container> chests;
	private List<Entity> npc;
        private String name;
	private String desc;
        
        private String onLookText;
        private String onEnterText;
        
        private void setupPlace(Place p){
            this.chests = new ArrayList<>(p.chests);
            this.desc = p.desc;
            this.exit = new ArrayList<>(p.exit);
            this.name = p.name;
            this.npc = new ArrayList<>(p.npc);
            this.onEnterText = p.onEnterText;
            this.onLookText = p.onLookText;
        }
        
        public Place(){
            this("a Empty room","This place have no desc.");
        }
        public Place(String p_name, String p_desc){
            this.exit = new ArrayList<>();
            this.chests = new ArrayList<>();
            this.npc = new ArrayList<>();
            this.name = p_name;
            this.desc = p_desc;
            this.onEnterText = new String();
            this.onLookText = new String();
        }
        public Place(String p_name){
            try{
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(PATH + p_name)))) {
                    this.setupPlace((Place) ois.readObject());
                }
            }catch (IOException e) {
                System.out.println("Error initializing stream");
            }catch(ClassNotFoundException e){
            System.out.println("ClassNotFound");
            }
        }

        public List<Entity> getListNpc(){
            return this.npc;
        }
        
        @Override
        public void onLook(){
            if(this.onLookText != null){
                System.out.println(this.onLookText);
            }
        }
        public void setOnLook(String text){
            this.onLookText = text;
        }
        
        public void onEnter(){
                System.out.println(this.onEnterText);
        }
        public void setOnEnter(String text){
            if(this.onEnterText!= null){
               this.onEnterText = text; 
            }
        }
        
        
        public void saveToFile(){
            try{
                File file = new File(PATH + this.name);
                file.delete();
                file.createNewFile();
                try (FileOutputStream f = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(f)) {
                    oos.writeObject(this);
                }
            }catch(FileNotFoundException e){
                System.out.println("Error file not Found");
            }catch (IOException e) {
                System.out.println("Error initializing stream");
            }
        }
        
        public Door findExitByName(String p_name){
            for(Door dor : this.exit){
                if(dor.getName().equals(p_name) ){
                    return dor;
                }
            }
            return null;
        }
        public Container findContainer(String p_name){
            for(Container container : this.chests){
                if(container.getName().equals(p_name) ){
                    return container;
                }
            }
            return null;
        }
        public Entity findCharacterByName(String p_name){
            for(Entity character : this.npc){
                if(character.getName().equals(p_name) ){
                    return character;
                }
            }
            return null;
        }
        
        public void desc(){
            System.out.printf(this.desc + "\n");
        }
        
        @Override
        public String getName(){
            return this.name;
        }
        
        public void addExit(Door door){
            this.exit.add(door);
        }
        public void addChest(Container chest){
            this.chests.add(chest);
        }
        public void addCharacter(Entity chara){
            this.npc.add(chara);
        }
        
        @Override
        public String getDesc(){
            return this.onEnterText;
        }
        
        public String getLookText(){
            return this.onLookText;
        }
}
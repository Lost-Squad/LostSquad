package LostSquad.Model.Game;

import LostSquad.Model.Room.DoorLock;
import LostSquad.Model.Room.Place;
import LostSquad.Model.Room.DoorLockKey;
import LostSquad.Model.Room.Door;
import LostSquad.Model.Interfaces.Descriptible;
import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.FireArmWithMod;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.ContainerLockKey;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Ammo;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Mod;
import LostSquad.Model.Container.Key;
import LostSquad.Model.Container.Heal;
import LostSquad.Model.Characters.Trader;
import LostSquad.Model.Characters.Entity;
import LostSquad.Model.Characters.Enemy;
import LostSquad.Model.Characters.Npc;
import LostSquad.Model.Characters.Player;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	private Place currentPlace;
	private Player player;
        
        
        /**********************************************************/
        /*                      main method                       */
        /**********************************************************/
       public void main(){
            
            //start creat player
            Container c1 = new Container("bag","it is a well maintained military bag",14,new ArrayList<>());          
            this.player = new Player(c1);
            //end creat player

            System.out.println("// Starting game...");
            /*currentPlace.desc();
            boolean continuePlaying = true;
            while(continuePlaying){
                List<Entity> npc= this.currentPlace.getListNpc();
                npc.forEach((character) -> {
                    ((Npc)character).attack(player);
                });
                player.verifHurt();
                if(player.getLife()<=0 || this.currentPlace.getName().equals("end")){
                    continuePlaying=false;
                }else{
                    continuePlaying = mainInputTreatment();
                }
            }
            if(player.getLife()<=0){
                System.out.print("--------------DEAD--------------\n");
            }
            */
        }
        
        public void printHelp(String target){
            if(target.equals("")){
                System.out.printf(""
                    + "*********************************************************\n"
                    + "*                        Help                           *\n"
                    + "*********************************************************\n"
                    + "* Type your command and the target of your command to   *\n"
                    + "* execute it                                            *\n"
                    + "* The list of the command implemented is here :         *\n"
                    + "* >HELP [] or [LOOK] or [LOOT] or [USE]                 *\n"
                    + "* >GO [where]                                           *\n"
                    + "* >LOOK [what]                                          *\n"
                    + "* >BUY [nameTrade] [nameObject]                         *\n"
                    + "* >SELL [nameTrade] [nameObject]                        *\n"
                    + "* >TAKE [nameTarget] [nameObject or ALL]                *\n"
                    + "* >DROP [nameTarget] [nameObject]                       *\n"
                    + "* >USE [nameObject] [nameObject or me]                  *\n"
                    + "* >ATTACK [nameTarget]                                  *\n"
                    + "* >SPEAK  [nameTarget]                                  *\n"
                    + "* >LOOT   [nameTarget]                                  *\n"
                    + "* >EXIT                                                 *\n"
                    + "*********************************************************\n");
            }else{
                target = target.substring(1);
                switch (target){
                    case("LOOK"):
                        System.out.printf(""
                            + "*********************************************************\n"
                            + "*                        Help LOOK                      *\n"
                            + "*                                                       *\n"
                            + "* >LOOK [what] = take a description                     *\n"
                            + "* [what] : myweapon or inventory or mymoney or nameNpc  *\n"
                            + "*          or nameDoor or life or ...                   *\n"
                            + "* using this function does not consume your turn        *\n"
                            + "*********************************************************\n"
                        );
                        break;
                    case("LOOT"):
                        System.out.printf(""
                            + "*********************************************************\n"
                            + "*                        Help LOOT                      *\n"
                            + "*                                                       *\n"
                            + "* >LOOT   [nameTarget] = see inventory npc death        *\n"
                            + "*********************************************************\n"
                        );
                        break;
                    case("USE"):
                        System.out.printf(""
                            + "*********************************************************\n"
                            + "*                        Help USE                       *\n"
                            + "*                                                       *\n"
                            + "* >USE [nameObject] [nameObject or me] = use object     *\n"
                            + "* [nameObject] : name d'un item                         *\n"
                            + "* [nameObject or me] : name Weapon or name Door or Me   *\n"
                            + "* Example : use nameWeapon me = your weapon principale  *\n"
                            + "*                               is now nameWeapon       *\n"
                            + "*********************************************************\n"
                        );
                        break;
                }
            }     
        }
        
        /**********************************************************/
        /*                        Utility                         */
        /**********************************************************/
        
        boolean yesOrNo(String question){
            System.out.println(question);
            Scanner sc = new Scanner(System.in);
            while(true){
                switch(sc.nextLine()){
                case("y"):
                case("yes"):
                case("Yes"):
                case("Y"):
                case("YES"):
                    return true;
                case("n"):
                case("no"):
                case("No"):
                case("N"):
                case("NO"):
                    return false;
                default :
                    System.out.println("Please answer by yes or no.");
                    break;
                }
            }
        }
        
        
        public boolean verrifEnemy(){
            int i=0;
            boolean v=false;
            while(!v && i<this.currentPlace.getListNpc().size()){
                Entity people = this.currentPlace.getListNpc().get(i);
                if(people instanceof Enemy && people.getLife()>0){
                    v=true;
                }
                i++;
            }
            return v;
        }      
        
        public boolean useMain(int obj, int cible){
                Item object = player.getItem(obj);
                Item target = player.getItem(cible);
                if(object==null){     
                }else if (target==null){
                    if(object instanceof Weapon){
                        player.setWeapon((Weapon)object);
                        player.getContainer().removeItem(object);
                        return true;
                    }else if (object instanceof Heal){
                        ((Heal)object).use(player);
                        player.getContainer().removeItem(object);
                        return true;
                    }
                    else if(object instanceof Kevlar){
                        player.setKevlar((Kevlar)object);
                        return true;
                    }
                }else if(object instanceof Ammo && target instanceof FireArm && player.isArmPrincipale((Weapon)target)){
                    return player.reload((Ammo)object);
                }else if (object instanceof Mod && target instanceof FireArmWithMod && player.isArmPrincipale((Weapon)target)){
                    player.setMod(object);
                    return true;
                } 
                return false;
       }
        
       public void useMain(int obj, String cible){
            Item object = player.getItem(obj);
            if (object instanceof Key){
                Door door = this.currentPlace.findExitByName(cible);
                Container container = this.currentPlace.findContainer(cible);
                if(door==null && container==null){
                    System.out.print("the target does not exist\n");
                }else if(door !=null && door instanceof DoorLockKey){
                    System.out.print("you try the key\n");
                    if(object instanceof Key){
                        ((Key) object).use((DoorLockKey)door);
                    }   
                }else if (container!=null && container instanceof ContainerLockKey){
                    System.out.print("you try the key\n");
                    if(object instanceof Key){
                        ((Key) object).use((ContainerLockKey)container);
                    }
                }else if((door !=null && door instanceof DoorLock)|| (container!=null)){
                    System.out.print("it is not locked by a key\n");
                }else {
                    System.out.print("this has never been and will not be locked\n");
                }
            }else if(object instanceof Heal){
                Npc people = (Npc)this.currentPlace.findCharacterByName(cible);
                if(people!=null){
                    ((Heal)object).use(people);
                    player.getContainer().removeItem(object);
                }    
            }      
       }         
        
        
        public void dropMain(String target,int idObject){
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if (targetNpc==null){
                Container targetContain = this.currentPlace.findContainer(target);
                if(targetContain!=null){
                    player.dropItem(idObject, targetContain);
                }else{
                    System.out.print("no match for target\n");
                }
            }else{
                player.dropItem(idObject, targetNpc);
            }
        }    
        
        public void takeMain(String target,int idObject){ 
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if (targetNpc==null){
                Container targetContain = this.currentPlace.findContainer(target);
                if(targetContain!=null){
                    player.take(idObject, targetContain);
                }else{
                    System.out.print("no match for target\n");
                }
            }else{
                player.take(idObject, targetNpc);
            }
        }
        
        public void takeMain(String target){
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if (targetNpc==null){
                Container targetContain = this.currentPlace.findContainer(target);
                if(targetContain!=null){
                    player.takeAll(targetContain);
                }
            }else{
                player.takeAll(targetNpc);
            }
        }
        
                
        public void lootMain(String target){
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if(targetNpc==null){
                System.out.print("this person does not exist\n");  
            }else if(targetNpc.getContainer()==null){
                System.out.print("he has no inventory\n"); 
            }else{
                player.seeLoot(targetNpc);
            }
        }
        
        
        public void SpeakMain(String target){
            if(target.equals("")){
                System.out.print("you should be targeting someone\n");
            }else{
                Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
                if(targetNpc==null){
                  System.out.print("this person does not exist\n");  
                }else if(targetNpc instanceof Trader){
                    player.speak((Trader)targetNpc);
                }else{
                    player.speak(targetNpc);
                }
            }
        }
        
        public boolean buyMain(String target,int idObject){
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if(targetNpc==null){
                System.out.print("this person does not exist\n"); 
                return false;
            }else if(targetNpc instanceof Trader){
                return player.buyItems(idObject, (Trader)targetNpc);
            }else{
                System.out.print("this person is not a merchant\n");
                return false;
            }
        }
        
        public boolean sellMain(String target,int idObject){
            Npc targetNpc = (Npc)this.currentPlace.findCharacterByName(target);
            if(targetNpc==null){
                System.out.print("this person does not exist\n");  
                return false;
            }else if(targetNpc instanceof Trader){
                return player.sellItem(idObject, (Trader)targetNpc);
            }else{
                System.out.print("this person is not a merchant\n");
                return false;
            }
        }
        
        public void attackMain(Npc targetNpc){
            if(targetNpc==null){
                System.out.print("this person does not exist in function attackMain in game");  
            }else{
                player.attack(targetNpc);
            }
        }
        
        public void lookMain(String target){
            Descriptible targetedItem = (Descriptible) this.currentPlace.findExitByName(target);
            if(targetedItem==null){
                targetedItem = (Descriptible) this.currentPlace.findCharacterByName(target);
            }
            if(targetedItem==null){
                targetedItem = (Descriptible) this.currentPlace.findContainer(target);
            }
            if(targetedItem != null){
                System.out.print("\n---START LOOK ---\n");
                targetedItem.onLook();
                System.out.print("---END LOOK ---\n\n");
            }else{
                System.out.print("la cible n'existe pas\n");
            }
        }
        
        public void goMain(Door targetedDoor){
            if(targetedDoor != null){
                goDoor(targetedDoor);
            }else{
                System.out.printf("There is no place name here ! \n");
            }
        }
        
        public void enterPlace(String nPlace){
            this.currentPlace = new Place(nPlace);
            this.currentPlace.onEnter();
        }
        
        public void goDoor(Door targetedDoor){
            if(targetedDoor.onApproching()){
                if(yesOrNo("Do you want to continue ?")){
                    if(targetedDoor.onTravel()){
                        this.enterPlace(targetedDoor.getPlace());
                    }
                }else{
                    System.out.println("You turn back from the door");
                }
            }else{
                if(targetedDoor.onTravel()){
                        this.currentPlace.saveToFile();
                        this.enterPlace(targetedDoor.getPlace());
                }
            }
        }
        
        public Player getPlayer(){
            return this.player;
        }
        
        public Place getCurrentPlace(){
            return this.currentPlace;
        }
        
        public String attackEntity(Npc npc){
            return npc.attack(player);
        }
}
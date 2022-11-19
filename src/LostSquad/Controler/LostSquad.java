/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Controler;
import LostSquad.Model.Room.Place;
import LostSquad.Model.Room.DoorLockKey;
import LostSquad.Model.Room.Door;
import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.FireArmWithMod;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Grip;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Bauble;
import LostSquad.Model.Container.Key;
import LostSquad.Model.Container.Cross;
import LostSquad.Model.Container.ContainerLockKey;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Ammo;
import LostSquad.Model.Container.Barrel;
import LostSquad.Model.Container.Heal;
import LostSquad.Model.Container.Sight;
import LostSquad.Model.Characters.Trader;
import LostSquad.Model.Characters.Enemy;
import LostSquad.Model.Characters.Nosalis;
import LostSquad.Model.Characters.Npc;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import LostSquad.View.MainScene;
import javafx.scene.Scene;

public class LostSquad extends Application{
      
    @Override
    public void start(Stage stage) throws Exception {
  
        Container ground;
        Container chest;
        List<Item> liste;
        Bauble babiole;
        Trader trade;
        Npc people;
        Enemy bandit;
        Nosalis nosalis;
        Door porte;
        
        //---------------------------------------------------------------------------------------
        
        Place place = new Place("Start","");
        place.setOnEnter("You are in the place where you fell just before, Everything is cover in darkness, But you notice that you can only continue [forward]. but you see something on ground (you can click on button loot for see more and use drag and drop on the left white list)");
        place.setOnLook("When you look around, you notice that there are rail on the ground, one direction is blocked, but you can still go [forward]");
        
        ground = new Container("ground","That's a pretty nice chest",12,new ArrayList<>());
        ground.addItem(new Heal("bandage",3,"heal",20));
        ground.addItem(new Heal("bandage",3,"heal",20));
        ground.addItem(new Key("generator key",5,"a dirty key where you can see written \"generator\"",386));
        ground.addItem(new Kevlar("divine",30,"ancestral armor",20));
        ground.addItem(new Weapon("knife",20,"cut things",20,10, 100, 50));
        ground.addItem(new Ammo("calibre9",2,"ces balle de 9mm sont de tres bonne qualitées",10));
        ground.addItem(new FireArmWithMod("ak-47",10,"shoots 7.62 bullets",20,25,50,70,"7.62",new Ammo("7.62",15,"The 7.62 are the most knowed Ammo in mosco",5)));
        ground.addItem(new Sight("red dot sight",10,"A red dot sight upgrade for your weapon",15));
        
        ground.addItem(new Ammo("7.62",4,"The 7.62 are the most knowed Ammo in mosco",5));
        place.addChest(ground);
        
        porte = new Door("Metro1","forward");
        porte.setTravelText("You walk further in the dark, following the only way usable.");
        porte.setLookText("You cannot see anything, It's too dark");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("Metro1","");
        place.setOnEnter("You are walking inside the tunnels, but after some minutes, You see a light even further away, You can still walk [forward] or just go [back]");
        place.setOnLook("There are rail on the ground, you can still go [forward] or go [back] if you are effraid..");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("Start","back");
        porte.setTravelText("You go back like a effraid kid");
        porte.setLookText("You can only see darkness, Nothing is following you, That's just the place were you fell before.");
        place.addExit(porte);
        
        porte = new Door("Metro2","forward");
        porte.setTravelText("You continue forward in the direction of the light");
        porte.setLookText("You can only see a little bit of light far away on the right.");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("Metro2","");
        place.setOnEnter("You arrived near the light, It's a [door] with a \"Emergency Exit\" sign over it.");
        place.setOnLook("The rail forward are now blocked by a dead train, But there is a [door] with a little window on it");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);

        porte = new Door("Metro1","back");
        porte.setTravelText("You go back");
        porte.setLookText("You cant see anything behind you, it's too dark");
        place.addExit(porte);
        
        porte = new Door("stairRoom","door");
        porte.setTravelText("You open the door and go throught it");
        porte.setLookText("As you look throught the window, you can see nothing special, It's a calm room with stairs");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("stairRoom","");
        place.setOnEnter("You are in the room wich is bigger than you thought, Nothing seems to be dangerous, You are in front of the [stairs]");
        place.setOnLook("After a little moment, you notice that you hear a little motor sound on your [right]");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        liste = new ArrayList<>();
        chest=new Container("chest","That's a pretty nice chest",12,liste);
        chest.addItem(new Heal("bandage",3,"heal",20));
        chest.addItem(new Ammo("7.62",4,"The 7.62 are the most knowed Ammo in mosco",15));
        chest.addItem(new Grip("grip",15,"to sustain your weapon better",10, 20));
        chest.addItem(new Ammo("7.62",4,"The 7.62 are the most knowed Ammo in mosco",15));
        chest.addItem(new Bauble("vis",0,"une vise"));
        chest.addItem(new FireArm("little",20,"it's small gun just enough to scare",6,8,20,50,"calibre9",new Ammo("calibre9",2,"ces balle de 9mm sont de tres bonne qualitées",5)));
        chest.addItem(new Cross("cross",10,"improve precision",20));
        chest.addItem(new Barrel("flash-hider",20,"aggrus damage",20,10));
        
        place.addChest(chest);
        
        porte = new DoorLockKey(386,"stairRoom2","right");
        porte.setTravelText("You go away from the stair and walk on your right.");
        porte.setLookText("Even with the light of outside wich came from the stairs, you cant see the end of the room");
        place.addExit(porte);
        
        porte = new Door("outside","stairs");
        porte.setTravelText("You climb the stairs and reach outside, The air is refreshing");
        porte.setLookText("The [stairs] are in stones and looks safe to climb.");
        place.addExit(porte);
        
        porte = new Door("Metro2","back");
        porte.setTravelText("You go back");
        porte.setLookText("You cant see anything behind you, it's too dark");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        place = new Place("stairRoom2","");
        place.setOnEnter("You enter in a room wich contain a generator, There is pretty much nothing, you can only see a [desc] with a [paper] on it and the door from were you came [back]");
        place.setOnLook("A generator cant be in that good shape despite human activity around it.");

        ground = new Container("desc","The desc is in good shape",1000,new ArrayList<>());
        place.addChest(ground);
        
        babiole=new Bauble("paper",0,"Hey Andrew ! I hope that you closed the [chest] under the stair, or you gonna have to sleep on a rope this evening");
        ground.addItem(babiole);
        
        place.addChest(ground);
        
        porte = new Door("stairRoom","back");
        porte.setTravelText("You go back");
        porte.setLookText("Just the other part of the room");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------

        place = new Place("outside","");
        place.setOnEnter("You arrived outside,you can trade with travel merchant (with action Loot) .\n You hear music and loud voice coming from a depraved [house] near you");
        place.setOnLook("There is nothing around, Just a little bit of water on the ground, The only way to continue is the door leading to this [house] and you can always go [back]");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        chest = new Container("sac","Shop",50,new ArrayList<>());
        chest.addItem(new Heal("bandage",13,"heal",20));
        chest.addItem(new Heal("bandage",3,"heal",10));
        chest.addItem(new Kevlar("armor",30,"this armor protects you",70));
        chest.addItem(new Barrel("quiet",15,"reduced damage aggrus precision",-10,25));
        chest.addItem(new Barrel("flash-hider",20,"aggrus damage",20,10));
        chest.addItem(new Heal("bandage",13,"heal",20));
        chest.addItem(new Heal("bandage",3,"heal",10));
        chest.addItem(new Cross("hand guard",15,"improves precision",20));
        chest.addItem(new Heal("bandage",3,"heal",10));       
        
        trade = new Trader("Tibo","this man is strong to lift such a big bag","hello traveler I'm waiting for your coming, look I have lots of interesting objects",1000,chest,new FireArm("ak-47",120,"on dirait un BFG10k",126,158,80,90,"7.62",new Ammo("7.62",4,"the 7.62 are the most famous ammunition in the Moscow metro",30)));
        place.addCharacter(trade);
        
        porte = new Door("house","house");
        porte.setTravelText("You enter in the house");
        porte.setLookText("There is a windows, When you look throught it you can see a man with a gun, You notice a other guy with a lot of blood on him");
        place.addExit(porte);
        
        porte = new Door("stairRoom","back");
        porte.setTravelText("You go back");
        porte.setLookText("you can see stair, wich lead you to the dark hell..");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------

        place = new Place("house","");
        place.setOnEnter("You see a man sit on a chair there is blood everywhere but he is not feared of death.");
        place.setOnLook("You hear other bandits around, you have 3 choice : a random [door], the door of the [kitchen], and the door [out]");
        
        ground = new Container("ground","le sol est recouvert de saltés",1000,new ArrayList<>());
        place.addChest(ground);
        
        liste = new ArrayList<>();
        chest=new Container("chest","it's a beautiful chest",12,liste);
        chest.addItem(new Heal("bandage",3,"healed",20));
        chest.addItem(new Heal("bandage",3,"healed",20));
        chest.addItem(new Ammo("7.62",4,"the 7.62 are the most famous ammunition in the Moscow metro",20));
        
        bandit=new Enemy("Andrew","It's a strong man with death in his eyes",20,null,chest,new FireArm("little",20,"It's a little gun",6,8,20,50,"calibre9",new Ammo("calibre9",2,"This 9mm Ammo are really good !qualitées",20)));
        place.addCharacter(bandit);
        
        porte = new DoorLockKey(12,"house2","door");
        porte.setTravelText("");
        porte.setLookText("This door is locked you cant do anything !");
        place.addExit(porte);
        
        porte = new Door("house3","kitchen");
        porte.setTravelText("You go on your right, Straight into the kitchen");
        porte.setLookText("This door is covered with blood and it smell.. Really bad..");
        place.addExit(porte);

        porte = new Door("outside","out");
        porte.setTravelText("You go outside this house");
        porte.setLookText("this door leads outside");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("house3","");
        place.setOnEnter("When you arrived into the kitchen, You see a door leading to the [heart] of the house, and a [storage] door. Also you can go back to the [home]");
        place.setOnLook("This kitchen look like every other in the Polis Station, A beautifull cooking machine and many other kitchen thing are here, But it seems to be infested by insects..\n");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("house4","heart");
        porte.setTravelText("You enter in the room on your left, Two man heavily equiped is waiting for you, They want you dead, Now");
        porte.setLookText("This door lead to the heart of the house");
        place.addExit(porte);
        
        porte = new Door("débarras","storage");
        porte.setTravelText("You enter in the storage");
        porte.setLookText("It's a big and heavy door");
        place.addExit(porte);
        
        porte = new Door("house","home");
        porte.setTravelText("You go back at the entrance");
        porte.setLookText("This door is still covered in blood");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("débarras","");
        place.setOnEnter("When you get in, You feel a little cold wind coming from the broken windows.\n You find 3 crate : [chest1][chest2][chest3]");
        place.setOnLook("There is nothing exept a broken window and the 3 chest on the ground, You can go [back]");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        liste = new ArrayList<>();
        chest=new Container("chest1","it's a big wardrobe",12,liste);
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Key("storage key",5,"someone has lost this key",1234));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        place.addChest(chest);
        
        
        liste = new ArrayList<>();
        chest=new Container("chest2","it's a big wardrobe",12,liste);
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        place.addChest(chest);
        
        liste = new ArrayList<>();
        chest=new Container("chest3","it's a big wardrobe",12,liste);
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));chest.addItem(new Heal("meat",3,"moldy but edible",2));
        chest.addItem(new Heal("meat",3,"moldy but edible",5));
        place.addChest(chest);
        
        
        porte = new Door("house3","back");
        porte.setTravelText("You go back in the kitchen");
        porte.setLookText("This door is heavy and hard to open");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("house4","");
        place.setOnEnter("The two man are sit around a handcrafted fire and a bowl of boiling water, with a little bit of VDNKH Tea");
        place.setOnLook("Nothing special around, The fire is warming you a lot\n you can see a [stair] leading to a second stair, and a [ark] leading to the next part of the living room, and the door leading back to the [kitchen]");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        liste = new ArrayList<>();
        chest=new Container("inventory","FortuneBag",12,liste);
        chest.addItem(new Heal("bandage",3,"heal",20));
        chest.addItem(new Heal("bandage",3,"heal",20));
        chest.addItem(new Ammo("7.62",4,"The 7.62 are the most knowed Ammo in mosco",5));
        
        bandit=new Enemy("alexis","He is beautifull and strong but smell like a potatoe",20,new Kevlar("armor",0,"The must have of a good survivor",50),chest,new FireArm("little",20,"Just a little gun",6,8,20,50,"calibre9",new Ammo("calibre9",2,"Really good quality 9mn Ammo",20)));
        place.addCharacter(bandit);
        
        liste = new ArrayList<>();
        chest=new Container("inventory","FortuneBag",12,liste);
        chest.addItem(new Heal("bandage",3,"heal",20));
        chest.addItem(new Sight("holographic",10,"A holographic upgrade for your weapon",15));
        
        bandit=new Enemy("Anton","His armor look really strong",20,new Kevlar("armor",0,"The must have of a good survivor",50),chest,new FireArm("little",20,"Just a little gun",6,8,20,50,"calibre9",new Ammo("calibre9",2,"Good quality 9mn Ammo",20)));
        place.addCharacter(bandit);
        
        porte = new Door("stairhouse","stair");
        porte.setTravelText("You are going up");
        porte.setLookText("");
        place.addExit(porte);
        
        porte = new Door("house2","ark");
        porte.setTravelText("You are going to the next part of the living room");
        porte.setLookText("This ark will no stay here eternally..");
        place.addExit(porte);
        
        porte = new Door("house3","kitchen");
        porte.setTravelText("You go back to kitchen");
        porte.setLookText("This door is leading to the kitchen");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("house2","");
        place.setOnEnter("it is a large living room, a broken telephone, two settees completely torn apart, we can see them spring, empty cupboards, a [storage] door with a window and the famous [door] to close when you arrive in this house");
        place.setOnLook("you are intrigued by the sofa, you search it and you find nothing in particular some chocolate bar that has been in existence for 30 years \\ n when you get up you notice that [ark] is supported by fixed iron bars that the bandits added to support it");
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new DoorLockKey(12,"house","door");
        porte.setTravelText("");
        porte.setLookText("this door is locked, impossible to force the lock or to break down the door");
        place.addExit(porte);
        
        porte = new DoorLockKey(1234,"storage","storage");
        porte.setTravelText("you enter the storage room");
        porte.setLookText("this door seems to close you notice thanks to the window of the storage of weapons and the ammunition boxes you suppose that it is there which stores their material (Chaque porte a sa clef et chaque clef a sa porte)");
        place.addExit(porte);
        
        porte = new Door("house4","ark");
        porte.setTravelText("you head to the foyer, this makes you feel good");
        porte.setLookText("supported by iron bars if he collapsed on a person he would be impaled");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        place = new Place("storage","");
        place.setOnEnter("in fact you enter this room filled with stuff two boxes are offered to you [boxmun] [box]");
        place.setOnLook("there are plenty of tools that allowed to repair and build the weapons of good fortune of these bandits");

        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        chest = new Container("boxmun","it is a box with a lot of varied charger",10,new ArrayList<>());
        chest.addItem(new Ammo("calibre9",2,"these 9mm balls are very good quality",15));
        chest.addItem(new Ammo("calibre9",2,"these 9mm balls are very good quality",15));
        chest.addItem(new Bauble("screwdriver",5,"outil"));
        chest.addItem(new Ammo("7.62",4,"the 7.62 are the most famous ammunition in the Moscow metro",10));
        chest.addItem(new Ammo("rocket",50,"a shock could make it explode",1));
        place.addChest(chest);
        
        chest = new Container("box","this box is full",8,new ArrayList<>());
        chest.addItem(new Weapon("knife",15,"very sharp",10,15,20,90));
        chest.addItem(new Bauble("harmmer",5,"tool"));
        chest.addItem(new FireArm("little",20,"it's small gun just enough to scare",6,8,20,50,"calibre9",new Ammo("calibre9",2,"ces balle de 9mm sont de tres bonne qualitées",20)));
        chest.addItem(new Ammo("calibre9",2,"these 9mm balls are very good quality",6));
        chest.addItem(new Bauble("scrap",5,"tool"));
        chest.addItem(new Bauble("gunpowder",2,"we can't get anything out of it"));
        chest.addItem(new Cross("cross",10,"improve precision",20));
        chest.addItem(new Bauble("bolt",5,"tool"));
        place.addChest(chest);
        
        porte = new Door("house2","back");
        porte.setTravelText("you go to the living room");
        porte.setLookText("it's a beautiful, well-built door");
        place.addExit(porte);
        
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("stairhouse","");
        place.setOnEnter("Bravo, vu que vous êtes arrivés jusqu'ici le jeu vous a plu. Alors si vous voulez pouvoir continuer l'aventure, on mérite surment la licence svp(c'est un easter egg, c'est pour le travail). Avez-vous trouvé le boss caché!?");
        place.setOnLook("you find that these steps support your weight well since you have not gone through");
        
        ground = new Container("ground","The Stairs is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("house4","down");
        porte.setTravelText("you are heading towards the fire");
        porte.setLookText("the markets cracked on every step");
        place.addExit(porte);
        
        porte = new Door("firstfloor","up");
        porte.setTravelText("the markets cracked on every step");
        porte.setLookText("the markets crack on every step");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("firstfloor","");
        place.setOnEnter("you are in a corridor, it has a door [door] and you can continue in the direction of the corridor [forward] or take the stairs [stairs]");
        place.setOnLook("when you look up you see lots of cracks on the walls and the full which testifies to a collapse");

        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("firstfloor2","forward");
        porte.setTravelText("you continue ahead in the corridor you feel something strange, a presence, a kind of premonition, you have flash rooms filled with computers, programmers, students happy with their grades, you see the word JAVA without power say what it is");
        porte.setLookText("this hallway looks normal from where you are");
        place.addExit(porte);
        
        porte = new Door("bedroom","door");
        porte.setTravelText("you enter a bedroom.");
        porte.setLookText("it is a wooden door of pink color with a frame where it is written Julia");
        place.addExit(porte);
        
        
        porte = new Door("stairhouse","stairs");
        porte.setTravelText("you head for the stairs");
        porte.setLookText("the stairs you just took inspires me more confidence");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("bedroom","");
        place.setOnEnter("you see when entering the room the huge hole in the roof, and a Nosalis on the bunk bed he must have passed through the roof to come here");
        place.setOnLook("looking around you see a door leading to a bathroom, one leading to the hallway, as well as a closed door and continuing to examine \nthe room you confirm that it is fine a room more precisely a room for two little girls because all the rooms are painted pink and by most of the mountains \nfluff crushed torn or even burned \\ n deep inside you would like you that these little girls were not dead under the bombs but \nimpossible to know");

        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        chest = new Container("mouth","between the teeth of this beast is something go find out why it is",1,new ArrayList<>());
        chest.addItem(new Key("keychest",0,"it is written (Andrew's chest)",56));
        nosalis = new Nosalis("nosalis",chest);
        place.addCharacter(nosalis);
        
        porte = new Door("prison","door");
        porte.setTravelText("the door seems to be stuck so you push with all your strength and you manage to enter");
        porte.setLookText("this door is covered with metal plate as if we wanted to strengthen it");
        place.addExit(porte);
        
        porte = new Door("firstfloor","corridor");
        porte.setTravelText("you go back to the corridor");
        porte.setLookText("on the door side you notice lines above with dates, when you approach you see 7 lines with for each date a year difference and on the other the same thing except that the last line is higher by a good twenty centimeter");
        place.addExit(porte);
        
        porte = new Door("bathroom","bathroom");
        porte.setTravelText("you go to the bathroom");
        porte.setLookText("it is a very classic door aged by time but which remains robust");
        place.addExit(porte);
        
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("bathroom","");
        place.setOnEnter("the bathroom is covered with a large carpet and in the bathtub there is a cushion and a sheet, you also see a [chest] at the foot of the bathtub and the door where you come from [back]");
        place.setOnLook("You search the bathroom, you look everywhere, from the mold of the cupboards to the asbestos slabs of the walls to the house of the dust mites, you do not find anything, it is then that you press on the window to breathe a little that you see below the window a box [pandora] on a board");

        liste = new ArrayList<>();
        liste.add(new Bauble("lanceRocket",25,"the state of a certain room freezes your blood how can you not take care of such a weapon \n, you decide not to use it in this state at the risk of blowing yourself up"));
        liste.add(new Bauble("douille",25,"small metal container of a bullet, which contains gunpowder"));
        liste.add(new Kevlar("gilet",20,"The must have of a good survivor",50));
        chest=new ContainerLockKey("chest","medium chest",6,liste,56);
        place.addChest(chest);
        
        chest=new Container("pandore","medium trunk has the area of ​​being extremely heavy",6,new ArrayList<>());
        chest.addItem(new Bauble("gold",60,"worth a small fortune these days"));
        chest.addItem(new Bauble("gold",60,"worth a small fortune these days"));
        chest.addItem(new Kevlar("divine",30,"ancestral armor",100));
        chest.addItem(new Bauble("gold",60,"worth a small fortune these days"));
        chest.addItem(new Bauble("gold",60,"worth a small fortune these days"));
        place.addChest(chest);
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("bedroom","back");
        porte.setTravelText("you go to the bedroom");
        porte.setLookText("it is a very classic door aged by time but which remains robust");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("prison","");
        place.setOnEnter("once the door is open, you come across a huge cage with someone inside a person lying down, a [door] leads inside");
        place.setOnLook("la porte de la cage [cell] d'un coté la porte vers la chambre [back] de l'autre et juste à coté de la cage un bureau [desk] vous titille de curiousité");

        chest = new Container("desk","small desk there is room for a small laptop and for writing perfect for a student with a little drawer to store things",4,new ArrayList<>());
        chest.addItem(new Key("keycell",0,"it's a key, nothing specific about it",42));
        chest.addItem(new Bauble("picture",3,"a picture of a child playing with his dog"));
        chest.addItem(new Bauble("collier",1,"a dog collar we read tobi"));      
        place.addChest(chest);
        
        porte = new DoorLockKey(42,"cell","cell");
        porte.setTravelText("you enter the cage");
        porte.setLookText("this door is solid no way to explode");
        place.addExit(porte);
        
        porte = new Door("bedroom","back");
        porte.setTravelText("the door is easier to open from this strange side");
        porte.setLookText("on this side the door can be barricaded by an artisanal hinge system but most are not even targeted");
        place.addExit(porte);
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("cell","");
        place.setOnEnter("once inside you see your comrade [Alan], lying on the ground");
        place.setOnLook("nothing really extraordinary a prison with the only entry / exit of the cage door [cell]");

        porte = new Door("prison","cell");
        porte.setTravelText("you leave your comrade here he will slow you down as you progress, put the name of the guy will start again once the wound is healed");
        porte.setLookText("these a barred door");
        place.addExit(porte);
        
        chest = new Container("pocket","small pocket with holes",1,new ArrayList<>());
        chest.addItem(new Bauble("medallion",3,"a medallion in the shape of a Catholic cross"));
        people = new Npc("Alan",false,"by examining him you notice that his leg is broken, he has cuts all over his arms","lieutenant it's you thank god these bandits stumbled upon me and my badly banged up and jailed here \n i would like to help but they broke my leg because i tried to run away you are there is that there is no one left I will rest a bit and I will go to the entrance of Kitai-Gorod station which is not very far from here \n, it is my destination before ending up here, \nstart now you have to reach Polis as quickly as possible",0,null,chest,null);
        place.addCharacter(people);
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("firstfloor2","");
        place.setOnEnter("after your little hallucination due to asbestos, a shadow approaches you");
        place.setOnLook("looking more closely you have in this corridor than the corp of a bandit, one [door] leading to another room and the other side of the hall leading to the stairs [backward]");
        
        bandit = new Enemy("Tibo","only a fool can attack you naked and by hand",100,null,null,null);
        place.addCharacter(bandit);
        
        porte = new Door("firstfloor","backward");
        porte.setTravelText("step over the corpse to come back to the hallway");
        porte.setLookText("it's a beautiful door");
        place.addExit(porte);
        
        porte = new Door("firstfloor3","door");
        porte.setTravelText("you open the door");
        porte.setLookText("it's a nice door");
        place.addExit(porte);
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("firstfloor3","");
        place.setOnEnter("you arrive in a very bright room, because the walls and the roof are missing, part of the ground is collapsed. \nYou find yourself outside again from this height you see the sign for the entrance of the Kitai-Gorad a about 2km as the crow flies");
        place.setOnLook("you can go back inside the house by the [door] or go down by the ground [collapse]");

        porte = new Door("firstfloor2","door");
        porte.setTravelText("you go back inside");
        porte.setLookText("this door takes you back to the house where corpses abound");
        place.addExit(porte);
        
        porte = new Door("outside2","collapse");
        porte.setTravelText("you descend carefully through the collapsed passage");
        porte.setLookText("at the bottom there are sandbags, it is surely a post-avant-garde, is you notice a person it seems not dangerous");
        place.addExit(porte);
        
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("outside2","");
        place.setOnEnter("you arrive at the bottom, the man turns around, sits down and takes out his several objects from his bag it is surely a walker, there is also a road which leads to a residence [road], the only one collapsed in the still passable area [collapse] ");
        place.setOnLook("there is not much just full of sandbags, if you got here these bandits you will have killed in a few seconds");

        
        chest = new Container("sac","Shop",20,new ArrayList<>());
        chest.addItem(new Heal("bandage",13,"heal",20));
        chest.addItem(new Heal("bandage",3,"heal",10));
        chest.addItem(new Kevlar("armor",30,"this armor protects you",70));
        chest.addItem(new Barrel("quiet",15,"reduced damage aggrus precision",-10,25));
        chest.addItem(new Barrel("flash-hider",20,"aggrus damage",20,10));
        chest.addItem(new Heal("bandage",13,"heal",20));
        chest.addItem(new Heal("bandage",3,"heal",10));
        chest.addItem(new Cross("hand guard",15,"improves precision",20));
        chest.addItem(new Heal("bandage",3,"heal",10));
        
        
        trade = new Trader("pamuelleseltier","this man is strong to lift such a big bag","hello traveler I'm waiting for your coming, look I have lots of interesting objects",1000,chest,new FireArm("ak-47",120,"on dirait un BFG10k",126,158,80,90,"7.62",new Ammo("7.62",4,"the 7.62 are the most famous ammunition in the Moscow metro",30)));
        place.addCharacter(trade);
        ground = new Container("ground","The ground is dirty",1000,new ArrayList<>());
        place.addChest(ground);
        
        porte = new Door("firstfloor3","collapse");
        porte.setTravelText("you go up the slope avoiding slipping");
        porte.setLookText("this slope is very steep");
        place.addExit(porte);
        
        porte = new Door("end","road");
        porte.setTravelText("you walk towards the residence, around you death and destruction, the sound of the wind comes you scream in your ears, you hope to reach a warm place before dark");
        porte.setLookText("this road seems to be used a lot");
        place.addExit(porte);
        
        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        place = new Place("end","");
        place.setOnEnter("TO BE CONTINUE");
        place.setOnLook("");

        place.saveToFile();
        
        //---------------------------------------------------------------------------------------
        
        System.out.print(" ██▓    ▒█████    ██████ ▄▄▄█████▓\n" +
                         "▓██▒   ▒██▒  ██▒▒██    ▒ ▓    ██▒ ▓▒\n" +
                         "▒██░   ▒██░  ██▒░ ▓█▄   ▒    ▓██░ ▒░\n" +
                         "▒██░   ▒██   ██░  ▒   ██▒░   ▓██▓ ░ \n" +
                         "░█████░ ████▓▒░▒██████▒▒ ▒██▒ ░ \n" +
                         "░ ▒░▓  ░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░  ▒ ░░   \n" +
                         "░ ░ ▒    ░ ▒ ▒░ ░ ░▒  ░ ░    ░    \n" +
                         "  ░ ░  ░ ░ ░ ▒  ░  ░  ░    ░      \n" +
                         "    ░      ░ ░        ░           \n" +
                         "\n");
        System.out.print("   ██████   █████   █       ██     ▄▄▄     ▓█████▄\n");
        System.out.print("▒██    ▒ ▒██▓  ██▒  ██    ▓██▒ ▒████▄   ▒██▀ ██▌\n");
        System.out.print(" ░ ▓██▄   ▒██▒  ██░▓██   ▒██░▒██    ▀█▄ ░██   █▌\n");
        System.out.print("    ▒   ██▒░██  █▀ ░▓▓█   ░██░░██▄▄▄▄██░▓█▄   ▌\n");
        System.out.print("▒██████▒▒░▒███▒█▄ ▒▒█████▓ ▒▓█   ▓██░▒████▓\n");
        System.out.print("▒ ▒▓▒ ▒ ░░░ ▒▒░ ▒ ░▒▓▒ ▒ ▒ ░▒▒   ▓▒█ ▒▒▓  ▒\n");
        System.out.print("░ ░▒  ░   ░ ▒░  ░ ░░▒░ ░ ░ ░ ░   ▒▒  ░ ▒  ▒\n");
        System.out.print("░  ░  ░     ░   ░  ░░░ ░ ░   ░   ▒   ░ ░  ░\n");
        System.out.print("         ░      ░       ░           ░     ░  \n");
                                                                                
        
        System.out.print("------Intro------\nTwenty year as passed since the nuclear apocalypse who have caused the end of the world,\n"
                    + "The only few survivor have to get underground to survive and to flee the radiation of the surface. \n"
                    + "Inside the deep underground of Moscou, the survivor organized themselves in micro society and \n"
                    + "survive like they can to the war, Starvation and diseases.\n\n");
        System.out.print("We’re in 2033, You are Nikola patroski a stalker unit lieutenant, charged to search and collect \n"
                + "precious item at the surface of the irradiated world, For the \\“Polis\\” Station wich is the center point \n"
                + "of the \\”Spartiates\\”. One of you officer told you to form the newbies in a real situation. \n"
                + "And while doing that, You notice the presence of a unknown mutant nest. As you walk silently toward \n"
                + "the nest with your team, You saw many terrible things, The nest was the biggest you never seen, \n"
                + "It was really deep and seems endless, You noticed many Nosalise( Big mutant dog ) deep down the nest, \n"
                + "Crawling around like insect. You carry your breath but not surrender to fear, Whereas, That wasn’t the case \n"
                + "for one of you solider who was horrified by this vision of hell.. He Tried to run away in fear without \n"
                + "thinking, But as soon as he get up and started to run, Every mutant around noticed his presence and started \n"
                + "to charge. One for all and all for One, You charge with the rest of your team, to help him, But there was no \n"
                + "way for you to win, They were to many, Because you cannot see your teammate dying in front of you, You called \n"
                + "the retreat, But it was too late, Every survivor tried to run as fast as they can, And you too are running. \n"
                + "In the panic, The ground opened under you feet and you fall into a deep dark hole… You just woke up, \n"
                + "You don’t know how many time you slept, But now only one thing matter, save your mates if you can and warn the \n"
                + "Polis station about this insane Nest.\n\n");   
        
        MainScene myScene = new MainScene("mainScene");
        
        // Scene dimensions
        double width = 1080.0, height = 766.0;
        Scene scene = new Scene(myScene, width, height);
        stage.setScene(scene);
        stage.setTitle("LOST SQUAD");
        stage.show();
    }  
    
    public static void main(String[] args) {
        launch (args);
    }
    
}

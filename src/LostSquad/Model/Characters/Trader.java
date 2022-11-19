package LostSquad.Model.Characters;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import java.util.List;

//class Marchand
public class Trader extends Npc{
	
	public Trader(String name,String destext,String speak,int money,Container contain,Weapon arm){
		super(name,false,destext,speak,money,new Kevlar("armor",10,"protected",100),contain,arm);
	}
        
        public Trader(String name,String text,String speak,int money,List<Item> contain,int capacity,String descripContain,Weapon arm){
		super(name,false,text,speak,money,new Kevlar("armor",10,"protected",100),contain,capacity,descripContain,arm);
	}

        //printStock() le marche parle et montre son inventaire
	protected void printStock(){
                System.out.printf("%s",getSpeak());
                System.out.print("\n\nMarket : \n");
                printInventory();
        }

        //buyItem(Container inventoryPlayer,Item item) prend l'inventaire du player
        //et l'item qu'on veut obtenir se qui augmentera l'argent du marchant
	protected void buyItem(Container inventoryPlayer,Item item){
		inventoryPlayer.addItem(item);
                setUpMoney(item.getPrice());
		getContainer().removeItem(item);
	}

}
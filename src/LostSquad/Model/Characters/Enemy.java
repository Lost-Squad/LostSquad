package LostSquad.Model.Characters;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import java.util.List;

public class Enemy extends Npc{

	public Enemy(String name,String text,int money,Kevlar armor,Container contain,Weapon arm){
		super(name,true,text,null,money,armor,contain,arm);
	}
        
        public Enemy(String name,String text,int money,Kevlar armor,List<Item> contain,int capacity,String descripContain,Weapon arm){
		super(name,true,text,null,money,armor,contain,capacity,descripContain,arm);
	}
}
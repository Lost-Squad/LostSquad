/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LostSquad.Model.Characters;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Heal;
import java.util.List;

public class Npc extends Entity{

    private boolean aggressive;   //Boolean qui si vrai il attaquera les joueur sinon non
    private final Weapon arm;     //Variable qui correspond a l'item qui doit etre un Weapon avec lequel il attaquera
    private final String speak;   //String de dialogue

    
    public Npc(String name ,boolean aggre,String description,String speak,int money,Kevlar armor,Container sac,Weapon arm){
        super(name,description,money,armor,sac);
	this.aggressive=aggre;	
        this.arm=arm;
        this.speak=speak;
    }

    public Npc(String name ,boolean aggre,String description,String speak,int money,Kevlar armor,List<Item> liste,int capacity,String descripContain,Weapon arm){
	super(name,description,money,armor,liste,capacity,descripContain);
	this.aggressive=aggre;
        this.arm=arm;
        this.speak=speak;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.print("Aggressive : " + this.aggressive + "\n");
        if(this.arm==null){
                System.out.printf("he use main, damages : 5, damages critics : 0\n");
            }else{
                this.arm.printStats();  
            }
    }
    
    @Override
    public void healing(Heal nameItem){
        if(!(this instanceof Enemy)) {
            this.aggressive=false;
            System.out.printf("%s find you nice\n",getName());
        }
        super.healing(nameItem);
    }
    
    //recuptionMoney() permet au player de recuper l'argent du Npc
    public int recupMoney(){
        int res=this.getMoney();
        this.setDownMoney(res);
        return res;
    }
    
    //getSpeak() return le String de dialogue du Npc
    public String getSpeak(){
        return this.speak;
    }

    //getWeapon() return le Weapon du Npc
    protected Weapon getWeapon(){
        return this.arm;
    }
    
    //getAggressive() return le Boolean d'aggressive
    protected boolean getAggressive(){
        return this.aggressive;
    }
    
    //setAggre() change l'aggressive en vrai
    protected void setAggre(){
        this.aggressive=true;
    }
    
    //hit (int degat) reprend celui de sa class mere et rajoute un condition qui
    //est si il est pas aggressif il le deviendra
    @Override
    public void hit (int degat){
        super.hit(degat);
	if (this.aggressive==false){
            System.out.printf("%s is hurt and he will now kill you\n",getName());
            setAggre();
        }
    }
        
    //attack(Player character) attack le player an fonction de sa variable arm
    public String attack(Player character){
	if(getLife()>0 && this.aggressive){
            if(this.arm==null){
                character.hit(5);
                return ""+this.getName()+" no have weapon,and he hit you whit hand";
            }
            else if(this.arm instanceof FireArm){
                if(((FireArm)this.arm).isOutofAmmo()){
                    character.hit(5);
                    return ""+this.getName()+" no have ammo, he hit you whit hand";
                }else{
                    int r=(int) (Math.random()*10);
                    if(this.arm.attack(character) && r>8){
                        character.hurting();
                        return ""+this.getName()+" attack and you blood";
                    }
                    return ""+this.getName()+" attack";
                }
            }else{
                this.arm.attack(character);
                return ""+this.getName()+" attack";
            }

	}
        return "this person is dead or just frendly";
    }
    
}

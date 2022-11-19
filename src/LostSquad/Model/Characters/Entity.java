package LostSquad.Model.Characters;

import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Heal;
import java.io.Serializable;
import java.util.List;
import LostSquad.Model.Interfaces.Descriptible;

public class Entity implements Serializable,Descriptible {

	private final String name;          //nom de l'Entity
	private int life;                   //son niveau de vie
	private final Container inventory;  //son Container qui lui sert d'inventaire
	private int money;                  //nombre correspondant a son argent
        private final String description;   //String correnspondant a un text de description d'Entity
        private final int HEAL_MAX=100;     //niveau de vie par default
        private Kevlar armor;               //son item Kevlar
        

	

public Entity(String name,String description,int money,Kevlar armor,Container sac){
	this.name = name;     
	this.life = HEAL_MAX;
        
        this.armor=armor;
        
	this.inventory = sac; 
	this.money=money;
        
        this.description=description;  
}

public Entity(String name ,String description ,int money,Kevlar armor ,List<Item> liste,int capacity,String des){
	this.name = name;
	this.life = 100;
        this.armor=armor;
	this.inventory = new Container("sac",des,capacity,liste);
	this.money=money;
        this.description=description;
}

@Override
public void onLook() {
    System.out.printf("%s\nlife : %d\n", this.description,this.life);
    if(this.armor!=null){
        System.out.printf("Kevlar : %d\n",this.armor.getProtection());
    }
}
    
@Override
public String getName(){
    return this.name;
}

//healing(Heal nameItem) avec un item Heal l'entity sera soigné et sa vie augmentera
public void healing(Heal nameItem){   
    setLife(nameItem.getHeal());
    System.out.printf("%s are healing\n",getName());
}

//setKevlar(Kevlar armor) permet de changer le Kevlar le change si il y en avait pas avant et se supprime de l'inventaire
//si on remettera l'ancient Kevlar dans l'inventaire
public void setKevlar(Kevlar armor){  
   if(this.armor==null){
        this.armor=armor;
        getContainer().removeItem(armor);
   }else{
       Item oldK = this.armor;
       this.armor=armor;
       getContainer().removeItem(armor);
       getContainer().addItem(oldK);
    }
}

public Kevlar getKevlar(){
    return this.armor;
}

//printInventory() affiche le Container de l'Entity sur la console
protected void printInventory(){
        this.inventory.onLook();
}

//seeLife() affiche la vie de l'Entity sur la console
public void seeLife(){
    System.out.printf("Life : %d \n", this.life);
    if(this.armor!=null){
       System.out.printf("Kevlar : %d \n", this.armor.getProtection()); 
    }
}

//setLife(int heal) augment l'entier de la variable life de l'entity sans depasser son HEAL_MAX
protected void setLife(int heal){
    this.life+=heal;
    if(this.life>HEAL_MAX){
        this.life=HEAL_MAX;
    }
}

//getLife() return la variable Life
public int getLife(){
    return this.life;
}


protected void setDownLife(int degat){
    this.life=this.life-degat;
}

//getMoney() return la variable Money
public int getMoney(){
	return this.money;
}

//setDownMoney(int enleve) reduit l'entier de la variable money
protected void setDownMoney(int enleve){
	this.money-=enleve;
}

//setUpMoney(int enleve) augmente l'entier de la variable money
protected void setUpMoney(int ajout){
	this.money+=ajout;
}

//getContainer() return la variable Container
public Container getContainer(){
	return this.inventory;
}

//hit(int degat) si l'Entity est attack alors la variable degat est l'entier de damage qu'il reçoit
//si il a pas de Kevlar alors c'est la varialble qui est touché
//sinon le kevlar prend les damage si la protection tombe a zero il sera detruit, si il restait des damages la Life sera touché
public void hit(int degat){
    if(this.armor!=null){
        int newdegat = this.armor.use(degat); 
        System.out.printf("total %d damage \n%d damages for Kevlar ",degat,degat-newdegat);
        if(this.armor.getProtection()<=0){
            this.armor=null;
        }
        setDownLife(newdegat);
        System.out.printf("and %d damages for life\n",newdegat);
    }else{
	setDownLife(degat);
        System.out.printf("(no Kevlar) %d damages for life\n",degat);
    }
}

    @Override
    public String getDesc() {
        return this.description;
    }

    
}
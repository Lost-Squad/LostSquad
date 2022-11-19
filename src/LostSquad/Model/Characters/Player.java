package LostSquad.Model.Characters;

import LostSquad.Model.Container.Weapon;
import LostSquad.Model.Container.FireArmWithMod;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Container.FireArm;
import LostSquad.Model.Container.Ammo;
import LostSquad.Model.Container.Container;
import LostSquad.Model.Container.Grip;
import LostSquad.Model.Container.Kevlar;
import LostSquad.Model.Container.Barrel;
import LostSquad.Model.Container.Heal;
import LostSquad.Model.Container.Sight;
import LostSquad.Model.Container.Cross;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player extends Entity {
    
    private Weapon armPrincipale;  //Variable qui correspond a l'item qui doit etre un Weapon avec lequel il attaquera
    private boolean hurt;          //Boolean qui indique si le player est blessé
    private final DoubleProperty lifeProperty;
    private final DoubleProperty armorProperty;
    private final IntegerProperty moneyProperty;
    private final IntegerProperty minDps;
    private final IntegerProperty maxDps;
    private final IntegerProperty nbControl;
    private final IntegerProperty nbAccuracy;
    private final IntegerProperty nbMunition;
	
	public Player(Container list){
            super("Nikola Patroski","you are a soldier",15,null,list);
            this.lifeProperty=new SimpleDoubleProperty(this.getLife());
            if(this.getKevlar()==null){
                this.armorProperty=new SimpleDoubleProperty(0);
            }else{
                this.armorProperty=new SimpleDoubleProperty(this.getKevlar().getProtection());
            }
            this.moneyProperty=new SimpleIntegerProperty(this.getMoney());
            this.armPrincipale=null;
            this.minDps = new SimpleIntegerProperty(5);
            this.maxDps = new SimpleIntegerProperty(5);
            this.nbMunition = new SimpleIntegerProperty(0);
            this.nbControl = new SimpleIntegerProperty(0);
            this.nbAccuracy = new SimpleIntegerProperty(100);
            this.hurt=false;
	}
        
        //ajout de fonction nécessaire pour les trois nouvelle variable pour l'affichage(les trois variable IntegerProperty)
        @Override
        public void setKevlar(Kevlar armor){  
            super.setKevlar(armor);
            this.armorProperty.set(this.getKevlar().getProtection());
        }
        
        public DoubleProperty getArmorProperty(){
            return this.armorProperty;
        }
        
        public DoubleProperty getLifeProperty(){
            return this.lifeProperty;
        }
        
        public IntegerProperty getMoneyProperty(){
            return this.moneyProperty;
        }
        
        @Override
        public void hit(int degat){
            super.hit(degat);
            if(this.getKevlar()!=null){
                this.armorProperty.set(this.getKevlar().getProtection());
            }else{
                this.armorProperty.set(0);
            }
            this.lifeProperty.set(this.getLife());
        }
        
        
        //verifHurt() a chaque tour de jeu il verifie si le boolean est sur vrai
        //si c'est la cas on est prend 1 point de vie, il faudra alors se soigner
        //pour repasser la variable a faux
        public void verifHurt(){
            if(this.hurt){
                setDownLife(1);
                System.out.print("you are hurt, you must be healed, 1 damage for your life\n");
            }
        }
        
        //setMod(Item item) une arme peut etre équipé de mod qui améliore la visé
        //cette fonction permet de s'en équiper ou de replacer un deja equipé
        public void setMod(Item item){
            Grip grip = (Grip)((FireArmWithMod)this.armPrincipale).getGrip();
            Barrel barrel = (Barrel)((FireArmWithMod)this.armPrincipale).getBarrel();
            Cross cross = (Cross)((FireArmWithMod)this.armPrincipale).getCross();
            Sight sight = (Sight)((FireArmWithMod)this.armPrincipale).getSight();
            if(item instanceof Grip && grip!=null){                
                ((FireArmWithMod)this.armPrincipale).use(item);
                getContainer().removeItem(item);
                getContainer().addItem(grip);
            }else if(item instanceof Barrel && barrel!=null){               
                ((FireArmWithMod)this.armPrincipale).use(item);
                getContainer().removeItem(item);
                getContainer().addItem(barrel);
            }else if(item instanceof Cross && cross!=null){                
                ((FireArmWithMod)this.armPrincipale).use(item);
                getContainer().removeItem(item);
                getContainer().addItem(cross);
            }else if(item instanceof Sight && sight!=null){
                ((FireArmWithMod)this.armPrincipale).use(item);
                getContainer().removeItem(item);
                getContainer().addItem(sight);
            }else{
                ((FireArmWithMod)this.armPrincipale).use(item);
                getContainer().removeItem(item);
            }
            this.nbControl.set(this.armPrincipale.getControl());
            this.nbAccuracy.set(this.armPrincipale.getAccuracy());
            this.maxDps.set(this.armPrincipale.getDamages()+(this.armPrincipale.getDamages()*this.armPrincipale.getCritics()/100));
            this.minDps.set(this.armPrincipale.getDamages());
        }
        
        public IntegerProperty getMinDps(){
            return this.minDps;
        }
        
        public IntegerProperty getMaxDps(){
            return this.maxDps;
        }
        
        public IntegerProperty getNbControl(){
            return this.nbControl;
        }
        
        public IntegerProperty getNbAccuracy(){
            return this.nbAccuracy;
        }
        
        public IntegerProperty getNbMunition(){
            return this.nbMunition;
        }
        
        
        //isArmPrincipale(Weapon arm) return vrai si le Weapon donnait est le même que l'arme équipé
        public boolean isArmPrincipale(Weapon arm){
            return arm==this.armPrincipale;
        }
        
        //hurting() met la variable hurt sur vrai
        protected void hurting(){
            this.hurt=true;
        }
        
        //getItem(String Item) return null si le String Item ne correspond à aucun nom d'item
        //dans son inventaire sinon return l'item trouvé
        public Item getItem(int id){
            if(this.armPrincipale!=null && this.armPrincipale.getId()==id){
                return this.armPrincipale;
            }
            return getContainer().getItem(id);
        }
        
        //reload(Ammo Ammo) recharge l'arme principale
        public boolean reload(Ammo Ammo){
            if (((FireArm)this.armPrincipale).isOutofAmmo() && ((FireArm)this.armPrincipale).load(Ammo)){
                getContainer().removeItem(Ammo);
                this.nbMunition.set(((FireArm)this.armPrincipale).getAmmo().getNb_bullets());
                return true;
            }else{
                Ammo bal =((FireArm)this.armPrincipale).getAmmo();     
                if(((FireArm)this.armPrincipale).load(Ammo)){
                    getContainer().removeItem(Ammo);
                    getContainer().addItem(bal);
                    this.nbMunition.set(((FireArm)this.armPrincipale).getAmmo().getNb_bullets());
                    return true;
                }else{
                    System.out.print("you put back your old charger\n");
                    return false;
                }
            }   
        }
        
        @Override
        public void onLook(){
            super.onLook();
            seeMyMoney();
            MyWeapon();
        }
        
        @Override
        public void healing(Heal nameItem){
            if(this.hurt){
                this.hurt=false;
                System.out.print("you are no longer hurt");
            }else{
                setLife(nameItem.getHeal());
                this.lifeProperty.set(this.getLife());
                System.out.print("you are healing\n");
            }
            
        }
        
        @Override
        public void printInventory(){
            System.out.printf("\n---START INVENTORY---\n");
            super.printInventory();
            System.out.printf("---END INVENTORY---\n\n");
        }
        
        //seeMyMoney() affiche votre argent sur la console
        public void seeMyMoney(){
            System.out.printf("Your money : %d \n",getMoney());
            
        }
        
        //setWeapon(Weapon nameItem) permet de changer d'arme principale
        public void setWeapon(Weapon nameItem){
            if(this.armPrincipale==null){
               this.armPrincipale=nameItem; 
            }else{
                getContainer().addItem(this.armPrincipale);
                this.armPrincipale=nameItem;
            }
            if(this.armPrincipale instanceof FireArm){
                this.nbMunition.set(((FireArm)this.armPrincipale).getAmmo().getNb_bullets());
            }else{
                this.nbMunition.set(0);
            }
            this.nbControl.set(this.armPrincipale.getControl());
            this.nbAccuracy.set(this.armPrincipale.getAccuracy());
            this.minDps.set(this.armPrincipale.getDamages());
            this.maxDps.set(this.armPrincipale.getDamages()+(this.armPrincipale.getDamages()*this.armPrincipale.getCritics()/100));
            
        }
        
        public Weapon getWeapon(){
            return this.armPrincipale;
        }
        
        //MyWeapon() affiche les stats de votre arme principale
        public void MyWeapon(){
            System.out.printf("Your Weapon : ");
            if(this.armPrincipale==null){
                System.out.printf("main, damages : 5, damages critics : 0\n");
            }else{
                this.armPrincipale.printStats();
            }
        }
        
        //see(Npc people) affiche sur la console le text description du Npc
	public void see (Npc people){
            System.out.printf("\n\n---START DESCRIPTION---\n");           
            if(people.getLife()>0){
                people.onLook();
                System.out.print("\nAggressive : ");
                System.out.print(people.getAggressive());
            }else{
                System.out.print("\ncette persone est mort");
            }
            System.out.printf("\n---END DESCRIPTION---\n\n");
	}
        
        //speak(Npc people) affiche sur la console le text speak du Npc
        public void speak(Npc people){
            System.out.printf("\n\n---START SPEAK ---\n");
            if(!people.getAggressive() && people.getLife()>0){
               System.out.printf("%s\n", people.getSpeak()); 
            }else if(people.getAggressive()){
                System.out.print("he is angry he will not speak with you\n");
            }else{
                System.out.print("he is dead\n");
            }
            System.out.printf("---END SPEAK ---\n\n");
        }
        
        //speak(Trader trade) affiche sur la console le text speak et l'inventaire du marchand
        public void speak(Trader trade){
            System.out.printf("\n\n---START SPEAK TRADER---\n");
            if(!trade.getAggressive() && trade.getLife()>0){               
                trade.printStock();
            }else if(trade.getAggressive()){
                System.out.print("he is angry he will not speak with you\n");
            }else{
                System.out.print("he is dead\n");
            }
            System.out.printf("---END SPEAK TRADER---\n\n");
        }
        
        //attack(Npc character) attaque un Npc avec l'arme principale
        public void attack(Npc character){
            if(character.getLife()>0 && this.armPrincipale!=null){
                if(this.armPrincipale instanceof FireArm){
                    if(((FireArm)this.armPrincipale).isOutofAmmo()){
                        System.out.printf("you have not ammo\n");
                    }else{
                        System.out.printf("you hit %s with %s for ", character.getName(),this.armPrincipale.getName());
                        ((FireArm)armPrincipale).attack(character);
                        this.nbMunition.set(((FireArm)this.armPrincipale).getAmmo().getNb_bullets());
                    }
                }else{
                    System.out.printf("you hit %s with %s for ", character.getName(),this.armPrincipale.getName());
                    armPrincipale.attack(character);
                }
            }else if(this.armPrincipale==null){
                System.out.printf("you have no weapon you use your hand\n",character.getName());
                character.hit(5);
            }
            if(character.getLife()<=0){
                    System.out.printf("%s is death\n",character.getName());
            }
        }

        //buyItems(String nameItem, Trader trade) permet d'acheter un item si on a l'argent suffissant 
        //et si le marchand a l'item dans son inventaire
	public boolean buyItems(int id, Trader trade){
		Item achat = trade.getContainer().getItem(id);
                if(trade.getLife()<=0 ){
                    System.out.printf("%s is death you can't trade with her\n", trade.getName());
                    return false;
                }
                else if (trade.getAggressive()){
                    System.out.printf("%s is angry, you can't trade with her\n", trade.getName());
                    return false;
                }
                else if(achat==null){
			System.out.printf("this trader not have this Item\n");
                        return false;
		}else if (getMoney()<achat.getPrice()){
			System.out.printf("this item is too expensive\n");
                        return false;
		}else{
                    trade.buyItem(getContainer(),achat);
                    setDownMoney(achat.getPrice());
                    System.out.printf("you buy %s\n",achat.getName());
                    this.moneyProperty.set(this.getMoney());
                    return true;
		} 
	}
	
        //sellItem(String nameItem, Trader trade) permet de vendre un item si le marchand a l'argent suffissant 
        //et si vous avez l'item dans votre inventaire
	public boolean sellItem(int id, Trader trade){
		Item achat = getContainer().getItem(id);
                if(trade.getLife()<=0 ){
                    System.out.printf("%s is death you can't trade with her\n", trade.getName());
                    return false;
                }else if (trade.getAggressive()){
                    System.out.printf("%s is angry, you can't trade with her\n", trade.getName());
                    return false;
                }
                else if(achat==null){
                    System.out.printf("you have not this Item\n");
                    return false;
                }else if (trade.getMoney()<achat.getPrice()){
                    System.out.printf("this item is too expensive for trader\n");
                    return false;
                }else{
                    System.out.print("this deal is ok\n");
                    if(achat instanceof Weapon && (Weapon)achat == this.armPrincipale){
                        this.armPrincipale=null;
                    }
                    setUpMoney(achat.getPrice());
                    trade.setDownMoney(achat.getPrice());
                    trade.getContainer().addItem(achat);
                    getContainer().removeItem(achat);
                    this.moneyProperty.set(this.getMoney());
                    return true;
		} 
	}
        
        //seeLoot (Npc people) permet de voir l'inventaire d'un Npc mort
        public void seeLoot (Npc people){
            if(people.getLife()<=0){
                System.out.printf("\n\n---START INVENTORY---\n");
                System.out.printf("\n%s have %d money \n\n",people.getName(),people.getMoney());
                people.printInventory();
                System.out.printf("---END INVENTORY---\n\n");
            }else{
                System.out.printf("%s is not dead \n",people.getName());
            }
        }
        
        //takeAll(Container nameContain) permet de prendre tous les objets d'un Container
        //prend tous ou quelques un si vous avez pas assez de place dans votre inventaire
        public void takeAll(Container nameContain){
            int taille = nameContain.getContent().size();
                for(int i=0;i<taille;i++){
                    Item take = nameContain.getContent().get(0);
                    if(getContainer().addItem(take)){
                       nameContain.removeItem(take);
                    }
                }
        }
        
        //takeAll(Npc people) permet de prendre tous les objets d'un Npc mort
        //prend tous ou quelques un si vous avez pas assez de place dans votre inventaire
        public void takeAll(Npc people){
            if(people.getLife()<=0){
                this.setUpMoney(people.recupMoney());
                int taille = people.getContainer().getContent().size();
                for(int i=0;i<taille;i++){
                    Item take = people.getContainer().getContent().get(0);
                    if(getContainer().addItem(take)){
                       people.getContainer().removeItem(take);
                    }
                }
            }
        }
        
        //take(String nameItem,Container nameContain) permet de prendre un objets d'un Container
        //le prend si vous avez de la place et si il existe
        public void take(int id,Container nameContain){
            Item take = nameContain.getItem(id);
            if(take==null){
                System.out.print("this Item does not exist\n");
            }else if(getContainer().addItem(take)){
                System.out.printf("you take %s\n",take.getName());
                nameContain.removeItem(take);
            }
        }
        
        //take(String nameItem,Container nameContain) permet de prendre un objets ou l'argent d'un Npc mort
        //le prend si vous avez de la place et si il existe
        public void take(int id,Npc people){
            /*if(nameItem.equals("money")){
                if(people.getLife()<=0){
                    System.out.print("you get his money back\n");
                    this.setUpMoney(people.recupMoney());
                    return;
                }else{
                    System.out.print("you try to steal his money this person is now angry\n");
                    people.setAggre();
                    return;
                }
            }*/
            Item take = people.getContainer().getItem(id);
            if(people.getLife()>0){
                    System.out.printf("%s is a live you can't take this item\n", people.getName());
            }
            else if(take!=null && getContainer().addItem(take)){
                System.out.printf("you take %s\n",take.getName());
                people.getContainer().removeItem(take);
            }else{
                System.out.printf("%s not have this Item\n",people.getName());
            }
        }
        
        //dropItem(String nameItem,Npc people) met un item dans l'inventaire d'un Npc mort si il a de la place
        public void dropItem(int id,Npc people){
            Item achat = getContainer().getItem(id);
            if(this.armPrincipale!=null && this.armPrincipale.getId()==id && people.getContainer().addItem(this.armPrincipale)){
                this.armPrincipale=null;
                System.out.print("you dropped the item\n");
            }
            else if(people.getLife()>0){
                    System.out.printf("%s is a live you can't drop\n", people.getName());
            }
            else if(achat==null){
                System.out.printf("You have not this Item\n");
            }
            else if(people.getContainer().addItem(getContainer().getItem(id))){
                getContainer().removeItem(getContainer().getItem(id));
            }else{
                System.out.printf("This container is full\n");
            }
        }
        
        //dropItem(String nameItem,Npc people) met un item dans Container si il a de la place
        public void dropItem(int id,Container nameContain){
            Item achat = getContainer().getItem(id);
            if( this.armPrincipale!=null && this.armPrincipale.getId()==id && nameContain.addItem(this.armPrincipale)){
                this.armPrincipale=null;
                System.out.print("you dropped the item\n");
            }
            else if(achat==null){
                System.out.printf("You have not this Item\n");
            }
            else if(nameContain.addItem(getContainer().getItem(id))){
                getContainer().removeItem(getContainer().getItem(id));
            }else{
                System.out.printf("This container is full\n");
            }
        }
}
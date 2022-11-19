package LostSquad.Model.Container;

import LostSquad.Model.Characters.Entity;
import LostSquad.Model.Characters.Player;

public class Weapon extends Item{
    private int damages; // dégâts de l'arme
    private int critics; // % de dégâts suplémentaire possibles
    private int accuracy; // % chances de critiques
    private int control; // % chances de toucher

    public Weapon(String name, int price, String description, int damages, int critics, int accuracy, int control) {
        super(name, price, description);
        this.damages=damages;
        this.critics=critics;
        this.accuracy=accuracy;
        this.control=control;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        printStats();
    }
    
    public int getDamages(){
        return this.damages;
    }
    
    public int getCritics(){
        return this.critics;
    }
    
    public int getAccuracy(){
        return this.accuracy;
    }
    
    public int getControl(){
        return this.control;
    }
    
    public void setDamages(int damages){
        this.damages=damages;
    }
    
    public void setCritics(int critics){
        this.critics=critics;
    }
    
    public void setAccuracy(int accuracy){
        this.accuracy=accuracy;
    }
    
    public void setControl(int control){
        this.control=control;
    }
    
    public boolean attack(Entity entity){
        int controlchance=(int) (Math.random()*100);
        if(controlchance<this.control){
            int accuracychance=(int) (Math.random()*100);
            int dmg=this.damages;
            if(accuracychance<this.accuracy){
                dmg+=dmg*this.critics/100;
            }
            entity.hit(dmg);
            return true;
        }
        else if(entity instanceof Player){
            System.out.println("you missed him");
            return false;
        }else{
            System.out.println(entity.getName()+" missed");
            return false;
        }
    }
    
    public void printStats(){
        System.out.println(this.getName()+":");
        System.out.println(" -Damage: "+String.valueOf(this.damages));
        System.out.println(" -Critiques(%): "+String.valueOf(this.critics));
        System.out.println(" -Precision(%): "+String.valueOf(this.accuracy));
        System.out.println(" -Control(%): "+String.valueOf(this.control));
    }

    @Override
    public void use(Item item) {
        System.out.println("This item couldn't be use on a other item.");
    }
}

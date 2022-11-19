package LostSquad.Model.Container;

import LostSquad.Model.Characters.Entity;

public class FireArm extends Weapon{
    private Ammo ammo;
    private String typeAmmo;
    
    public FireArm(String name, int price, String description, int damages, int critics, int accuracy, int control,String typeAmmo,Ammo ammo) {
        super(name, price, description, damages, critics, accuracy, control);
        this.ammo=ammo;
        this.typeAmmo=typeAmmo;
    }
    
    public FireArm(String name, int price, String description, int damages, int critics, int accuracy, int control,String typeAmmo) {
        super(name, price, description, damages, critics, accuracy, control);
        this.ammo=null;
        this.typeAmmo=typeAmmo;
    }
    
    public Ammo getAmmo(){
        return this.ammo;
    }
    
    public boolean load(Ammo ammo){
        if(this.typeAmmo.equals(ammo.getName())){
            this.ammo=ammo;
            System.out.print("you reload\n");
            return true;
        }else{
            System.out.print("bad type Ammo\n");
            return false;
        }
    }
    
    public boolean isOutofAmmo(){        
        return this.ammo==null || this.ammo.getNb_bullets()<=0;
    }
    
    @Override
    public boolean attack(Entity entity){
        if(!this.isOutofAmmo()){
            boolean res=super.attack(entity);
            this.ammo.use();
            return res;
        }
        return false;
    }
    
    @Override
    public void printStats(){
        super.printStats();
        System.out.println(" -Type Ammo: "+String.valueOf(this.typeAmmo));
        if(this.ammo!=null){
            System.out.println(" -Ammo:\n  -type bullet : "+String.valueOf(this.typeAmmo)+"\n  -nb bullets : "+String.valueOf(this.ammo.getNb_bullets())+"\n  -price : "+String.valueOf(this.ammo.getPrice()));
        }else{
            System.out.println(" -No Ammo");
        }
    }
}

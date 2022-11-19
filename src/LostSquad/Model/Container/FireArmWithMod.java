package LostSquad.Model.Container;

public class FireArmWithMod extends FireArm {
    private Sight sight=null;
    private Cross cross=null;
    private Barrel barrel=null;
    private Grip grip=null;
    
    public FireArmWithMod(String name, int price, String description, int damages, int critics, int accuracy, int control,String typeAmmo,Ammo ammo) {
        super(name, price, description, damages, critics, accuracy, control, typeAmmo ,ammo);
    }
    
    public Item getSight() {
        return this.sight;
    }
    
    public void removeSight(){
        if(this.sight!=null){
            int newAccuracy=this.getAccuracy()-this.sight.getAccuracy();
            this.setAccuracy(newAccuracy);
            this.sight=null;
        }
    }
    
    public Item getCross() {
        return this.cross;
    }
    
    public void removeCross(){
        if(this.cross!=null){
            int newControl=this.getControl()-this.cross.getControl();
            this.setControl(newControl);
            this.cross=null;
        }
    }
    
    public Item getBarrel() {
        return this.barrel;
    }
    
    public void removeBarrel(){
        if(this.barrel!=null){
            int newDamages=this.getControl()-this.barrel.getDamages();
            int newCritics=this.getCritics()-this.barrel.getCritics();
            this.setDamages(newDamages);
            this.setCritics(newCritics);
            this.barrel=null;
        }
    }
    
    public Item getGrip() {
        return this.grip;
    }
    
    public void removeGrip(){
        if(this.grip!=null){
            int newAccuracy=this.getAccuracy()-this.grip.getAccuracy();
            int newControl=this.getControl()-this.getControl();
            this.setAccuracy(newAccuracy);
            this.setControl(newControl);
            this.grip=null;
        }
    }
    

    @Override
    public void use(Item item){
        if(item instanceof Sight){
            System.out.print("you add Sight to Weapon\n");
            int newAccuracy;
            Sight sight=(Sight)item;
            
            if(this.sight!=null){
                this.removeSight();
            }
            
            this.sight=sight;
            newAccuracy=this.getAccuracy()+sight.getAccuracy();
            this.setAccuracy(newAccuracy);
        }
        if(item instanceof Cross){
            System.out.print("you add Cross to Weapon\n");
            int newControl;
            Cross cross=(Cross)item;
            
            if(this.cross!=null){
                this.removeCross();
            }
            
            this.cross=cross;
            newControl=this.getControl()+cross.getControl();
            this.setControl(newControl);
        }
        if(item instanceof Barrel){
            System.out.print("you add Barrel to Weapon\n");
            int newDamages;
            int newCritics;
            Barrel barrel=(Barrel)item;
            
            if(this.barrel!=null){
                this.removeBarrel();
            }
            
            this.barrel=barrel;
            newDamages=this.getDamages()+barrel.getDamages();
            newCritics=this.getCritics()+barrel.getCritics();
            this.setDamages(newDamages);
            this.setCritics(newCritics);
        }
        if(item instanceof Grip){
            System.out.print("you add Grip to Weapon\n");
            int newAccuracy;
            int newControl;
            Grip grip=(Grip)item;
            
            if(this.grip!=null){
                this.removeGrip();           
            }
            
            this.grip=grip;
            newAccuracy=this.getAccuracy()+grip.getAccuracy();
            newControl=this.getControl()+grip.getControl();
            this.setAccuracy(newAccuracy);
            this.setControl(newControl);
        }
    }
    
    @Override
    public void printStats(){
        super.printStats();
        System.out.println("Equipped mods:");
        if(this.sight!=null){
            System.out.println("   -Sight: "+this.sight.getName());
        }
        if(this.cross!=null){
            System.out.println("   -Cross: "+this.cross.getName());
        }
        if(this.barrel!=null){
            System.out.println("   -barrel: "+this.barrel.getName());
        }
        if(this.grip!=null){
            System.out.println("   -Grip: "+this.grip.getName());
        }
    }
}

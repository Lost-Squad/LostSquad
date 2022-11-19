package LostSquad.Model.Container;

public class Barrel extends Item implements Mod {
    private final int damages; // bonus de dégâts
    private final int critics; // bonus de critics

    public Barrel(String name, int price, String description, int damages, int critics) {
        super(name, price, description);
        this.damages=damages;
        this.critics=critics;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("Damages bonus : %d\n\nCritics bonus : %d\n\n", this.damages,this.critics);
    }
    
    public int getDamages(){
        return this.damages;
    }
    
    public int getCritics(){
        return this.critics;
    }

    @Override
    public void use(Item item) {
        if (item instanceof FireArmWithMod){
            FireArmWithMod arm=(FireArmWithMod)item;
            arm.use(this);
        }
    }
    
}

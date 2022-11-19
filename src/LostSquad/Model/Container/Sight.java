package LostSquad.Model.Container;

public class Sight extends Item implements Mod {
    private final int accuracy; // bonus de précision donné
    
    public Sight(String name, int price, String description, int accuracy) {
        super(name,price,description);
        this.accuracy=accuracy;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("Accuracy bonus : %d\n\n", this.accuracy);
    }
    
    public int getAccuracy(){
        return this.accuracy;
    }
    
    @Override
    public void use(Item item) {
        if (item instanceof FireArmWithMod){
            FireArmWithMod arm=(FireArmWithMod)item;
            arm.use(this);
        }
    }
    
}

package LostSquad.Model.Container;

public class Grip extends Item implements Mod {
    private int accuracy;
    private int control;

    public Grip(String name, int price, String description, int accuracy, int control) {
        super(name, price, description);
        this.accuracy=accuracy;
        this.control=control;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("Accuracy bonus : %d\n\nControl bonus : %d\n\n", this.accuracy,this.control);
    }
    
    public int getAccuracy(){
        return this.accuracy;
    }
    
    public int getControl(){
        return this.control;
    }
    
    @Override
    public void use(Item item) {
        if (item instanceof FireArmWithMod){
            FireArmWithMod arm=(FireArmWithMod)item;
            arm.use(this);
        }
    }
}

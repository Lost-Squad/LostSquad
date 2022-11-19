package LostSquad.Model.Container;

public class Cross extends Item implements Mod {
    private final int control; // bonus de control de l'arme

    public Cross(String name, int price, String description,int control) {
        super(name, price, description);
        this.control=control;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("Control bonus : %d\n\n", this.control);
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

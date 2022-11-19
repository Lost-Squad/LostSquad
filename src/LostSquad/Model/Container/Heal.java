package LostSquad.Model.Container;

import LostSquad.Model.Characters.Entity;

public class Heal extends Item {
    
    private final int healLevel;
        
    public Heal(String name, int price, String description,int healLevel){
        super(name,price,description);
        this.healLevel=healLevel;
    }
    
    public int getHeal(){
        return this.healLevel;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("level heal : %d\n\n", getHeal());
    }
    
    public void use(Entity entity){
        entity.healing(this);
    }
    
    @Override
    public void use(Item item) {
        System.out.println("You can't heal an item");
    }
}

package LostSquad.Model.Container;

public class Bauble extends Item {
    
    public Bauble(String name,int price, String description){
        super(name,price,description);
    }
    
    @Override
    public void use(Item item) {
        System.out.println("This item couldn't be use on a other item.");
    }
}

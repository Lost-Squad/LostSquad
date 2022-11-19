package LostSquad.Model.Container;

public class Ammo extends Item{
    
    private int nb_bullets;
    private final int priceOneBullet;

    public Ammo(String name, int priceOneBullet, String description, int nb_bullets) {
        super(name, priceOneBullet*nb_bullets, description);
        this.nb_bullets=nb_bullets;
        this.priceOneBullet=priceOneBullet;
    }

    public void use() {
        this.nb_bullets -=1;
        setPrice(getPrice()-priceOneBullet);
    }

    @Override
    public void use(Item item) {
        if(item instanceof FireArm){
            FireArm fireArm=(FireArm)item;
            fireArm.load(this);
        }
    }
    
    public int getNb_bullets(){
        return this.nb_bullets;
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("nb nullet : %d\n\nprice : %d\n\n", this.nb_bullets,getPrice());
    }
    
}

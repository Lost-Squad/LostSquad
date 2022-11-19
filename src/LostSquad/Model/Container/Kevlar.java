package LostSquad.Model.Container;

public class Kevlar extends Item {
    
    private int protection;

    public Kevlar(String name, int price, String description, int protection) {
        super(name, price, description);
        this.protection=protection;
    }
    
    @Override
    public void use(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void onLook(){
        super.onLook();
        System.out.printf("level Kevlar : %d\n\n", getProtection());
    }
    
    
    public int getProtection(){
        return this.protection;
    }
    
    // renvoie les dégâts restants à subir
    public int use(int damages){
        int res = 0;
        if (damages>this.protection){
            res=damages-this.protection;
            this.protection=0;
        }
        else{
            this.protection-=damages;
        }
        return res;
    }
}

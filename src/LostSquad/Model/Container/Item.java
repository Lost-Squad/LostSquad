package LostSquad.Model.Container;

import java.io.Serializable;
import LostSquad.Model.Interfaces.Descriptible;

/**
 *
 * @author vava3
 */
public abstract class Item implements Descriptible, Serializable{

        private static int idStatic;
        private final int id;
	private final String name;
        private int price;
        private final String description;
        
	public Item(String name, int price, String description) {
		// TODO - implement Item.Item
                this.id=Item.idStatic+1;
                Item.idStatic=this.id;
                this.name = name;
                this.price= price;
                this.description= description;
	}
        
        @Override
        public String getName(){
            return this.name;
        }
        
        public int getId(){
            return this.id;
        }
        
        public int getPrice(){
            return this.price;
        }
        
        protected void setPrice(int price){
            this.price=price;
        }
        
        public String getDescription(){
            return this.description;
        }
        
        @Override
        public void onLook(){
            System.out.print("ID: "+this.id+"\n"+"Objet: "+this.name+"\n\n"+"Price: "+String.valueOf(this.price)+"\n\n"+"Description: "+this.description+"\n\n");
        }
        
        public abstract void use(Item item);
        
        @Override
        public String getDesc(){
            return this.description;
        }

}
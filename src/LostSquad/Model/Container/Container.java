package LostSquad.Model.Container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import LostSquad.Model.Interfaces.Descriptible;

/**
 *
 * @author vava3
 */
public class Container implements Descriptible,Serializable {
        
        private final String name;
        private final String description;
	private List<Item> content;
	private int capacity;
        
        public Container(String name, String description, int capacity, List<Item> content){
            this.name=name;
            this.description=description;
            this.capacity=capacity;
            this.content=content;
        }
        
        public boolean isEmpty(){
            return content.isEmpty();
        }
        
        public Container(String name, String description, int capacity){
            this(name, description, capacity,new ArrayList<Item>());
        }
        
        @Override
        public String getName(){
            return this.name;
        }
        
        public String getDescription() {
            return this.description;
        }
        
        public int getCapacity(){
            return this.capacity;
        }
        
        public List<Item> getContent(){
            return this.content;
        }
        
        public boolean addItem(Item item){
            if(this.content.size()+1<=this.capacity){
                this.content.add(item);
                return true;
            }
            else{
                System.out.println(this.name+" full");
                return false;
            }
        }
        
        public Item getItem(int id){
            int count=this.content.size();
            int i=0;
            Item res=null;
            
            if(this.capacity<count){
                count=this.capacity;
            }
            if (count!=0){
                do{
                    res=this.content.get(i);
                    i++;
                }while(i<count && id!=res.getId());
                
                if(i>=count && id!=res.getId()){
                    res=null;
                }
            }
            
            return res;
        }
        
        public void removeItem(Item item){
            this.content.remove(item);
        }
        
        public boolean contains(int id){
            int count=this.content.size();
            int i=0;
            Item res;
            
            if(this.capacity<count){
                count=this.capacity;
            }
            
            do{
                res=this.content.get(i);
                i++;
            }while(i<count && id!=res.getId());
            
            return i<count;
        }
        
        @Override
	public void onLook() {
            // TODO - implement Container.printContainer
            int count=this.content.size();
            
            if(this.capacity<count){
                count=this.capacity;
            }
            
            for(int i=0; i<count; i++){
                System.out.printf("---Object %d---\n", i+1);
                this.content.get(i).onLook();
            }
	}

    @Override
    public String getDesc() {
        return this.description;
    }
        
}
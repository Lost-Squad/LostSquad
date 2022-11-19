package LostSquad.Model.Container;

import java.util.List;
import LostSquad.Model.Interfaces.Lockeable;

public class ContainerLockKey extends Container implements Lockeable {
        private final int KEY;
        private boolean isLock;

        public ContainerLockKey(String name, String description, int capacity, List<Item> content, int code) {
            super(name, description, capacity, content);
            this.KEY=code;
            this.isLock=true;
        }

        public ContainerLockKey(String name, String description, int capacity, int code) {
            super(name, description, capacity);
            this.KEY=code;
            this.isLock=true;
        }

        public boolean isLock(){
            return this.isLock;
        }

        @Override
        public void Lock() {
            if(this.isLock){
                System.out.println(super.getName()+" is already locked");
            }
            else{
                System.out.println(super.getName()+" is unlocked: use a key to lock it");
            }
        }

        public void Lock(int code) {
            if (code==this.KEY){
                this.isLock=true;
                System.out.println(super.getName()+" is locked.");
            }
            else{
                System.out.println("This is the wrong key!");
            }
        }

        @Override
        public void unLock() {
            if(this.isLock){
                System.out.println(super.getName()+" is locked: use a key to unlock it");
            }
            else{
                 System.out.println(super.getName()+" is already unlocked");
            }
        }

        public void unLock(int code) {
            if (code==this.KEY){
                this.isLock=false;
                System.out.println(super.getName()+" is locked.");
            }
            else{
                System.out.println("This is the wrong key!");
            }
        }

        @Override
        public List<Item> getContent(){
            if(!this.isLock){
                return super.getContent();
            }
            else{
                System.out.println(super.getName()+" is locked.");
                return null;
            }
        }
        
        @Override
        public boolean addItem(Item item){
            if(!this.isLock){
                if(super.getContent().size()+1<=super.getCapacity()){
                    super.getContent().add(item);
                    return true;
                }
                else{
                    System.out.println(super.getName()+" full");
                    return false;
                }
            }
            else{
                System.out.println(super.getName()+" is locked.");
                return false;
            }
        }
        
        @Override
        public Item getItem(int id){
            if(!this.isLock){
                int count=super.getContent().size();
                int i=0;
                Item res=null;

                if(super.getCapacity()<count){
                    count=super.getCapacity();
                }
                if (count!=0){
                    do{
                        res=super.getContent().get(i);
                        i++;
                    }while(i<count && id!=res.getId());

                    if(i>=count && id!=res.getId()){
                        res=null;
                    }
                }

                return res;
            }
            else{
                System.out.println(super.getName()+" is locked.");
                return null;
            }
        }
        
        @Override
        public void removeItem(Item item){
            if(!this.isLock){
                super.getContent().remove(item);
            }
            else{
                System.out.println(super.getName()+" is locked.");
            }
        }
        
        @Override
        public boolean contains(int id){
            if(!this.isLock){
                int count=super.getContent().size();
                int i=0;
                Item res;

                if(super.getCapacity()<count){
                    count=super.getCapacity();
                }

                do{
                    res=super.getContent().get(i);
                    i++;
                }while(i<count && id!=res.getId());

                return i<count;
            }
            else{
                System.out.println(super.getName()+" is locked.");
                return false;
            }
        }
        
        @Override
	public void onLook() {
            if(this.isLock){
                int count=super.getContent().size();

                if(super.getCapacity()<count){
                    count=super.getCapacity();
                }

                for(int i=0; i<count; i++){
                    super.getContent().get(i).onLook();
                }
            }
            else{
                System.out.println(super.getName()+" is locked.");
            }
	}
}

package LostSquad.Model.Room;
import LostSquad.Model.Container.Item;
import LostSquad.Model.Interfaces.Lockeable;

public class DoorLock extends Door implements Lockeable{

	protected boolean isLock;
        
        @Override
        public boolean tryTravel(Item keyCode){
            return !isLock;
        }
        
        public DoorLock(String place, String name){
            this(true,place,  name);
        }
        
        public DoorLock(boolean p_isLocked, String place, String name){
            super(place, name);
            this.isLock = p_isLocked;
        }
        
        @Override
        public boolean onTravel(){
            if(isLock){
                System.out.println("The door is locked");
            }
            return !isLock;
        }
        
        @Override
	public void Lock() {
		this.isLock = true;
	}

        @Override
	public void unLock() {
		this.isLock = false;
	}
        
        public boolean isLock(){
            return this.isLock;
        }

}
package LostSquad.Model.Container;

import LostSquad.Model.Room.Door;
import LostSquad.Model.Room.DoorLockKey;
public class Key extends Item {

	private final int code;

	public Key(String name, int price, String description, int code) {
		super(name, price, description);
                this.code=code;
	}
        
	public int getCode() {
		return this.code;
	}
        
        public void use(Door door) {
            if(door instanceof DoorLockKey ){
                DoorLockKey doorLockKey=(DoorLockKey)door;
                if (doorLockKey.isLock()){
                    doorLockKey.unLock(this.code);
                }
                else{
                    doorLockKey.Lock(this.code);
                }
            }
        }
        
        public void use(Container container) {
            if(container instanceof ContainerLockKey ){
                ContainerLockKey containerLockKey=(ContainerLockKey)container;
                if (containerLockKey.isLock()){
                    containerLockKey.unLock(this.code);
                }
                else{
                    containerLockKey.Lock(this.code);
                }
            }
        }

    @Override
    public void use(Item item) {
        System.out.println("You couldn't unlock an item");
    }
}
package LostSquad.Model.Room;

public class DoorLockKey extends DoorLock{

	private final int KEY;
        
        public DoorLockKey(int code,String place, String name){
            super(place,  name);
            this.KEY = code;
        }
        
	public void Lock(int key) {
		if(key == KEY){
                    super.Lock();
                    System.out.printf("this door is now lock \n");
                }else{
                    System.out.printf("That wasn't the good key! \n");
                }
	}

	public void unLock(int key) {
		if(key == KEY){
                    System.out.println("The key worked !");
                    super.unLock();
                }else{
                    System.out.printf("That wasn't the good key");
                }
	}
        
        
        public boolean open(int p_code) {
            this.unLock(p_code);
            return !this.isLock;
        }
        

}
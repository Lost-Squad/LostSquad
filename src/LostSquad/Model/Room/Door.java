package LostSquad.Model.Room;
import LostSquad.Model.Interfaces.Descriptible;
import LostSquad.Model.Container.Item;
import java.io.Serializable;
public class Door implements Serializable, Descriptible{

	private String place;
	protected boolean isOpen;
        private String name;
	//private String desc;
        
        private String travelText;
        private String approchingText;
        private String onLookText;
        
        public Door(){
            this("a empty room","a door");
        }
        
        public void setLookText(String text){
            this.onLookText = text;
        }
        @Override
        public void onLook(){
            if(this.onLookText != null){
                System.out.println(this.onLookText);
            }
        }
        
        public boolean tryTravel(Item keyCode){
            return true;
        }
        
        public Door(String placeName, String name){
            this.place = placeName;
            this.isOpen = false;
            this.name = name;
            //this.desc = desc;
        }
        
        public void setTravelText(String text){
            this.travelText = text;
        }
        public boolean onTravel(){
            if(this.travelText != null){
                System.out.println(this.travelText);
            }
            return true;
        }
        
        public void setApprochingText(String text){
            this.approchingText = text;
        }
        public boolean onApproching(){
            // Return True if it need a confirmation
            if(this.approchingText != null){
                System.out.println(this.approchingText);
                return true;
            }
            return false;
        }
        
        public String getPlace(){
            return this.place;
        }
        
        public boolean canTravel(){
            return true;
        }
        
        @Override
        public String getName(){
            return this.name;
        }
        
	public void close() {
		this.isOpen = false;
	}

    @Override
    public String getDesc() {
        return this.onLookText;
    }

}
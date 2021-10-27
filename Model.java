//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 19th, 2021
// Model Class; controls the list of sprites, collision detection, and calling update
// for each of the individual sprites
//----------------------------------------------------------------------------------|
import java.util.ArrayList;

class Model{

	ArrayList<Sprite> sprites;
	ArrayList<Integer> deleteList;
	Mario mario;
	
	//-----------------------------------------------------------------------------------------------------|
	// Model constructor
	//-----------------------------------------------------------------------------------------------------|
	Model(){
		
		sprites = new ArrayList<Sprite>();
		deleteList = new ArrayList<Integer>();
		mario = new Mario();
		sprites.add(mario);
		
		try{
			Json j = Json.load("map.json");
			unmarshal(j);
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update method
	//-----------------------------------------------------------------------------------------------------|
	void update(){
		
		// Update all the sprites
		for(int i = 0; i < sprites.size(); i++){
			
			// Grab the next sprite
			Sprite s = sprites.get(i);
			
			// If its not a mario and its not an orb
			if(!s.isMario() && !s.isOrb()){
				// Check to see if mario can collide with it
				if(mario.isCollisionPossible(s)){
					// If so fix the collision
					mario.fixCollision(s);
				}
			}
		
			// If any updates are returning false we want to remove the sprite
			if(s.update() == false){
				// Add the index to a delete list
				sprites.remove(s);
			}
		
		}
			
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Unmarshal json objects
	// |-------------------------------------------------------------------------------------------------------------------|
	public void unmarshal(Json ob){
		
		sprites = new ArrayList<Sprite>();
		mario = new Mario();
		sprites.add(mario);
		
		// Get json list of bricks
		Json tmpList = ob.get("bricks");
		// Get json list of crates
		Json tmpList2 = ob.get("crates");
		
		// get all the bricks from the json list
		for(int i = 0; i < tmpList.size(); i++){
			sprites.add(new Brick(tmpList.get(i), this));
		}
		
		// get all the crates from the json list
		for(int i = 0; i < tmpList2.size(); i++){
			sprites.add(new Crate(tmpList2.get(i), this));
		}
		
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Marshal objects into json
	// |-------------------------------------------------------------------------------------------------------------------|
	Json marshal()
     {	 
		 Json ob = Json.newObject();
         Json tmpList = Json.newList();
		 Json tmpList2 = Json.newList();
         
		 // json list for bricks and crates
		 ob.add("bricks", tmpList);
		 ob.add("crates", tmpList2);
         
		 for(int i = 0; i < sprites.size(); i++){
			 
			 Sprite s = sprites.get(i);
			 // if sprite is crate add to crate list
			 if(s.isCrate()){
				 //System.out.println("Marshal crate");
				 Crate c = (Crate)sprites.get(i);
				 tmpList2.add(c.marshal());
			 }
			 // if sprite is brick add to brick list
			 else if(s.isBrick()){
				 Brick b = (Brick)sprites.get(i);
				 tmpList.add(b.marshal());
			 }
			 
		 }
          return ob;
    }
}
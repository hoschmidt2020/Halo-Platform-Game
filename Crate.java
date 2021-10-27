//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 16th, 2021
// Crate Class; a brick subclass, and a sprite subsubclass, the "coin" brick
//----------------------------------------------------------------------------------|

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Random;

class Crate extends Brick{
	
	// boolean for whether or not to draw the orb
	boolean drawOrb = false;
	boolean newOrb = false;
	Orb orb;
	int numOrbs = 0;
	BufferedImage brokenCrate;
	
	
	//-----------------------------------------------------------------------------------------------------|
	// Default constructor
	//-----------------------------------------------------------------------------------------------------|
	Crate(Model m){
		super(m);
		
		model = m;

		this.x = 0; 
		this.y = 0;
		this.w = 100;
		this.h = 100;
		
		image = null;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Constructor with dimensions
	//-----------------------------------------------------------------------------------------------------|
	Crate(int x1, int y1, int w1, int h1, Model m){
		super(x1,y1,w1,h1,m);
		model = m;
		this.x = x1;
		this.y = y1;
		this.w = w1;
		this.h = h1;
	
		image = null;
		
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Unmarshal json object variables
	// |-------------------------------------------------------------------------------------------------------------------|
	Crate(Json ob, Model m){
		super(ob, m);
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		model = m;
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Crate Marshaler
	// |-------------------------------------------------------------------------------------------------------------------|
	@Override
	Json marshal()
     {	 
	     Json ob = Json.newObject();
         ob.add("x", x);
		 ob.add("y", y);
		 ob.add("w", w);
		 ob.add("h", h); 
		 return ob;
    }
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract method; update any variables if necessary
	//-----------------------------------------------------------------------------------------------------|
	boolean update(){
		
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract method; draw the designated brick to the screen
	//-----------------------------------------------------------------------------------------------------|
	void draw(Graphics g){
		
		// load image if needbe
		if(this.image == null){
			this.image = View.loadImage("Crate.png");
		}
		
		// If there are any new orbs to create
		if(newOrb == true){
			
			// Don't add orbs if there are already five of them
			if(numOrbs <= 5){
				
				//System.out.println("NumOrbs: " + numOrbs);
				numOrbs++;
				
				// Create the orb in the top middle of the crate
				orb = new Orb(this.x + (int)(this.w/2), this.y, model);
				
				// Add the new orb to the list of sprites
				model.sprites.add(orb);
				newOrb = false;

			}
			
		}
		
		// Change image if all orbs have been hit out of the crate
		if(numOrbs <= 5){
			g.drawImage(this.image, this.x - model.mario.x + model.mario.marioScreenLocation, this.y, this.w, this.h, null);
		}else if(numOrbs >= 5){
			if(this.brokenCrate == null){
				this.brokenCrate = View.loadImage("Metal2.png");
			}
			g.drawImage(this.brokenCrate, this.x - model.mario.x + model.mario.marioScreenLocation, this.y, this.w, this.h, null);
		}
				
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Determine whether this sprite object is crate; yes
	//-----------------------------------------------------------------------------------------------------|
	@Override
	boolean isCrate(){
		return true;
	}
		
}
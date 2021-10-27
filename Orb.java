//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 17th, 2021
// Orb Class; extends abstract sprite class, these are the "coins" from our crates
//----------------------------------------------------------------------------------|

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.lang.Math;
import java.util.Random;

class Orb extends Sprite{
	
	BufferedImage orbImage;
	double horz_vel = 0;
	boolean deleteOrb = false;
	Model model;
	Random r;
	
	//-----------------------------------------------------------------------------------------------------|
	// Default Orb Constructor
	//-----------------------------------------------------------------------------------------------------|
	Orb(Model m){
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		this.orbImage = null;
		model = m;
	}
	
	
	//-----------------------------------------------------------------------------------------------------|
	// Orb with positional variables
	//-----------------------------------------------------------------------------------------------------|
	Orb(int x1, int y1, Model m){
		
		this.x = x1;
		this.y = y1;
		this.w = 20;
		this.h = 20;
		
		r = new Random();
		
		// Start with a random horizontal and vertical velocity
		this.vert_vel -= r.nextInt(20) + 10;
		this.horz_vel = r.nextInt(5 + 5) - 5;
	
		model = m;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract method; update any variables if necessary
	//-----------------------------------------------------------------------------------------------------|
	boolean update(){
		
		// Gravity
		this.vert_vel += 1.2;
		this.y += vert_vel;
		
		// Horizontal velocity decreation
		this.x += horz_vel;
		this.horz_vel += Math.signum(horz_vel/r.nextInt(5));
		
		if(y > View.windowHeight){
			return false;
		}
		
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract method; draw the designated brick to the screen
	//-----------------------------------------------------------------------------------------------------|
	void draw(Graphics g){
		
		// Load the image if needbe
		if(this.orbImage == null){
			this.orbImage = View.loadImage("Energy_Ball.png");
		}
		
		g.drawImage(this.orbImage, this.x - model.mario.x + model.mario.marioScreenLocation, this.y, this.w, this.h, null);
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Determine whether this sprite object is an orb; yes
	//-----------------------------------------------------------------------------------------------------|
	@Override
	boolean isOrb(){
		return true;
	}	
}
//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 16th, 2021
// Mario Class; the main character of the game, brick drawing is centered around his
// location, controls for gravity here
//----------------------------------------------------------------------------------|

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Mario extends Sprite{
	
	// Image count variable
	int imageNum;	
	
	// Additional movement positional values 
	int marioScreenLocation;
	double vert_vel;
	int jumpFrames;
	int imageScale = 3;
	
	// Movement variables
	boolean movingRight;
	boolean wasMovingRight;
	boolean movingLeft;
	boolean wasMovingLeft;
	boolean movingUp;
	boolean movingDown;
	boolean onGround;
	boolean onGroundWasTrue;
	
	//-----------------------------------------------------------------------------------------------------|
	// Mario constructor
	//-----------------------------------------------------------------------------------------------------|
	Mario(){
		
		// Position and movement variablesx
		x = 0;
		y = 0;
		w = 0;
		h = 0;
		prevX = 0;
		prevY = 0;
		prevW = 0;
		prevH = 0;
		vert_vel = 0;
		marioScreenLocation = 150;
		
		// Image number
		imageNum = 0;
		jumpFrames = 0;
		
		// Boolean variables
		movingRight = false;
		movingLeft = false;
		movingUp = false;
		movingDown = false;
		onGround = false;
		onGroundWasTrue = false;
		wasMovingRight = false;
		wasMovingLeft = false;
		
		// Image list
		images = new ArrayList<BufferedImage>();
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update method
	//-----------------------------------------------------------------------------------------------------|
	boolean update(){
		
		// Gravity
		vert_vel += 1.2;
		y += vert_vel;
		
		// Ground level at 500
		if(y > View.windowHeight - h - 50){
			vert_vel = 0;
			y = View.windowHeight - h - 50;
			onGround = true;
			jumpFrames = 0;
		}else{
			onGround = false;
		}
		
		// Get the width and height of the character before we check for collisions
		if(!images.isEmpty()){
			this.w = images.get(imageNum).getWidth() * imageScale;
			this.h = images.get(imageNum).getHeight() * imageScale;
		}
	
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract method; draw mario to the screen
	//-----------------------------------------------------------------------------------------------------|
	void draw(Graphics g){
		
		// Load the images if they haven't been yet
		if(images.isEmpty()){
			images.add(View.loadImage("Halo2.png"));
			images.add(View.loadImage("Halo3.png"));
			images.add(View.loadImage("Halo3.png"));
			images.add(View.loadImage("Halo4.png"));
			images.add(View.loadImage("Halo5.png"));
			images.add(View.loadImage("Halo6.png"));
			images.add(View.loadImage("Halo7.png"));
			images.add(View.loadImage("Halo8.png"));
			images.add(View.loadImage("Halo9.png"));
			
			w = images.get(0).getWidth();
			h = images.get(0).getHeight();
		}
		
		// Actively change the width and height as we print the character
		this.w = images.get(imageNum).getWidth() * imageScale;
		this.h = images.get(imageNum).getHeight() * imageScale;
		
		// Determine which way the character should be facing 
		if(movingRight == true || wasMovingRight == true){
			g.drawImage(this.images.get(imageNum), marioScreenLocation, this.y, this.w, this.h, null);
		}
		else if(movingLeft == true || wasMovingLeft == true){
			g.drawImage(this.images.get(imageNum), this.w + marioScreenLocation, this.y, -this.w, this.h, null);
		}
		else{
			g.drawImage(this.images.get(imageNum), marioScreenLocation, this.y, this.w, this.h, null);
		}

				
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Fix the found collision
	//-----------------------------------------------------------------------------------------------------|
	void fixCollision(Sprite s1){
		
		// System.out.println(((Brick)s1).toString());
		// System.out.println(this.toString());
		
		// If character was to the left of the brick previously
		if(this.prevX + this.prevW <= s1.x && this.x + this.w >= s1.x){
			// charcter x = object x - character width
			this.x = s1.x - this.w - 1;
		}
		
		// If character was to the right of the brick previously
		if(this.prevX >= s1.x + s1.w && this.x <= s1.x + s1.w){
			// character x = object x + object width
			this.x = s1.x + s1.w + 1;
		}

		// If it was above the brick previously
		if(this.prevY + this.prevH <= s1.y && this.y + this.h >= s1.y){
			// character y = objects y - character height
			this.y = s1.y - this.h;
			this.vert_vel = -1.2;
			
			// Set onGround to true so that we can jump
			this.onGround = true;
		}
		
		
		// If character was beneath the object previously
		if(this.prevY >= s1.y + s1.h && this.y <= s1.y + s1.h){
			
			// character y = object y + object height
			this.y = s1.y + s1.h;
			this.vert_vel = 0;
			
			// Mario is not moving up anymore
			this.onGround = false;
			this.onGroundWasTrue = false;
			
			// If the object is a crate we want to make orbs
			if(s1.isCrate()){
				((Crate)s1).newOrb = true;
			}
		}
		
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update the image num of mario
	//-----------------------------------------------------------------------------------------------------|
	void updateImage(){
		imageNum++;
		if(imageNum >= 8){
			imageNum = 0;
		}
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Check if we can jump or not
	// |-------------------------------------------------------------------------------------------------------------------|
	void jump(){
		
		// if on ground reset jump count
		if(onGround == true && movingUp == false){
			jumpFrames = 0;
		}
		
		// If we were just on the ground or are on the ground  
		if(onGround == true || onGroundWasTrue == true){
			onGroundWasTrue = true;
			// If space bar is pressed and counter still under 10 continue jumping
			if(jumpFrames <= 10){
				vert_vel -= 3.0;
				jumpFrames++;
			}else{
				onGroundWasTrue = false;
			}
		}	
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update the pervious location of the sprite
	//-----------------------------------------------------------------------------------------------------|
	@Override 
	void updatePrev(){
		this.prevX = this.x;
		this.prevY = this.y;
		this.prevW = this.w;
		this.prevH = this.h;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Determine whether this sprite object is mario; yes
	//-----------------------------------------------------------------------------------------------------|
	@Override
	boolean isMario(){
		return true;
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  toString for Brick
	// |-------------------------------------------------------------------------------------------------------------------|
	@Override
	public String toString(){
		return "Character: ( " + x + ", " + y + ") w: " + w + " h: " + h + "\n Prev: (" + prevX + ", " + prevY + ")"; 
	}
}
//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 16th, 2021
// Sprite Abstract Class; used for all of the interactable pieces in the game
//----------------------------------------------------------------------------------|
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract class Sprite{
	
	int x,y,w,h;
	double vert_vel;
	int prevX, prevY, prevW, prevH;

	ArrayList<BufferedImage> images;
	
	//-----------------------------------------------------------------------------------------------------|
	// Sprite abstract methods;
	//-----------------------------------------------------------------------------------------------------|
	abstract boolean update();
	abstract void draw(Graphics g);
	
	//-----------------------------------------------------------------------------------------------------|
	// isBlanck() methods to determine the type of sprite we are working with
	//-----------------------------------------------------------------------------------------------------|
	boolean isBrick(){
		return false;
	}
	
	boolean isMario(){
		return false;
	}
	
	boolean isCrate(){
		return false;
	}
	
	boolean isOrb(){
		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Check if collision between this sprite and the given sprite is possible
	//-----------------------------------------------------------------------------------------------------|
	public boolean isCollisionPossible(Sprite s){
		//Check if the right side of the character is to the right or left of the left side of the brick
		if(this.x + this.w <= s.x){
			return false;
		}
		
		// Check if the left side of the character is to the right or the left of the left side of the brick
		if(this.x >= s.x + s.w){
		   return false;	
		}
		
		// Check if the bottom of the character is above the top of the brick
		if(this.y + this.h <= s.y){
			return false;
		}
		
		// Check if the top of the character is below the bottom of the brick
		if(this.y >= s.y + s.h){
			return false;
		}
	
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update the pervious location of the sprite
	//-----------------------------------------------------------------------------------------------------|
	void updatePrev(){
		this.prevX = this.x;
		this.prevY = this.y;
	}
}
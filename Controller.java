//----------------------------------------------------------------------------------|
//  Henry Schmidt
//  October 16th, 2021
//  Controller class; has the key listener and mouse listener associated with view, 
//  allows us to capture the values needed to make bricks on the screen and control
//  screen object movements
//----------------------------------------------------------------------------------|

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.System;

class Controller implements MouseListener, KeyListener{
	
	// Class objects
	Model model;
	
	// MouseListener variables
	int mousePressed_x;
	int mousePressed_y;
	int mouseReleased_x;
	int mouseReleased_y;
	int frame = 0;;
	
	// Camera position increment
	static int xspeed = 5;
	
	//Bools for key presses
	static boolean keyRight = false;
	static boolean keyLeft = false;
	static boolean space = false;
	
	// Save and load booleans
	boolean save = false;
	boolean load = false;
	
	// Editing booleans
	boolean editing = false;
	boolean editingIsOn = false;
	boolean brick = false;
	boolean crate = false; 
	boolean sameSize = false;
	int brickSize = 50;
	
	Controller(Model m){
		model = m;
	}
		
	//-----------------------------------------------------------------------------------------------------|
	// Update method
	//-----------------------------------------------------------------------------------------------------|
	void update(){
		
		model.mario.updatePrev();
	    
		//If right key is pressed adjust the camera position
		if(keyRight == true){
			model.mario.x += xspeed;
			
			// Only update every other frame
			if(frame % 2 == 0){
				model.mario.updateImage();
				frame++;
			}else{
				frame++;
			}
		}
		
		//If the left key is pressed adjust the camera position
		if(keyLeft == true){
			model.mario.x -= xspeed;
			
			// Only update every other frame
			if(frame % 2 == 0){
				model.mario.updateImage();
				frame++;
			}else{
				frame++;
			}
		}
		
		//Check if he can jump if space bar is pressed
		if(space == true){
			model.mario.jump();
		}
			
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  For drawing bricks; capture the location where the mouse was pressed
	// |-------------------------------------------------------------------------------------------------------------------|
	public void mousePressed(MouseEvent e){
		mousePressed_x = e.getX();
		mousePressed_y = e.getY();
		
		if(editing == true && sameSize == true){
			if(brick == true){
				Brick b = new Brick(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y, brickSize,  brickSize, model);
				model.sprites.add(b);
			}else if(crate == true){
				Crate c = new Crate(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y,  brickSize,  brickSize, model);
				model.sprites.add(c);
			}
		}
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  For drawing bricks; capture the location where the mouse was released and create the new brick
	// |-------------------------------------------------------------------------------------------------------------------|
	public void mouseReleased(MouseEvent e){
		
		if(editing == true && sameSize == false){
			
			mouseReleased_x = e.getX();
			mouseReleased_y = e.getY();
			
			// Add the new brick ounce you have the coordinates
			// If mouse dragged in the downward right direction
			if((mousePressed_x < mouseReleased_x) && (mousePressed_y < mouseReleased_y)){
				if(brick == true){
					Brick b = new Brick(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y, mouseReleased_x - mousePressed_x, mouseReleased_y - mousePressed_y, model);
					model.sprites.add(b);
				}else if(crate == true){
					Crate c = new Crate(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y, mouseReleased_x - mousePressed_x, mouseReleased_y - mousePressed_y, model);
					model.sprites.add(c);
				}
			}
			// If mouse dragged upward left direction
			else if((mousePressed_x > mouseReleased_x) && (mousePressed_y > mouseReleased_y)){
				if(brick == true){
					Brick b = new Brick(mouseReleased_x + model.mario.x - model.mario.marioScreenLocation, mouseReleased_y, mousePressed_x - mouseReleased_x, mousePressed_y - mouseReleased_y, model);
					model.sprites.add(b);
				}else if(crate == true){
					Crate c = new Crate(mouseReleased_x + model.mario.x - model.mario.marioScreenLocation, mouseReleased_y, mousePressed_x - mouseReleased_x, mousePressed_y - mouseReleased_y, model);
					model.sprites.add(c);
				}
				
			}
			// If mouse is dragged upward right direction
			else if((mousePressed_x < mouseReleased_x) && (mousePressed_y > mouseReleased_y)){
				if(brick == true){
					Brick b = new Brick(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mouseReleased_y, mouseReleased_x - mousePressed_x, mousePressed_y - mouseReleased_y, model);
					model.sprites.add(b);
				}else if(crate == true){
					Crate c = new Crate(mousePressed_x + model.mario.x - model.mario.marioScreenLocation, mouseReleased_y, mouseReleased_x - mousePressed_x, mousePressed_y - mouseReleased_y, model);
					model.sprites.add(c);
				}
			}
			// If mouse is dragged in downward left direction
			else if((mousePressed_x > mouseReleased_x) && (mousePressed_y < mouseReleased_y)){
				if(brick == true){
					Brick b = new Brick(mouseReleased_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y, mousePressed_x - mouseReleased_x, mouseReleased_y - mousePressed_y, model);
					model.sprites.add(b);
				}else if(crate == true){
					Crate c = new Crate(mouseReleased_x + model.mario.x - model.mario.marioScreenLocation, mousePressed_y, mousePressed_x - mouseReleased_x, mouseReleased_y - mousePressed_y, model);
					model.sprites.add(c);
				}
			}
		}
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Key mapping to various functions
	// |-------------------------------------------------------------------------------------------------------------------|
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_RIGHT: 
				keyRight = true;
				model.mario.movingRight = true;
				model.mario.wasMovingLeft = false;
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				model.mario.movingLeft = true;
				model.mario.wasMovingRight = false;
				break;
			case KeyEvent.VK_SPACE: 
				space = true;  
				break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				model.mario.movingRight = false;
				model.mario.wasMovingRight = true;
				break;
			
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				model.mario.movingLeft = false; 
				model.mario.wasMovingLeft = true;
				break;
			
			case KeyEvent.VK_SPACE: 
				space = false;  
				break;
			
			// save the map
			case KeyEvent.VK_S:
				model.marshal().save("map.json");
				break;
			
			// load the json map
			case KeyEvent.VK_L:
				Json j = Json.load("map.json");
				model.unmarshal(j);
				break;
			
			// change to bricks
			case KeyEvent.VK_1:
				brick = true;
				crate = false;
				break;
			
			// change to crates
			case KeyEvent.VK_2:
				crate = true;
				brick = false;
				break;
			
			// turn editing on and off
			case KeyEvent.VK_E:
				if(editing == false){
					editing = true;
				}else{
					editing = false;
				}
				break;
			
			// adjust the editing mode
			case KeyEvent.VK_Q:
				if(sameSize == false){
					sameSize = true;
				}else{
					sameSize = false;
				}
				break;
				
			case KeyEvent.VK_MINUS:
				brickSize -= 25;
				break;
			
			case KeyEvent.VK_EQUALS:
				brickSize += 25;
				break;
		}

	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Additional key and mouse listener functions
	// |-------------------------------------------------------------------------------------------------------------------|
	public void keyTyped(KeyEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	
}
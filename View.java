//----------------------------------------------------------------------------------|
// Henry Schmidt
// October 15th, 2021
// View class; in charge of printing the background elements and calling other elements
// to print to the screen
//----------------------------------------------------------------------------------|


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.util.Random;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

class View extends JPanel{

	Model model;
	Controller controller;
	BufferedImage ground;
	BufferedImage background;
	static int windowHeight;
	
	//-----------------------------------------------------------------------------------------------------|
	// Update method
	//-----------------------------------------------------------------------------------------------------|
	View(Controller c, Model m){
		model = m;
		controller = c;
		background = null;
		ground = null;
		windowHeight = 0;
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Draw the image at a specific position and paint the specific color
	// |-------------------------------------------------------------------------------------------------------------------|
	public void paintComponent(Graphics g)
	{
		// Set a base background color
		g.setColor(new Color(0,0, 150));
		g.fillRect(0,0, this.getWidth(), this.getHeight());
		
		// Load the background image if its not already 
		if(background == null){
			background = loadImage("HaloBackground.jpg");
		}
		
		// Draw the background image to the screen at a third of the scroll rate
		g.drawImage(background,(int)(0 - model.mario.x)/3, -400, null);
		g.drawImage(background, (int)(0 - model.mario.x)/3, -400, -background.getWidth(), background.getHeight(), null);

		
		// Load the ground image if its not already
		if(ground == null){
			ground = loadImage("Metal_Ground.png");
		}
		
		// Count variable to indicate how many bricks are printed to the screen
		int count = 0;
		g.drawImage(ground, 0 - model.mario.x, this.getHeight() - 50, 50, 50, null);
		
		// Draw the middle set of bricks
		for(int i = 0; i < (model.mario.x + this.getWidth())/50; i++){
			count++;
			// Adjust the printing position to scroll with the characters
			g.drawImage(ground, 50 * i - model.mario.x, this.getHeight() - 50, 50, 50, null);
		}
		
		// Draw the last brick adjusted to the scroll position
		//int remainingSpace = (model.mario.x + this.getWidth()) % 100;
		g.drawImage(ground, count * 50 - model.mario.x, this.getHeight() - 50, 50, 50, null);
		
		// Draw all the sprites
		for(int i = 0; i < model.sprites.size(); i++){
			model.sprites.get(i).draw(g);
		}
		
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Update method
	//-----------------------------------------------------------------------------------------------------|
	void update(){
		windowHeight = this.getHeight();
	}
	
	// |-------------------------------------------------------------------------------------------------------------------|
	//  Designated method for classes to load their images
	// |-------------------------------------------------------------------------------------------------------------------|
	static BufferedImage loadImage(String filename){
		
		BufferedImage image = null;
		try{
			image = ImageIO.read(View.class.getResource(filename));
		}catch(Exception e){
			e.printStackTrace(System.err);
		}
		
		return image;
	}
}
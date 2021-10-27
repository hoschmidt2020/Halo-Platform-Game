//------------------------------------------------------------------------|
//   Henry Schmidt
//   October 16th, 2021
//   Game class; brings together all the different classes of our "game",
//   also runs our refresh function "run" which updates our objects
//------------------------------------------------------------------------|
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame{

	Controller controller;
	Model model;
	View view;
	
	//-----------------------------------------------------------------------------------------------------|
	//Constructor to intialize all the parts of our game and the window
	//-----------------------------------------------------------------------------------------------------|
	public Game(){
		
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		
		// Setup the JFrame window
		this.setTitle("Program Window");
		this.setSize(750, 750);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(controller);
		view.addMouseListener(controller);
		
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Main file to run the program
	//-----------------------------------------------------------------------------------------------------|
	public static void main(String []args){
		Game game = new Game();
		game.run();
	}
	
	//-----------------------------------------------------------------------------------------------------|
	// Run method to continously run the game updating the individual parts as it goes
	//-----------------------------------------------------------------------------------------------------|
	public void run(){
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			view.update(); 
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 miliseconds
			try
			{
				Thread.sleep(40);
				
			}catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		}
	}
}
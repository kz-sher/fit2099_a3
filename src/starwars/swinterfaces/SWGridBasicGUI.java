package starwars.swinterfaces;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import starwars.SWMessageConsole;
/**
 * This class is for our bonus feature. 
 * The main idea is to make the console application more interactive by using swing.
 * Although the class name is related to GUI, The program is still text-based because our main goal is to make it a swing application
 * It can be played by simply pressing keyboard or clicking button for some certain moves
 * 
 * @author kz
 **/
public class SWGridBasicGUI extends JFrame {
	
	/**the component that is responsible for redirecting console output to swing application**/
	private SWMessageConsole mc;
	
	/**the body of swing application**/
	private Container container;
	
	/**the view of game in swing application**/
	private static JTextArea viewTextArea;
	
	/**the button area of game in swing application**/
	private static JPanel mainPanel;
	
	/**the component that allows the view to be scrollable if its size is exceeded beyond the line limit**/
	private JScrollPane scrollPane;
	
	/**the component that is in charge of writing user input to the pipe stream in order to inform the Scanner**/
	private static PrintWriter inWriter;
	
	/**the tunnel that is used to pass input to Scanner from PrintWriter**/
	private final PipedInputStream inPipe = new PipedInputStream();
	
	/**
	 * Constructor for the <code>SWGridBasicGUI</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize some components for the swing application</li>
	 * 	<li>Add them to the Container</li>
	 *  <li>Redirect user input and console out to the swing application</li>
	 * 	<li>Set the program exit if window is closed</li>
	 * 	<li>Set the frame to be visible to the user</li>
	 * </ul>
	 * 
	 */
	public SWGridBasicGUI(){

		this.initialize();
	    this.pack();
	    this.setDefaultCloseOperation(3); // JFrame.EXIT_ON_CLOSE => 3
	    this.setVisible(true); // make the frame visible
	    
	}
	
	/**
	 * This method is responsible for creating the main components that will be put into the swing application
	 * and redirecting the user input and console output to swing application
	 * 
	 */
	public void initialize(){
		
		container = getContentPane(); // initialize the window content area
	    viewTextArea = new JTextArea(); // initialize the text area which is the view of GUI 
	    scrollPane = new JScrollPane(viewTextArea); // allow the view of GUI to be scrollable
	    mainPanel = new JPanel();
		
		viewTextArea.setFont(new Font("monospaced", Font.PLAIN, 12)); // set font family to "monospaced" to have fixed-width fonts
		viewTextArea.setFocusable(true);
		
		container.add(scrollPane, BorderLayout.CENTER);
	    container.add(mainPanel, BorderLayout.SOUTH);
		
	    mc = new SWMessageConsole(viewTextArea);
	    mc.redirectOut(); // redirect all messages in console to the view (textArea) of GUI 
	    mc.redirectErr(); //redirect all errors in console to the view (textArea) of GUI
	    
	    System.setIn(inPipe); // redirect user input to this input stream
	    
	    try { 
	    	inWriter = new PrintWriter(new PipedOutputStream(inPipe), true); 
	    }
	    catch(IOException e) {
	    	System.out.println("Error: " + e);
	    	return;
	    }
	    
	}
	
	/**
	 * This method will make buttons and put them on panel as options for user to play the next move
	 * And it will enable user to move in any direction using keyboard
	 * Every move will trigger this function to refresh the view and the button panel
	 * 
	 * @param cmds - a list of commands that the user can perform
	 */
	public static void makeButtonControl(ArrayList<ActionInterface> cmds){
		
		clearAllMoveListeners(); // clear view's listener
		mainPanel.removeAll(); // clear all button on panel
		
		for (int i = 0; i < cmds.size(); i++) {
			
			String possibleCmd = cmds.get(i).getDescription();
			
			if(possibleCmd.contains("move")){ // if it is "move" action
				// split the string and take the last item to obtain the direction
				String[] splittedPossibleCmd = possibleCmd.split(" ");
				addMoveListener(splittedPossibleCmd[splittedPossibleCmd.length-1], i+1);
				
			}
			else{ // else we make a button for that command
				
				JButton controlButton = new JButton(cmds.get(i).getDescription());
				addClickListener(controlButton, i+1);
				mainPanel.add(controlButton);
				
			}
		}
		
		// refresh the button panel
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	/**
	 * This method create key lister for the text area given to be able to detect key event such as keyboard input
	 * Each key represents an command using input given
	 * If user presses on the specified key, the program will then accept the move and clear the view 
	 * 
	 * @param textArea - the text area that is used to detect key
	 * @param key - the keyboard input (in Java, they would be turned to int type as different inputs have different values)
	 * @param input - an integer that represents a particular command
	 */
	public static void onKeyListener(JTextArea textArea, int key, int input) {
		
		textArea.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == key) {
					inWriter.println(input);
					viewTextArea.setText("");
				}
				
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// this method is not needed
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// this method is not needed
			}
	    	
	    });

	}
	
	/**
	 * This method creates event listener for button to detect mouse click
	 * It will perform a certain move if user clicks on it
	 * 
	 * @param button - the button that is shown as an option for user
	 * @param input - an integer that represents a particular command
	 */
	private static void addClickListener(JButton button, int input){
		button.addActionListener(new ActionListener(){
	    	
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	inWriter.println(input);
	            viewTextArea.setText("");
	            
	        }
	        
	    });
	}
	
	/**
	 * This method allows user to move by pressing keyboard key
	 * It will create key listener for different direction as shown below:
	 * 
	 * - Q for Northwest
	 * - W for North
	 * - E for Northeast
	 * - A for West 
	 * - D for East
	 * - Z for Southwest
	 * - X for South
	 * - C for Southeast
	 * 
	 * @param direction - a string that represents the direction on compass
	 * @param input - an integer that represents a particular command
	 */
	public static void addMoveListener(String direction, int input){
		
		if(direction.equals("NORTH")){
			onKeyListener(viewTextArea, KeyEvent.VK_W, input);
		}
		else if(direction.equals("SOUTH")){
			onKeyListener(viewTextArea, KeyEvent.VK_X, input);
		}
		else if(direction.equals("EAST")){
			onKeyListener(viewTextArea, KeyEvent.VK_D, input);
		}
		else if(direction.equals("WEST")){
			onKeyListener(viewTextArea, KeyEvent.VK_A, input);
		}
		else if(direction.equals("NORTHEAST")){
			onKeyListener(viewTextArea, KeyEvent.VK_E, input);
		}
		else if(direction.equals("NORTHWEST")){
			onKeyListener(viewTextArea, KeyEvent.VK_Q, input);
		}
		else if(direction.equals("SOUTHEAST")){
			onKeyListener(viewTextArea, KeyEvent.VK_C, input);
		}
		else if(direction.equals("SOUTHWEST")){
			onKeyListener(viewTextArea, KeyEvent.VK_Z, input);
		}
		
	}
	
	/**
	 * This method will clear the status of the text area by simply removing all listeners added to it
	 *
	 */
	public static void clearAllMoveListeners(){
		
		KeyListener[] listenerList = viewTextArea.getListeners(KeyListener.class);
		for(KeyListener listener : listenerList){
			viewTextArea.removeKeyListener(listener);
		}
		
	}

}

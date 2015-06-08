import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Revani Govender
 * CMIS 242 6381
 * Final Project
 * Due March 2, 2014
 * Compiler - Eclipse
 * 
 * ReadStoreShow will ask the user to choose a value for N and a file
 * The GUI will display sorted colors based on the user choice of file and number to display
 * For comparison, the original ArrayList of colors and sorted colors will be displayed
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ReadStoreShow extends JFrame{
	
	private static JRadioButton button = new JRadioButton();
	
	private static ArrayList<String> colorsHex = new ArrayList<String>();
	private static ArrayList<String> guiColors = new ArrayList<String>();
	private static ArrayList<String> colors = new ArrayList<String>(); 
	
	private static int numberColors;
	
	private static String[] cols = null;
	private String color;
	

	
	public ReadStoreShow(){
		
		final JPanel rButtonPanel = new JPanel();
		//title color selection
		rButtonPanel.setBorder(new TitledBorder("Choose a color: "));

		//add buttons/panel
		rButtonPanel.setLayout(new GridLayout(guiColors.size(), 1));
		ButtonGroup group = new ButtonGroup();
		
		for(int i = 0; i < guiColors.size(); i++){
			
			//insert panel/buttons
			cols = guiColors.get(i).split(" ");
			final JRadioButton button = new JRadioButton(i + " " + cols[0]);
			rButtonPanel.add(button);
			group.add(button);
			button.addActionListener(new ActionListener() {

				//Insert an action listener for each button
				public void actionPerformed(ActionEvent e) {
					cols = button.getText().split(" ");
					int index = Integer.parseInt(cols[0]);
					setColor(index);
					Color colorShade = Color.decode(color);
					rButtonPanel.setBackground(colorShade);
					getContentPane().setBackground(colorShade);
					
				}//end actionPerformed
			});//end ActionListener
		}//end for loop
		
		
		//layout of buttons
		add(rButtonPanel, BorderLayout.WEST);
		
	}//end ReadStoreShow
	
	/**
	 * Scan the file and put in 2 ArrayLists.
	 * The color ArrayList holds color names and hex codes
	 * The colorHex ArrayList holds the hex codes that need to be sorted
	 * @param file
	 * @return file
	 * @throws FileNotFoundException
	 */
	public static File scanFile(File file) throws FileNotFoundException{

			Scanner inputFile = new Scanner(file);
			
			while(inputFile.hasNextLine()){
			//add the line, then split the line
			String line = inputFile.nextLine();
				if (colors.size() < numberColors){
					colors.add(line);
				}//end if
				else{
					break;
				}//end else
			}//end while

						
			for(int i = 0; i < colors.size(); i++){
				//using the array list tools to sort the hex value into their own array list 
				cols = colors.get(i).split(" ");
				colorsHex.add(cols[1]);
			}//end for 
			
			//print for comparison (before)
			System.out.println("Before Sorting:");
			Iterator<String> beforeColor = colors.iterator();
			while(beforeColor.hasNext()) {
				String outAfter = beforeColor.next();
				System.out.println(outAfter);
			}//end while
			
			/*
			 * Sort through colors and arrange in ascending order by Hex value
			 * When hex code is swapped then the color list also needs to be swapped
			 * Also, generate guiColors for use with the GUI
			 */
			
			for(int i=0;i<colorsHex.size();i++){
			    for(int j=i+1;j<colorsHex.size();j++){
			    	String color1 = colorsHex.get(i);
			        if(color1.compareTo(colorsHex.get(j))>0){
			            Collections.swap(colorsHex, i, j);
			            Collections.swap(colors, i, j);
			        }//end if
			    }//end for
			}//end for
			
			//print for comparison (after)
		//add colors to GUI	
		for (int i = 0; i < colorsHex.size(); i++){
			guiColors.add(colors.get(i));
			}//end for
		
		System.out.println("\nAfter Sorting:");
		Iterator<String> afterColor = colors.iterator();
		while(afterColor.hasNext()) {
			String outAfter = afterColor.next();
			System.out.println(outAfter);
		}//end while
		
		return file;
	}//end scanFile
	
	
	
	
	/**
	 * set the color in the GUI
	 * @param x
	 * @return color
	 */
	private String setColor(int x) {
		cols = guiColors.get(x).split(" ");
		color = cols[1];
		return color;
	}
	
	/**
	 * User selects how many colors and the file which they want to use
	 * Main method creates the JFrame 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String []args) throws FileNotFoundException{	
		System.out.println("Revani Govender, CMIS 242 6381, Final Project");
			
			//choose how many colors to display
			boolean colorNumEntered = false;
			do{
				String colorNumberInput = JOptionPane.showInputDialog(""
						+ "Enter a number from 10 to 20 of colors you want to choose from. \n"
						+ "Clicking cancel will use the default value of 10 ");
					if (colorNumberInput == null){
						String colorNumberString = "10";
						numberColors = Integer.parseInt(colorNumberString);
						colorNumEntered = true;
					}//end if
					else if (colorNumberInput.equals("")){
						colorNumEntered = false;
					}//end else if
					else{
						try{numberColors = Integer.parseInt(colorNumberInput);
						if(numberColors <10 || numberColors > 20){
							JOptionPane.showMessageDialog( null, "Enter an integer from 10 to 20");
							colorNumEntered = false;
						}//end if
						
						else{
						colorNumEntered = true;
						}//end else
						}catch(Exception e){
							JOptionPane.showMessageDialog( null, "Enter an integer from 10 to 20");
						}//end catch
						
					}//end else
					
			}while(!colorNumEntered);
			
			//choose the file
			boolean fileInput = false;
			do{
				String fileNameInput = JOptionPane.showInputDialog("Enter the file Name. \n"
					+ "Clicking cancel will use the default file: ");
				if (fileNameInput == null){
					File file = new File ("colors.txt");
					scanFile(file);
					fileInput = true;
				}//end if
				//if no file name is entered, the loop will repeat
				else if (fileNameInput.equals("")){
					fileInput = false;
				}//end else if
				//Making sure the file name is valid
				else{
					try{File file = new File (fileNameInput);
					System.out.print(fileNameInput);
					scanFile(file);
					fileInput = true;
					}catch(Exception e){
						JOptionPane.showMessageDialog( null, "Enter a valid file name");
					}//end catch
				}//end else
			}while (!fileInput);		
		
		//Create the frame
		ReadStoreShow frame = new ReadStoreShow();
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Display Your Colors!");
		frame.setSize(360, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}//end main
}//end class


package project;


import javax.swing.JFrame;


/**
<<<<<<< HEAD
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi 
 *
 *   Honor: I have completed this assignment on my own.
 *          In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: ProjectGoFit.java 
 *
 *   Description: This application allows users to track the progress of their strength training fitness regimen.
 *                users are able to add or delete each day's workout (rm and calories) and save the changed date to a file. 
 *                This is the main class to create an instance of MainGui class and set the size and visibility of window.
 * 
 * 
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */
=======
   *   Submitted by: Alex Yeji Park && Chris Sarvghadi 
   *
   *   Honor: I have completed this assignment on my own.
   *       In researching the assignment I got help/ideas from http://stackoverflow.com/ 
   *
   *   File name: ProjectGoFit.java 
   *
   *   Description: This application allows users to track the progress of their strength training fitness regimen.
   *                users are able to add or delete each day's workout (rm and calories) and save the changed date to a file. 
   *                This is the main class to create an instance of MainGui class and set the size and visibility of window.
   * 
   * 
   *   @author Alex Yeji Park && Chris Sarvghadi 
   */
>>>>>>> origin/master

public class ProjectGoFit 
{

    public static void main(String[] args)
    {
         // create a mainGui
        MainGui gui = new MainGui();

        // set its visibillity
        gui.setVisible(true);
        
        // make it be large enough for all component
        gui.pack();
        
        // make it disabled for users to resize it
        gui.setResizable(false);
        
        // make it be located in the middle of the screen
        gui.setLocationRelativeTo(null);
        
        // set the window's title
        gui.setTitle("Go Fit");
        
        // set the close button to exit the program 
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
    }
}

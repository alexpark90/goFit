
package project;


import javax.swing.JFrame;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE : 22-Apr-2015
//
/////////////////////////////////////////////////////

public class Project 
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


package project;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


/**
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi
 *
 *   Honor: I have completed this assignment on my own.
 *       In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: SubGui.java 
 *   
 *   Description: This is the sub GUI class of this application. 
 *              It contains interface to transfer user input data to mainGui.
 *
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */

public class SubGui extends JFrame implements ValidateInput
{

    // interface to force the class using the SubGui to implement the abstract method below
    interface TransferData
    {
        public abstract void onChildUpdate(String name, int age, String gender);
    }
    
    /////////////////////// FIELDS //////////////////////
    private TransferData parent;
    private String[] genderChoice = {"Male", "Female", "Else"};
    
    private JTextField nameField = new JTextField(5);
    private JTextField ageField = new JTextField(5);
    private JComboBox genderBox = new JComboBox(genderChoice);

    ////////////////////// CONSTRUCTOR ////////////////////
    
    public SubGui(TransferData parent) 
    {
        super("Create new Account");

        this.parent = parent;
        this.setPreferredSize(new Dimension(250, 300));
        
        // set the JFrame's layout to GridLayout
        setLayout(new GridLayout(4, 1));

        // create JPanel for getting a name from the user
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        namePanel.setBorder(new EmptyBorder(15, 50, 0, 0));

        namePanel.add(new JLabel(String.format("%-10s", "Name :")));
        namePanel.add(nameField);

        // add the namePanel to the JFrame
        add(namePanel);
        
        
        // create JPanel for getting a name from the user
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        agePanel.setBorder(new EmptyBorder(0, 50, 0, 0));

        agePanel.add(new JLabel(String.format("%-13s", "Age :")));
        agePanel.add(ageField);

        // add the namePanel to the JFrame
        add(agePanel);
        
        
        // create JPanel for getting a name from the user
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        genderPanel.setBorder(new EmptyBorder(0, 50, 0, 0));

        genderPanel.add(new JLabel(String.format("%-10s", "Gender :")));
        genderPanel.add(genderBox);

        // add the namePanel to the JFrame
        add(genderPanel);

        // create Jpanel for the buttons at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        JButton createBtn = new JButton("Create");
        JButton resetBtn = new JButton("Reset");
                
        createBtn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    // before pass the data, validate inputs first.
                    // if there is not valid input, it will throws InvalidInputException.
                    validateInput();
                    SubGui.this.parent.onChildUpdate(nameField.getText(), 
                            Integer.parseInt(ageField.getText()), 
                            (String)genderBox.getSelectedItem());
                    // after passing, close this dialog.        
                    SubGui.this.dispose();
                }
                catch(InvalidInputException eex)
                {
                    // show the error dialog.
                    JOptionPane.showMessageDialog(null, eex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
            }
        });
                        
        resetBtn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // get the user response using confirmDialog to double check the user's intention.
                int reponse = JOptionPane.showConfirmDialog(null, "Are you sure, jackass?", 
                        "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                // only if the user click yes
                if(reponse == JOptionPane.YES_OPTION)
                {
                    reset();
                }
            }
        });
        
        bottomPanel.add(createBtn);
        bottomPanel.add(resetBtn);

        //  add the bottomPanel to the frame
        add(bottomPanel);
    }
    
    // override validateInput method of ValidateInput interface
    @Override
    public void validateInput() throws InvalidInputException
    {
        try
        {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());

            String fileName = name.replace(" ", "_");        
            File file = new File(fileName+".json");

            if(name.equals("") || ageField.getText().equals(""))
            {
                throw new InvalidInputException("All fields should be filled in");
            }
            else if(file.exists())
            {
                throw new InvalidInputException("Same named file already exists.");
            }
            else if(age < 0)
            {
                throw new InvalidInputException("Age must be an positive integer number.");
            }
        }
        catch(NumberFormatException ex)
        {
            throw new InvalidInputException("Age must be an positive integer number.");
        }
    }

    // override reset method of ValidateInput interface
    @Override
    public void reset()
    {
        nameField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
    }
}


package project;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE : 14-Apr-2015
//
/////////////////////////////////////////////////////

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
        
        // set the JFrame's layout to GridLayout
        setLayout(new GridLayout(4, 1));

        // create JPanel for getting a name from the user
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

        namePanel.add(new JLabel(String.format("%-10s", "Name :")));
        namePanel.add(nameField);

        // add the namePanel to the JFrame
        add(namePanel);
        
        
        // create JPanel for getting a name from the user
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

        agePanel.add(new JLabel(String.format("%-13s", "Age :")));
        agePanel.add(ageField);

        // add the namePanel to the JFrame
        add(agePanel);
        
        
        // create JPanel for getting a name from the user
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));

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
                if(validateInput())
                {
                    SubGui.this.parent.onChildUpdate(nameField.getText(), Integer.parseInt(ageField.getText()), 
                            (String)genderBox.getSelectedItem());
                    SubGui.this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "All fields should be filled in", 
                            "Error", JOptionPane.ERROR_MESSAGE);
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

                
//        cancelBtn.addActionListener(new ActionListener() 
//        {
//            @Override
//            public void actionPerformed(ActionEvent e) 
//            {
//                // get the user response using confirmDialog to double check the user's intention.
//                int reponse = JOptionPane.showConfirmDialog(null, "Are you sure, jackass?", 
//                        "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
//                // only if the user click yes
//                if(reponse == JOptionPane.YES_OPTION)
//                {
//                    SubGui.this.dispose();
//                }
//            }
//        });
//        
        bottomPanel.add(createBtn);
        bottomPanel.add(resetBtn);

        //  add the bottomPanel to the frame
        add(bottomPanel);
    }
    
    
    @Override
    public boolean validateInput()
    {
        if(nameField.getText().equals("") || ageField.getText().equals(""))
        {
            return false;
        }        
        try
        {
            int age = Integer.parseInt(ageField.getText());
            if(age < 0)
            {
                return false;
            }
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null,
                        "Age must be an positive integer number.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    @Override
    public void reset()
    {
        nameField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
    }
}

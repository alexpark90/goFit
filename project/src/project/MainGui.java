
package project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.event.*;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

public class MainGui extends JFrame implements SubGui.TransferData, ValidateInput
{
    /////////////////////////// FIELDS /////////////////////////////
    
    CardLayout cards = new CardLayout();
    JPanel cardPanel;
    final static String HOME = "HOME";
    final static String ENTRY = "ENTRY";
    final static String LOGS = "LOGS";
    final static String GRAPHS = "GRAPHS";
    final static String[] MENU_LIST = {HOME, ENTRY, LOGS, GRAPHS};
    
    private SubGui child;
    
    private UserAccount account;
    
    JPanel card1 = new JPanel();
    JPanel card2 = new JPanel();
    JPanel card3 = new JPanel();
    JPanel card4 = new JPanel();
    JList menu = new JList();
    

    public MainGui()
    {
        // set the JFrame's layout to BorderLayout
        setLayout(new BorderLayout());
             
        
        //////////////////////////////////////// WEST //////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // put MENU_LIST to menu to show the elements in it
        menu.setListData(MENU_LIST);
        
        // set the mune's border and padding 
        menu.setBorder(new EmptyBorder(5, 5, 5, 10));
        menu.setFixedCellHeight(30);
        
        // add the listener to the menu
        menu.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                cards.show(cardPanel, (String)menu.getSelectedValue());
            }
        });
        
        // add the menu to the west area
        add(menu, BorderLayout.WEST);
        
        
        //////////////////////////////////////// CENTER /////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // set the cardPanel's layout to the cardLayout
        cardPanel = new JPanel(cards);
        
        
        // JLabels will be replaced to the corresponding Layout for each card later
        card1.add(new JLabel("This is home page"));
        card2.add(new JLabel("This is Entry page"));
        card3.add(new JLabel("This is Log page"));
        card4.add(new JLabel("This is graph page"));
        
        
        ///////////////////////////// HOME ///////////////////////////////
        
        // create buttons to create or open an user account
        JButton createBtn = new JButton("Create");
        JButton openBtn = new JButton("Open");
          
        // add the listener to the createBtn
        createBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                if(child==null)
                {
                    child = new SubGui(MainGui.this);
                    child.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    child.setLocationRelativeTo(MainGui.this);
                    child.pack();
                }
                child.setVisible(true);
            }
        });
        
        // add the listener to the openBtn
        openBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                openFile();
            }              
        });
        
        // add buttons to the card1
        card1.add(createBtn);
        card1.add(openBtn);
        
        
        ///////////////////////////// ENTRY ///////////////////////////////
        
        
        
        
        // code for the layout for the entry section
        
        
        
        
        
        ///////////////////////////// LOGS ///////////////////////////////
        
        
        
        // code for the layout for the logs section
        
        
        
        
        
        ///////////////////////////// GRAPHS ///////////////////////////////
        
        
        
        // code for the layout for the graphs sextion 
        
        
        
        
        
        
        ////////////////////////////////////////////////////////////////////////////
        
        
        // add each card to the cardPanel
        cardPanel.add(card1, HOME);
        cardPanel.add(card2, ENTRY);
        cardPanel.add(card3, LOGS);
        cardPanel.add(card4, GRAPHS);
        
        // show HOME card when the app start 
        cards.show(cardPanel, HOME);
        
        // add the cardpanel to the center area
        add(cardPanel, BorderLayout.CENTER);
    }

    
    private void openFile()
    {
        // code to open the file...
    }
    
    
    @Override
    public void onChildUpdate(String name, String age, String gender)
    {
        account = new UserAccount(name, Integer.parseInt(age), gender.charAt(0));
        System.out.println(account);  // ------> for debugging
    }

    @Override
    public boolean validateInput()
    {
        return true;
    }

    @Override
    public void reset()
    {
    }
    
}

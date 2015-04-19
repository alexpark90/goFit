
package project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.event.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/////////////////////////////////////////////////////
//                                                 
// AUTHOR : Alex Park & Chris Sarvghadi            
// CREATE : 7-Apr-2015                            
// UPDATE : 17-Apr-2015                                       
//                                                 
/////////////////////////////////////////////////////

public class MainGui extends JFrame implements SubGui.TransferData, ValidateInput
{
    ///////////////////////////////// FIELDS //////////////////////////////////
    
    
    final static String HOME = "HOME";
    final static String ENTRY = "ENTRY";
    final static String LOGS = "LOGS";
    final static String GRAPHS = "GRAPHS";
    final static String[] MENU_LIST = {HOME, ENTRY, LOGS, GRAPHS};
    
    
    final static String NAME = "Name";
    final static String AGE = "Age";
    final static String GENDER = "Gender";
    final static String[] EXERCISE_LIST = {"Bicep", "Tricep", "DeadLift", "BackExtension", "Squat", "LegPress", "BenchPress",};
    
    
    private UserAccount account;
    private ArrayList<Exercise> exerciseList;    
    private Exercise bicep;
    private Exercise tricep;
    private Exercise deadLift;
    private Exercise backExtension;
    private Exercise squat;
    private Exercise legPress;
    private Exercise benchPress;
    
    private JSONObject accountJson;
    private JSONObject exerciseListJson;
    private JSONArray bicepJson;
    private JSONArray tricepJson;
    private JSONArray deadLiftJson;
    private JSONArray backExtensionJson;
    private JSONArray squatJson;
    private JSONArray legPressJson;
    private JSONArray benchPressJson;
    
    private SubGui child;
    
    JPanel menuPanel = new JPanel();    
    
    CardLayout cards = new CardLayout();
    JPanel cardPanel;
    
    JPanel homeCard = new JPanel();
    JPanel entryCard = new JPanel(new BorderLayout());
    JPanel logsCard = new JPanel();
    JPanel graphsCard = new JPanel();
    
    JList menu;
    JComboBox exerciseComboBox;
    
    File file;
    File directory;
    
    int index;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// CONSTRUCTOR ////////////////////////////////////////////
    
    public MainGui()
    {
        // set the JFrame's layout to BorderLayout
        setLayout(new BorderLayout());

        //////////////////////////////////////// WEST //////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        // create menu Jlist and put MENU_LIST to menu to show the elements in it
        menu = new JList();
        menu.setListData(MENU_LIST);
        
        // set the menu's border and padding 
        menu.setBorder(new EmptyBorder(5, 5, 225, 15));
        menu.setFixedCellHeight(25);
        
        // add the listener to the menu
        menu.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                cards.show(cardPanel, (String)menu.getSelectedValue());
            }
        });
        
        // create the line dividing the menu from the rest of frame
        JSeparator menuSeparator = new JSeparator(SwingConstants.VERTICAL);
        menuSeparator.setPreferredSize(new Dimension(5, 345));
    
        //////////////////////////////////////// CENTER /////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // set the cardPanel's layout to the cardLayout
        cardPanel = new JPanel(cards);
        
        
        // JLabels will be replaced to the corresponding Layout for each card later
        homeCard.add(new JLabel("This is home page"));

        logsCard.add(new JLabel("This is Log page"));
        graphsCard.add(new JLabel("This is graph page"));
        
        
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
                if(openFile())
                {
                    readJsonFile();
                }
                
                System.out.println(account);  // ---------> for debugging
            }              
        });
        
        // add buttons to the card1
        homeCard.add(createBtn);
        homeCard.add(openBtn);
        
        
        ///////////////////////////// ENTRY ///////////////////////////////
        
        JPanel entryPanelNorth = new JPanel(new BorderLayout());
        entryPanelNorth.setBorder(new EmptyBorder(15, 5, 0, 0));
        
        JPanel entryPanelWest = new JPanel(new BorderLayout());
        JPanel entryPanelSouth = new JPanel(new BorderLayout());
        
        JPanel entryPanelEast = new JPanel(new GridLayout(11, 2));
        entryPanelEast.setBorder(new EmptyBorder(15, 55, 0, 0));
        
        JPanel entryInputGrid = new JPanel(new GridLayout(4, 2, 5, 5));
        
        JPanel entryPanel3 = new JPanel();
        JPanel entryPanel4 = new JPanel();
        
        
        
        
        String[] fakeExerciseArray = {"Exercise", "Bicep curls", "Squats", "Benchpress"};
        exerciseComboBox = new JComboBox(fakeExerciseArray);
        
        entryPanelNorth.add(exerciseComboBox, BorderLayout.WEST);
        //entryPanel1.add(Box.createHorizontalStrut(330));
        
        
        
        
        // Input grid on left side of Entry page
        JTextField dateField = new JTextField(20);
        JTextField liftedField = new JTextField(20);
        JTextField repsField = new JTextField(20);
        JTextField caloriesField = new JTextField(20);
        
        entryInputGrid.add(new JLabel("Date: "));
        entryInputGrid.add(dateField);
        
        entryInputGrid.add(new JLabel("Weight Lifted: "));
        entryInputGrid.add(liftedField);
        
        entryInputGrid.add(new JLabel("Repetitions: "));
        entryInputGrid.add(repsField);
        
        entryInputGrid.add(new JLabel("Calories burned: "));
        entryInputGrid.add(caloriesField);
        
        
        
        // RM grid on right side of Entry page
        JTextField RM50 = new JTextField(6);
        JTextField RM55 = new JTextField(6);
        JTextField RM60 = new JTextField(6);
        JTextField RM65 = new JTextField(6);
        JTextField RM70 = new JTextField(6);
        JTextField RM75 = new JTextField(6);
        JTextField RM80 = new JTextField(6);
        JTextField RM85 = new JTextField(6);
        JTextField RM90 = new JTextField(6);
        JTextField RM95 = new JTextField(6);
        JTextField RM100 = new JTextField(6);
        
        entryPanelEast.add(new JLabel("50% 1RM: "));
        entryPanelEast.add(RM50);
        entryPanelEast.add(new JLabel("55% 1RM: "));
        entryPanelEast.add(RM55);
        entryPanelEast.add(new JLabel("60% 1RM: "));
        entryPanelEast.add(RM60);
        entryPanelEast.add(new JLabel("65% 1RM: "));
        entryPanelEast.add(RM65);
        entryPanelEast.add(new JLabel("70% 1RM: "));
        entryPanelEast.add(RM70);
        entryPanelEast.add(new JLabel("75% 1RM: "));
        entryPanelEast.add(RM75);
        entryPanelEast.add(new JLabel("80% 1RM: "));
        entryPanelEast.add(RM80);
        entryPanelEast.add(new JLabel("85% 1RM: "));
        entryPanelEast.add(RM85);
        entryPanelEast.add(new JLabel("90% 1RM: "));
        entryPanelEast.add(RM90);
        entryPanelEast.add(new JLabel("95% 1RM: "));
        entryPanelEast.add(RM95);
        entryPanelEast.add(new JLabel("1RM: "));
        entryPanelEast.add(RM100);
        
        JButton calculateButton = new JButton("Calculate");
        entryPanel3.add(calculateButton);
        
        entryPanelWest.add(entryInputGrid, BorderLayout.CENTER);
        entryPanelWest.add(entryPanel3, BorderLayout.SOUTH);
        
        JButton newRecordButton = new JButton("Add New");
        JButton editRecordButton = new JButton("Edit");
        entryPanel4.add(newRecordButton);
        entryPanel4.add(editRecordButton);
        entryPanelSouth.add(entryPanel4);
        
        entryCard.add(entryPanelNorth, BorderLayout.NORTH);
        entryCard.add(entryPanelWest, BorderLayout.WEST);
        entryCard.add(entryPanelSouth, BorderLayout.SOUTH);
        entryCard.add(entryPanelEast, BorderLayout.EAST);
        
        ///////////////////////////// LOGS ///////////////////////////////
        
        
        
        // code for the layout for the logs section
        
        
        
        
        
        ///////////////////////////// GRAPHS ///////////////////////////////
        
        
        
        // code for the layout for the graphs section 
        
        
        
        
        
        
        //////////////////////////////////////////////////////////////////////////////
        
        // add components to the menuPanel (left side)
        menuPanel.add(menu);
        menuPanel.add(menuSeparator);
        menuPanel.setBorder(new EmptyBorder(-5, 0, -5, 0));
        
        // add the menupanel to the west area
        add(menuPanel, BorderLayout.WEST);
        
        // add each card to the cardPanel
        cardPanel.add(homeCard, HOME);
        cardPanel.add(entryCard, ENTRY);
        cardPanel.add(logsCard, LOGS);
        cardPanel.add(graphsCard, GRAPHS);
        
        // show HOME card when the app start 
        cards.show(cardPanel, HOME);
        
        // add the cardpanel to the center area
        add(cardPanel, BorderLayout.CENTER);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////METHODS////////////////////////////////////////////////
    
    private boolean openFile()
    {
        
        // create JFileChooser to open a file from the current directory
        JFileChooser openChooser = new JFileChooser();
        
        if(directory!=null) 
        {
            openChooser.setCurrentDirectory(directory);
        }
        else
        {
            openChooser.setCurrentDirectory(new File("."));
        }

        // show open Dialog so that the user can select the file. 
        // if the user opens a file, it set the file
        if(openChooser.showOpenDialog(MainGui.this) == JFileChooser.APPROVE_OPTION)
        {
            file = openChooser.getSelectedFile();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private UserAccount readJsonFile()
    {    
        try 
        {
            Scanner reader = new Scanner(file);
            
            StringBuilder jsonBuilder = new StringBuilder(); 
            
            // read a file using scanner and store the data to jsonBuilder
            while (reader.hasNextLine()) 
            {
                jsonBuilder.append(reader.nextLine());
            }
            
            String jsonString = jsonBuilder.toString();
            
            JSONParser parser = new JSONParser();
            
            // root represents one instance of UserAccount 
            accountJson = (JSONObject) parser.parse(jsonString);
            
            
            // exerciseListJson contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) accountJson.get("ExerciseList");
            
            String name = (String) accountJson.get(NAME);
            int age = (int)((long) accountJson.get(AGE));
            char gender = ((String)accountJson.get(GENDER)).charAt(0);
            account = new UserAccount(name, age, gender);
            
            
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[0]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[1]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[2]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[3]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[4]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[5]);
//            bicepJson = (JSONArray)exerciseListJson.get(EXERCISE_LIST[6]);
        }
        catch (FileNotFoundException | ParseException ex) 
        {
            System.out.println(ex);
        }
        
        return account;
    }
    
    private boolean saveFile()
    {
        if(file==null || account==null)
        {
            return false;
        }
        
        try(PrintWriter writer = new PrintWriter(file);)
        {
//            
//            JSONObject accountJson = new JSONObject();
            
//            
//            root.put(NAME, account.getName());
//            root.put(AGE, account.getAge());
//            root.put(GENDER, account.getGender());
//            
//            
//            exerciseListJson.put(EXERCISE_LIST[0], bicepJson);
//            exerciseListJson.put(EXERCISE_LIST[1], tricepJson);
//            exerciseListJson.put(EXERCISE_LIST[2], deadLiftJson);
//            exerciseListJson.put(EXERCISE_LIST[3], backExtensionJson);
//            exerciseListJson.put(EXERCISE_LIST[4], squatJson);
//            exerciseListJson.put(EXERCISE_LIST[5], legPressJson);
//            exerciseListJson.put(EXERCISE_LIST[6], benchPressJson);
            
            exerciseListJson.replace(EXERCISE_LIST[index], exerciseComboBox.getSelectedItem());
            accountJson.replace("ExerciseList", exerciseListJson);
            
            writer.printf(accountJson.toJSONString());
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        return true;
    }
    
    @Override
    public void onChildUpdate(String name, int age, String gender)
    {
        String fileName = name.replace(" ", "_");        
        file = new File(fileName+".json");
        
        if(file.exists())
        {
            JOptionPane.showMessageDialog(null, "Same Name is already existing", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        account = new UserAccount(name, age, gender.charAt(0));
        
        accountJson = new JSONObject();
            
        accountJson.put(NAME, account.getName());
        accountJson.put(AGE, account.getAge());
        accountJson.put(GENDER, String.valueOf(account.getGender()));
            
        exerciseListJson = new JSONObject();
        
        accountJson.put("ExerciseList", exerciseListJson);
        
        
        if(saveFile())
        {
            JOptionPane.showMessageDialog(null, "Your account created successfully!", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
            child=null;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "[Error]What can i say..... umm...", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
        }
        
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

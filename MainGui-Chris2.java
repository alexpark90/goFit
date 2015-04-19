Enter file contents here
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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    JPanel logsCard = new JPanel(new BorderLayout());
    JPanel graphsCard = new JPanel();
    
    JLabel logName;
    JLabel logAge;
    JLabel logSex;

    ArrayList<JSONObject> a = new ArrayList<>();
    JList logsList;
    JScrollPane logsScrollPane = new JScrollPane();
    
    
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
                    readJsonFile(file);
                }
                
                System.out.println(account);  // ---------> for debugging
            }              
        });
        
        // add buttons to the card1
        homeCard.add(createBtn);
        homeCard.add(openBtn);
        
        
        ///////////////////////////// ENTRY ///////////////////////////////
        
        JPanel entryPanelNorth = new JPanel(new BorderLayout());
        entryPanelNorth.setBorder(new EmptyBorder(15, 23, 15, 0));
        
        JPanel entryPanelWest = new JPanel(new BorderLayout());
        entryPanelWest.setBorder(new EmptyBorder(5, 25, 0, 0));
        entryPanelWest.setPreferredSize(new Dimension(300, 40));
        
        JPanel entryPanelSouth = new JPanel(new BorderLayout());
        
        JPanel entryPanelEast = new JPanel(new GridLayout(11, 2));
        entryPanelEast.setBorder(new EmptyBorder(0, 0, 0, 35));
        
        JPanel entryPanel3 = new JPanel();
        JPanel entryPanel4 = new JPanel();

        final JComboBox exerciseComboBox2 = new JComboBox(EXERCISE_LIST);
        
      
        // date / weight / reps / calories grid on left side of page
        JPanel entryInputGrid = new JPanel(new GridLayout(4, 2, 5, 15));
        entryInputGrid.setBorder(new EmptyBorder(0, 0, 30, 0));
        
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
        
        entryPanelWest.add(entryInputGrid, BorderLayout.CENTER);
        entryPanelWest.add(entryPanel3, BorderLayout.SOUTH);
        
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

        
        // action Listener for the combo box, to load entry page
        exerciseComboBox2.addActionListener(new ActionListener() 
        {
        
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                System.out.println(exerciseComboBox2.getSelectedItem());
                
                
                Object selectedItem = exerciseComboBox2.getSelectedItem();
                String b = "bicep";
                if (b.equals(selectedItem)){
                    System.out.println("lmao");

                }
            }
        });
        
        entryPanelNorth.add(exerciseComboBox2, BorderLayout.WEST);
        
        JButton calculateButton = new JButton("Calculate");
        entryPanel3.add(calculateButton);

        JButton newRecordButton = new JButton("Add New");
        JButton editRecordButton = new JButton("Edit");
        entryPanel4.add(newRecordButton);
        entryPanel4.add(editRecordButton);
        entryPanelSouth.add(entryPanel4);

        entryCard.add(entryPanelNorth, BorderLayout.NORTH);
        entryCard.add(entryPanelWest, BorderLayout.WEST);
        entryCard.add(entryPanelEast, BorderLayout.EAST);
        entryCard.add(entryPanelSouth, BorderLayout.SOUTH);
        
        
        
        ///////////////////////////// LOGS ///////////////////////////////
        
        exerciseComboBox = new JComboBox(EXERCISE_LIST);
        
        GridLayout gl = new GridLayout(1, 3, 0 , 0);
        
        // Panels that will go in each section of logsCard page borderlayout
        JPanel logsPanelNorth = new JPanel(new GridLayout(1, 3, 0, 0));
        JPanel logsPanelEast = new JPanel();
        JPanel logsPanelSouth = new JPanel(new BorderLayout());

        logName = new JLabel("Name: ");
        logAge = new JLabel ("Age: ");
        logSex = new JLabel ("Sex: ");
        
        // components added on top of logs page
        logsPanelNorth.add(logName);
        logsPanelNorth.add(logAge);
        logsPanelNorth.add(logSex);
        
        logsPanelEast.add(exerciseComboBox);
        
        // components added on bottom of logs page
        
            // labels section of bottom of page
            JPanel logsPanelSouthLabels = new JPanel(gl);
            logsPanelSouthLabels.add(new JLabel("Date: "));
            logsPanelSouthLabels.add(new JLabel("RM: "));
            logsPanelSouthLabels.add(new JLabel("Calories: "));
        


        
        
        logsScrollPane = new JScrollPane(logsList);

        logsScrollPane.setPreferredSize(new Dimension(650, 270));
        
        logsPanelSouth.add(logsPanelSouthLabels, BorderLayout.NORTH);
        logsPanelSouth.add(logsScrollPane, BorderLayout.SOUTH);
        

        
        
        
        exerciseComboBox.addActionListener(new ActionListener() 
        {
        
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                System.out.println(exerciseComboBox.getSelectedItem());

                Object selectedItem = exerciseComboBox.getSelectedItem();
                
                /*String b = "bicep";
                if (b.equals(selectedItem))
                    System.out.println("selected");*/


                        
                        
                        
            }
        });
        
        
        
      
        
        
        // logs page subsections added to main logs panel
        logsCard.add(logsPanelNorth, BorderLayout.NORTH);
        logsCard.add(logsPanelEast, BorderLayout.EAST);
        logsCard.add(logsPanelSouth, BorderLayout.SOUTH);
        
        
        
        
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
    
    private UserAccount readJsonFile(File file)
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
            
            
            String name = (String) accountJson.get(NAME);
            int age = (int)((long) accountJson.get(AGE));
            char gender = ((String)accountJson.get(GENDER)).charAt(0);
            
            // exerciseListJson contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) accountJson.get("ExerciseList");

            System.out.println(exerciseListJson.get("Bicep"));
            

            

            logName.setText("Name: " + name);
            logAge.setText("Age: " + String.valueOf(age));
            logSex.setText("Sex: " + gender);

            
            account = new UserAccount(name, age, gender);
            
            
    
            JSONArray chosenEx;
            chosenEx = (JSONArray) exerciseListJson.get("Bicep");

            for (int i = 0; i < chosenEx.size(); i++){
                JSONObject jo = (JSONObject) chosenEx.get(i);
                System.out.println(jo.get("reps"));
                

                a.add(jo);
                System.out.println(a);
                //System.out.println(chosenEx.get(i));
            }
            logsList = new JList(a.toArray());
            logsScrollPane = new JScrollPane(logsList);
                 logsScrollPane.setViewportView(logsList);
                 System.out.println(chosenEx.size());
    
            
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
        
       // loadLogsTable(account);
                
        return account;
    }
    

    private void saveFile()
    {
        if(file==null || account==null)
        {
            return;
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
            
     //       exerciseListJson.replace(EXERCISE_LIST[index], exerciseComboBox.getSelectedItem());
      //      accountJson.replace("ExerciseList", exerciseListJson);
            
            writer.printf(accountJson.toJSONString());
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        
        System.out.println(file.getName() + " has successfully saved!");
    }
    
    @Override
    public void onChildUpdate(String name, int age, String gender)
    {
        String fileName = name.replace(" ", "_");        
        File file = new File(fileName+".json");
        
        
        if(file.exists())
        {
            
            JOptionPane.showMessageDialog(null, "Same Name is already existing", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        account = new UserAccount(name, age, gender.charAt(0));
        
        try(PrintWriter pw = new PrintWriter(file);)
        {
            
            JSONObject root = new JSONObject();
            
            root.put(NAME, account.getName());
            root.put(AGE, account.getAge());
            root.put(GENDER, account.getGender());
            
            JSONObject exlist = new JSONObject();
            
            
            root.put("ExerciseList", exlist);
            
            pw.printf(root.toJSONString());
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        
        child=null;
        JOptionPane.showMessageDialog(null, "Your account created successfully!", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
        
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

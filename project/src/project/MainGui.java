
package project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
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
import static javax.swing.SwingConstants.CENTER;
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
// UPDATE : 19-Apr-2015                                       
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
    
    private JSONObject accountJson;
    private JSONObject exerciseListJson;
    
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

    JList logsList;
    DefaultListModel logsListModel = new DefaultListModel();
    
    JComboBox exerciseComboBoxEntry = new JComboBox(EXERCISE_LIST);
    JComboBox exerciseComboBoxLogs = new JComboBox(EXERCISE_LIST);
    
    File file;
    File directory;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// CONSTRUCTOR ////////////////////////////////////////////
    
    public MainGui()
    {
        
        // set the JFrame's layout to BorderLayout
        setLayout(new BorderLayout());

        //////////////////////////////////////// WEST //////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        // create menu Jlist and put MENU_LIST to menu to show the elements in it
        
        JList menu = new JList();
        menu.setListData(MENU_LIST);
        
        // set the menu's border and padding 
        menu.setBorder(new EmptyBorder(0, 5, 200, 5));
        menu.setFixedCellHeight(25);
        
        // add the listener to the menu
        menu.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                cards.show(cardPanel, (String)menu.getSelectedValue());
                if(account!=null && menu.getSelectedValue()==LOGS)
                {
                }
            }
        });
        
        // create the line dividing the menu from the rest of frame
        JSeparator menuSeparator = new JSeparator(SwingConstants.VERTICAL);
        menuSeparator.setPreferredSize(new Dimension(5, 310));
    
        //////////////////////////////////////// CENTER /////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // set the cardPanel's layout to the cardLayout
        cardPanel = new JPanel(cards);
        
        
        ///////////////////////////// HOME //////////////////////////////////////////////////////////////////
        
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
                    account = getFromFile();
                    
                    if(account!=null)
                    {
                        cards.show(cardPanel, LOGS);

                        logName.setText(NAME + " : " + account.getName());
                        logAge.setText(AGE + " : " + account.getAge());
                        logSex.setText(GENDER + " : " + account.getGender());
                    }
                    
                    if(!logsListModel.isEmpty())
                    {
                        logsListModel.clear();
                    }
                    logsList.setModel(logsListModel);
                    
                }
            }              
        });
        
        // add buttons to the card1
        homeCard.add(createBtn);
        homeCard.add(openBtn);
        
        
        ///////////////////////////// ENTRY /////////////////////////////////////////////////////////////////
        
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
        
        // action Listener for the combo box, to load entry page
        exerciseComboBoxEntry.addActionListener(new ActionListener() 
        {
        
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                System.out.println(exerciseComboBoxEntry.getSelectedItem());
                
            }
        });
        
        
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
        
        
        
        entryPanelNorth.add(exerciseComboBoxEntry, BorderLayout.WEST);
        
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
        entryCard.add(entryPanelEast, BorderLayout.EAST);
        entryCard.add(entryPanelSouth, BorderLayout.SOUTH);
        
        
        
        ///////////////////////////// LOGS ////////////////////////////////////////////////////////////////////
        
        // Panels that will go in each section of logsCard page borderlayout
        JPanel logsPanelNorth = new JPanel(new GridLayout(2, 4, 5, 30));
        JPanel logsPanelCenter = new JPanel(new BorderLayout());
        JPanel logsPanelSouth = new JPanel(new GridLayout(1, 3, 5, 0));
        
        logsPanelNorth.setBorder(new EmptyBorder(10,10,0,20));
        logsPanelCenter.setBorder(new EmptyBorder(0,10,0,20));
        logsPanelSouth.setBorder(new EmptyBorder(20,10,20,20));

        logName = new JLabel(NAME + " :");
        logAge = new JLabel (AGE + " :");
        logSex = new JLabel (GENDER + " :");
        
        // components added on top of logs page
        logsPanelNorth.add(logName);
        logsPanelNorth.add(logAge);
        logsPanelNorth.add(logSex);
        logsPanelNorth.add(exerciseComboBoxLogs);
        logsPanelNorth.add(new JLabel("Date", JLabel.RIGHT));
        logsPanelNorth.add(new JLabel("1RM", JLabel.RIGHT));
        logsPanelNorth.add(new JLabel("Calories", JLabel.RIGHT));
        
        exerciseComboBoxLogs.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                System.out.println(exerciseComboBoxLogs.getSelectedItem());
                
                int index = exerciseComboBoxLogs.getSelectedIndex();
                
                if(!logsListModel.isEmpty())
                {
                    logsListModel.clear();
                }

                // put the elements of socialList into the listModel
                for (Log log : account.getExerciseList().get(index))
                {
                    logsListModel.addElement(log);
                }

                // set listModel to show it on the JList
                logsList.setModel(logsListModel);
                
            }
        });
        
        logsList = new JList(logsListModel);
        
        logsList.setCellRenderer(new DefaultListCellRenderer()
        {
            public int getHorizontalAlignment() 
            {
                return CENTER;
            }
        });
        JScrollPane logsScrollPane = new JScrollPane(logsList);
        //logsScrollPane.setPreferredSize(new Dimension(100, 50));
        
        // components added on bottom of logs page
        logsPanelCenter.add(logsScrollPane);
        
        JButton saveBtn = new JButton("Save");
        JButton editBtn = new JButton("Details/Edit");
        JButton deleteBtn = new JButton("Delete");
        
        saveBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                if(saveFile())
                {
                    JOptionPane.showMessageDialog(null, "Change saved successfully!", "Notify", 
                                                JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "[Error] File cannot be saved.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        editBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                
            }
        });
        
        deleteBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                int reponse = JOptionPane.showConfirmDialog(null, "Are you sure, jackass?", 
                        "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                // only if the user click yes
                if(reponse == JOptionPane.YES_OPTION)
                {
                    int index = logsList.getSelectedIndex();
                    deleteLog(index);
                }
                
            }
        });
        
        logsPanelSouth.add(saveBtn);
        logsPanelSouth.add(editBtn);
        logsPanelSouth.add(deleteBtn);

        
        // logs page subsections added to main logs panel
        logsCard.add(logsPanelNorth, BorderLayout.NORTH);
        logsCard.add(logsPanelCenter, BorderLayout.CENTER);
        logsCard.add(logsPanelSouth, BorderLayout.SOUTH);
        
        
        
        
        ///////////////////////////// GRAPHS ///////////////////////////////////////////////////////////////
        
        
        
        // code for the layout for the graphs section 
        
        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        
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
    
    private UserAccount getFromFile()
    {    
        UserAccount acc = null;
        try(Scanner reader = new Scanner(file);) 
        {
            StringBuilder jsonBuilder = new StringBuilder(); 
            
            // read a file using scanner and store the data to jsonBuilder
            while (reader.hasNextLine()) 
            {
                jsonBuilder.append(reader.nextLine());
            }
            
            String jsonString = jsonBuilder.toString();
            
            JSONParser parser = new JSONParser();
            
            // root JsonObject represents one instance of UserAccount 
            accountJson = (JSONObject) parser.parse(jsonString);
            
            
            String name = (String) accountJson.get(NAME);
            int age = (int)((long) accountJson.get(AGE));
            char gender = ((String)accountJson.get(GENDER)).charAt(0);
            
            acc = new UserAccount(name, age, gender);
            
            // exerciseListJson contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) accountJson.get("ExerciseList");
            
            for (int i = 0; i < EXERCISE_LIST.length; i++)
            {
                JSONArray jsonarray = (JSONArray)exerciseListJson.get(EXERCISE_LIST[i]);
                
                if(!jsonarray.isEmpty())
                {
                    for (int j = 0; j < jsonarray.size(); j++)
                    {
                            JSONObject obj = (JSONObject)jsonarray.get(j);

                            String date = (String) obj.get("date");
                            int weight = (int)((long) obj.get("weight"));
                            int reps = (int)((long) obj.get("reps"));
                            int calories = (int)((long) obj.get("calories"));

                            Log log = new Log(date, weight, reps, calories);

                            acc.getExerciseList().get(i).add(log);
                    }
                }
            }
            
                        
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println("Not existing file.");
        }
        catch(ParseException ex)
        {
            System.out.println("The file cannot be opend.");
        }
        return acc;
    }
    
    private boolean deleteLog(int index)
    {
        int chosenExercise = exerciseComboBoxLogs.getSelectedIndex();
        account.getExerciseList().get(chosenExercise).remove(index);
        JSONArray jsonarray = (JSONArray) exerciseListJson.get(EXERCISE_LIST[chosenExercise]);
        jsonarray.remove(index);
        exerciseListJson.replace(EXERCISE_LIST[chosenExercise], jsonarray);
        accountJson.replace("ExerciseList", exerciseListJson);

        // remove the clicked item from the list
        logsListModel.removeElement(logsList.getSelectedValue());
        return true;    
    }
    
    private boolean saveFile()
    {
        if(file==null || account==null)
        {
            return false;
        }
        
        try(PrintWriter writer = new PrintWriter(file);)
        {
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
        
        for(int i = 0; i < EXERCISE_LIST.length; i++)
        {
            exerciseListJson.put(EXERCISE_LIST[i], new JSONArray());
        }
        
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

        if(account!=null)
        {
            cards.show(cardPanel, LOGS);

            logName.setText(NAME + " : " + account.getName());
            logAge.setText(AGE + " : " + account.getAge());
            logSex.setText(GENDER + " : " + account.getGender());
        }

        if(!logsListModel.isEmpty())
        {
            logsListModel.clear();
        }
        logsList.setModel(logsListModel);
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

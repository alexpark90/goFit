
package project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi
 *   Date: Apr. 15. 2015
 * 
 *   Honor: I have completed this assignment on my own.
 *          In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: MainGui.java 
 *   
 *   Description: This is the main GUI class of this application.
 *              Basically, the user interface is made of 4 cards and each card has 2 section, west and center.
 *              West section is static look for all 4 cards and only center section has different look for each card.
 *              This class also contains subGui, which is used for creating new user account.
 *                
 *
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */

public class MainGui extends JFrame implements SubGui.TransferData, ValidateInput
{
    ///////////////////////////////// FIELDS //////////////////////////////////
    
    // constants for card layout.
    final static String HOME = "HOME";
    final static String ENTRY = "ENTRY";
    final static String LOGS = "LOGS";
    final static String GRAPHS = "GRAPHS";
    final static String[] MENU_LIST = {HOME, ENTRY, LOGS, GRAPHS};
    
    // constants to label on GUI
    final static String NAME = "Name";
    final static String AGE = "Age";
    final static String GENDER = "Gender";
    final static String[] EXERCISE_LIST = {"Bicep", "Tricep", "DeadLift", "BackExtension", "Squat", "LegPress", "BenchPress",};
    
    // variables to hold the user information
    // and handle the JOSN file.
    private UserAccount account;
    private JSONObject accountJson;
    private JSONObject exerciseListJson;
    
    // to hold the SubGui 
    SubGui child;
        
    // variables for card layouts
    CardLayout cards = new CardLayout();
    JPanel centerPanel;
    JPanel homeCard = new JPanel(new BorderLayout());
    JPanel entryCard = new JPanel(new BorderLayout());
    JPanel logsCard = new JPanel(new BorderLayout());
    JPanel graphsCard = new JPanel();
    
    
    // variables to be used in entry section
    JTextField dateField = new JTextField(10);
    JTextField weightField = new JTextField(10);
    JTextField repsField = new JTextField(10);
    JTextField caloriesField = new JTextField(10);
    
    JLabel[] rmLabels = new JLabel[11];
    
    // variables to be used in logs section
    JLabel logName;
    JLabel logAge;
    JLabel logSex;

    JList logsList;
    DefaultListModel logsListModel = new DefaultListModel();
    
    JComboBox exerciseComboBoxEntry = new JComboBox(EXERCISE_LIST);
    JComboBox exerciseComboBoxLogs = new JComboBox(EXERCISE_LIST);
    
    // to handle I/O
    File file;
    File directory;
    
    // to control the editting mode in entry section
    boolean editMode = false;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// CONSTRUCTOR ///////////////////////////////////////////////////
    
    public MainGui()
    {
        //////////////////////////////////////// WEST /////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////

        // create menu JList and put MENU_LIST to menu to show the elements in it       
        JList menu = new JList();
        menu.setListData(MENU_LIST);
        
        // set the menu's border and cells size and allighnment
        menu.setBorder(new EmptyBorder(10, 0, 200, 0));
        menu.setFixedCellHeight(30);
        menu.setFixedCellWidth(80);
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)menu.getCellRenderer();  
        renderer.setHorizontalAlignment(JLabel.CENTER); 
        
        // add the listener to the menu
        menu.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                // only if the account is set up and not in editMode
                if(account!=null && !editMode)
                {
                    cards.show(centerPanel, (String)menu.getSelectedValue());
                }
            }
        });
        
        // create the line dividing the menu from the rest of frame
        JSeparator menuSeparator = new JSeparator(SwingConstants.VERTICAL);
        menuSeparator.setPreferredSize(new Dimension(5, 310));
    
        //////////////////////////////////////// CENTER /////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////
        
        // set the cardPanel's layout to the cardLayout
        centerPanel = new JPanel(cards);
        
        
        //////////////////////////////// HOME card //////////////////////////////
        
        // create the image icon and apply it to JLabel
        JLabel imageLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("image/logo.png");
        imageLabel.setIcon(logoIcon);
        
        // create panel and apply JLabel containing logo to panel
        JPanel logoPanel = new JPanel();
        logoPanel.add(imageLabel);
        
        // create buttons to create or open an user account
        JButton createBtn = new JButton("Create");
        JButton openBtn = new JButton("Open");
        
        JPanel homeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        homeButtonPanel.add(createBtn);
        homeButtonPanel.add(openBtn);
          
        // add the listener to the createBtn
        createBtn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                // open SubGui window to take user inputs
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
                    try
                    {
                        // read the file, get data from it,
                        //and set the account using getFromFile method
                        account = getAccountFromFile();
                        
                        // move to LOGS tap
                        cards.show(centerPanel, LOGS);
                        menu.setSelectedValue(LOGS, false);
                        
                        // show account owner's personal information
                        logName.setText(NAME + " : " + account.getName());
                        logAge.setText(AGE + " : " + account.getAge());
                        logSex.setText(GENDER + " : " + account.getGender());

                        // set logsList and logsListModel to show loaded data
                        if(!logsListModel.isEmpty())
                        {
                            logsListModel.clear();
                        }
                        logsList.setModel(logsListModel);
                        exerciseComboBoxLogs.setSelectedIndex(0);
                    }
                    catch(FileNotFoundException | ParseException | InvalidInputException exx)
                    {
                        // show the error message
                        JOptionPane.showMessageDialog(null, "The File is incorrect format or damaged.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }              
        });
        
        // add panels to the card panel
        homeCard.add(logoPanel, BorderLayout.CENTER);
        homeCard.add(homeButtonPanel, BorderLayout.SOUTH);
        
        
        ////////////////////////////// ENTRY card /////////////////////////////////
        
        JPanel entryPanelNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        entryPanelNorth.add(exerciseComboBoxEntry);
        
        JPanel entryPanelWest = new JPanel(new GridLayout(5, 1));
        
        JPanel entryPanelCenter = new JPanel(new GridLayout(11, 5));
        entryPanelCenter.setBorder(new EmptyBorder(0, 50, 0, 0));
        
        JPanel entryPanelSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        
        // for rm result displays
        for(int i = 0; i < rmLabels.length-1; i++)
        {
            rmLabels[i] = new JLabel();
            entryPanelCenter.add(new JLabel(" " +(5 * i + 50) + "% 1RM:"));
            entryPanelCenter.add(rmLabels[i]);
        }
        
        rmLabels[10] = new JLabel();
        entryPanelCenter.add(new JLabel("1Rep Max: "));
        entryPanelCenter.add(rmLabels[10]);
       
        // create the rm calculate button
        JButton calculateButton = new JButton("Calculate");
        
        // set the listener for calculateButton
        calculateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                try
                {
                    validateInput();
                    int weight = Integer.parseInt(weightField.getText());
                    int reps = Integer.parseInt(repsField.getText());

                    RM rm = new RM(weight, reps);

                    for (int i = 0; i < rmLabels.length-1; i++)
                    {
                        rmLabels[i].setText(String.valueOf(rm.getRMatPercentages(5 * i + 50)));
                    }
                    rmLabels[10].setText(rm.toString());    
                } 
                catch (InvalidInputException eex)
                {
                    JOptionPane.showMessageDialog(null, eex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
                catch(NumberFormatException eex)
                {
                    JOptionPane.showMessageDialog(null, "Weights, Reps, and Calories must be positive Integer.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
            }
        });
        
        // set west section using 5 FlowLayouts
        JPanel entryPanelWest1= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel entryPanelWest2= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel entryPanelWest3= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel entryPanelWest4= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel entryPanelWest5= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        entryPanelWest1.add(new JLabel("Date: "));
        entryPanelWest1.add(dateField);
        entryPanelWest2.add(new JLabel("Weight Lifted: "));
        entryPanelWest2.add(weightField);
        entryPanelWest3.add(new JLabel("Repetitions: "));
        entryPanelWest3.add(repsField);
        entryPanelWest4.add(new JLabel("Calories burned: "));
        entryPanelWest4.add(caloriesField);
        entryPanelWest5.add(calculateButton);
        
        // set the date field's value to todays date in advance.
        Date date = new Date();  
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM-dd-yyyy");
        dateField.setText(formatDate.format(date));
        
        entryPanelWest.add(entryPanelWest1);
        entryPanelWest.add(entryPanelWest2);
        entryPanelWest.add(entryPanelWest3);
        entryPanelWest.add(entryPanelWest4);
        entryPanelWest.add(entryPanelWest5);
        
        JButton clearBtn = new JButton("Clear");
        
        clearBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                reset();
            }
        });
        
        // create a button for adding new log
        JButton addNewBtn = new JButton("Add New");
        
        // set the listener for addNewButton
        addNewBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                // if the user desired to add in bicep, so the comboBox was not touched
                if(exerciseComboBoxEntry.getSelectedIndex()==-1)
                {
                    exerciseComboBoxEntry.setSelectedIndex(0);
                }             
                try
                {
                    validateInput();
                    
                    int weight = Integer.parseInt(weightField.getText());
                    int reps = Integer.parseInt(repsField.getText()); 
                    int calories = Integer.parseInt(caloriesField.getText());
                    
                    addNewLog(dateField.getText(), weight, reps, calories);

                    JOptionPane.showMessageDialog(null, "New log added Successfully.", 
                            "Added", JOptionPane.PLAIN_MESSAGE);

                    cards.show(centerPanel, LOGS);
                    menu.setSelectedValue(LOGS, false);
                    
                    exerciseComboBoxLogs.setSelectedIndex(exerciseComboBoxEntry.getSelectedIndex());
                    
                }
                catch(InvalidInputException eex)
                {
                    JOptionPane.showMessageDialog(null, eex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
                catch(NumberFormatException eex)
                {
                    JOptionPane.showMessageDialog(null, "Weights, Reps, and Calories must be positive Integer.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
            }
        });
        
        // create a button for editting the selected log.
        JButton editRecordBtn = new JButton("Edit");
        // Unless the log to be edit is passed from LOGS, the button is disabled to click.
        editRecordBtn.setEnabled(false);
        
        // create a button for canceling editing mode.
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setEnabled(false);
        
        // set the listener for editRecordButton
        editRecordBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                try
                {
                    validateInput();
                    editLog(dateField.getText(), Integer.parseInt(weightField.getText()),
                            Integer.parseInt(repsField.getText()), Integer.parseInt(caloriesField.getText()));

                    JOptionPane.showMessageDialog(null, "The selected log edited Successfully.", 
                            "Edit", JOptionPane.PLAIN_MESSAGE);

                    addNewBtn.setEnabled(true);
                    exerciseComboBoxEntry.setEnabled(true);
                    editRecordBtn.setEnabled(false);
                    cancelBtn.setEnabled(false);
                    cards.show(centerPanel, LOGS);
                    menu.setSelectedValue(LOGS, false);
                    editMode = false;
                }  
                catch(InvalidInputException eex)
                {
                    JOptionPane.showMessageDialog(null, eex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
                catch(NumberFormatException eex)
                {
                    JOptionPane.showMessageDialog(null, "Weights, Reps, and Calories must be positive Integer.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    reset();
                }
            }
        });
        
        cancelBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                int reponse = JOptionPane.showConfirmDialog(null, "Are you sure to cancel editing?", 
                            "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                // only if the user click yes
                if(reponse == JOptionPane.YES_OPTION)
                {
                    editMode = false;
                    editRecordBtn.setEnabled(false);
                    addNewBtn.setEnabled(true);
                    exerciseComboBoxEntry.setEnabled(true);
                    reset();
                }
            }
        });
        
        entryPanelSouth.add(clearBtn);
        entryPanelSouth.add(addNewBtn);
        entryPanelSouth.add(editRecordBtn);
        entryPanelSouth.add(cancelBtn);
        
        entryCard.add(entryPanelNorth, BorderLayout.NORTH);
        entryCard.add(entryPanelWest, BorderLayout.WEST);
        entryCard.add(entryPanelCenter, BorderLayout.CENTER);
        entryCard.add(entryPanelSouth, BorderLayout.SOUTH);
        
        ///////////////////////////// LOGS card /////////////////////////////////////
        
        // Panels that will go in each section of logsCard page borderlayout
        JPanel logsPanelNorth = new JPanel(new GridLayout(2, 4, 5, 5));
        JPanel logsPanelCenter = new JPanel();
        JPanel logsPanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        logsPanelNorth.setBorder(new EmptyBorder(10,10,0,10));
        logsPanelCenter.setBorder(new EmptyBorder(0,10,0,10));
        logsPanelSouth.setBorder(new EmptyBorder(10,10,20,10));

        logName = new JLabel(NAME + " :");
        logAge = new JLabel (AGE + " :");
        logSex = new JLabel (GENDER + " :");
        
        // components added on top of logs page
        logsPanelNorth.add(logName);
        logsPanelNorth.add(logAge);
        logsPanelNorth.add(logSex);
        logsPanelNorth.add(exerciseComboBoxLogs);
        logsPanelNorth.add(new JLabel(" Date", JLabel.RIGHT));
        logsPanelNorth.add(new JLabel(" 1RM", JLabel.RIGHT));
        logsPanelNorth.add(new JLabel("Calories", JLabel.RIGHT));
        
        
        logsList = new JList(logsListModel);
        JScrollPane logsScrollPane = new JScrollPane(logsList);
        logsScrollPane.setPreferredSize(new Dimension(400, 180));
        
        // component added on center of logs page
        logsPanelCenter.add(logsScrollPane);
        
        exerciseComboBoxLogs.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {   
                int index = exerciseComboBoxLogs.getSelectedIndex();
                
                if(!logsListModel.isEmpty())
                {
                    logsListModel.clear();
                }

                // put the elements of selected exercise into the listModel
                for (Log log : account.getExerciseList().get(index))
                {
                    logsListModel.addElement(log);
                }

                // set listModel to show it on the JList
                logsList.setModel(logsListModel);              
            }
        });
             
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
                    JOptionPane.showMessageDialog(null, "Changes saved successfully!", "Notify", 
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
                int exerciseIndex = exerciseComboBoxLogs.getSelectedIndex();
                int logIndex = logsList.getSelectedIndex();
                
                if (logIndex > -1)
                {
                    editMode = true;
                    // get the log specified by the exercise combo index + the list selection index
                    Log selectedLog = account.getExerciseList().get(exerciseIndex).get(logIndex);
                    // input entries from the log into fields on the entry page
                    dateField.setText(selectedLog.getDate());
                    weightField.setText(String.valueOf(selectedLog.getWeight()));
                    repsField.setText(String.valueOf(selectedLog.getReps()));
                    caloriesField.setText(String.valueOf(selectedLog.getCalories()));
                    
                    // change the combo box on the entry page to match the combo selection from logs page
                    exerciseComboBoxEntry.setSelectedIndex(exerciseIndex);
                    // switch to the entry page
                    cards.show(centerPanel, ENTRY);
                    menu.setSelectedValue(ENTRY, false);
                    // set the buttons in the entry page 
                    editRecordBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);
                    addNewBtn.setEnabled(false);
                    exerciseComboBoxEntry.setEnabled(false);
                }
            }
        });
        
        deleteBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ex)
            {
                int index = logsList.getSelectedIndex();
                if(index!=-1)
                {
                    int reponse = JOptionPane.showConfirmDialog(null, "Are you sure to delete it, jackass?", 
                            "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // only if the user click yes
                    if(reponse == JOptionPane.YES_OPTION)
                    {
                        deleteLog(index);
                    }
                }
            }
        });
        
        // components added on top of logs page
        logsPanelSouth.add(saveBtn);
        logsPanelSouth.add(editBtn);
        logsPanelSouth.add(deleteBtn);

        // logs page subsections added to main logs panel
        logsCard.add(logsPanelNorth, BorderLayout.NORTH);
        logsCard.add(logsPanelCenter, BorderLayout.CENTER);
        logsCard.add(logsPanelSouth, BorderLayout.SOUTH);
        
        
        ///////////////////////////// GRAPHS /////////////////////////////////////
        
        
        graphsCard.add(new JLabel("This section will be updated..."));
        
        
        
        //////////////////////////////////////////////////////////////////////////
        
        
        JPanel menuPanel = new JPanel(new BorderLayout());
        
        // add components to the menuPanel
        menuPanel.add(menu, BorderLayout.WEST);
        menuPanel.add(menuSeparator, BorderLayout.CENTER);
        
        // add the menupanel to the west area
        add(menuPanel, BorderLayout.WEST);
        
        // add each card to the cardPanel
        centerPanel.add(homeCard, HOME);
        centerPanel.add(entryCard, ENTRY);
        centerPanel.add(logsCard, LOGS);
        centerPanel.add(graphsCard, GRAPHS);
        
        // show HOME card when the app start 
        cards.show(centerPanel, HOME);
        
        // add the cardpanel to the center area
        add(centerPanel, BorderLayout.CENTER);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////HELPER METHODS/////////////////////////////////
    /**
     * openFile method.
     * It shows the file dialog for user to choose the file.
     * 
     * @param
     * @return
     */
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
    
    /**
     * getAccountFromFile method.
     * It reads the json file and store the data to UserAccount.
     * 
     * @param
     * @return an instance of UserAccount that holds the data extracted from Json.
     */
    private UserAccount getAccountFromFile() throws FileNotFoundException, ParseException, InvalidInputException
    {    
        UserAccount acc;
        
        Scanner reader = new Scanner(file);

        StringBuilder jsonBuilder = new StringBuilder(); 

        // read a file using scanner and store the data to jsonBuilder
        while (reader.hasNextLine()) 
        {
            jsonBuilder.append(reader.nextLine());
        }

        String jsonString = jsonBuilder.toString();

        JSONParser parser = new JSONParser();

        // JsonObject representing one instance of UserAccount 
        accountJson = (JSONObject) parser.parse(jsonString);

        String name = (String) accountJson.get(NAME);
        int age = (int)((long) accountJson.get(AGE));
        char gender = ((String)accountJson.get(GENDER)).charAt(0);

        // create an account using data from JSONObject
        acc = new UserAccount(name, age, gender);

        // exerciseListJson contains an array of different kinds of exercise
        exerciseListJson = (JSONObject) accountJson.get("ExerciseList");

        // get each exercise insise exerciseListJson
        for (int i = 0; i < EXERCISE_LIST.length; i++)
        {
            JSONArray jsonarray = (JSONArray)exerciseListJson.get(EXERCISE_LIST[i]);

            if(!jsonarray.isEmpty())
            {
                // get each log inside the exercise indicated by the index i
                for (int j = 0; j < jsonarray.size(); j++)
                {
                    JSONObject obj = (JSONObject)jsonarray.get(j);

                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));

                    // create a log using data from JSONObject
                    Log log = new Log(date, weight, reps, calories);

                    // add the created log to the exercise indicated by the index i
                    acc.getExerciseList().get(i).add(log);
                }
            }
        }                       
        return acc;
    }
    
    /**
     * deleteLog method.
     * It deletes the log from the exercise list
     * and also update the Json file.
     * 
     * @param index - the index of the log to be deleted.
     * @return boolean result of deleting process.
     */
    private boolean deleteLog(int index)
    {
        if(index==-1)
        {
            return false;
        }
        // get the index of currently selected exericse
        int chosenExercise = exerciseComboBoxLogs.getSelectedIndex();
        
        // remove the given index's log
        account.getExerciseList().get(chosenExercise).remove(index);
        JSONArray jsonarray = (JSONArray) exerciseListJson.get(EXERCISE_LIST[chosenExercise]);
        jsonarray.remove(index);
        
        // update JSON
        exerciseListJson.replace(EXERCISE_LIST[chosenExercise], jsonarray);
        accountJson.replace("ExerciseList", exerciseListJson);

        // remove the given index's log from the list
        logsListModel.removeElement(logsList.getSelectedValue());
        return true;
    }
    
    /**
     * addNewLog method.
     * It adds new log to the list to show the user
     * and also update the Json file.
     * 
     * @param date - date of the exercise or this log
     *        weight - the weight lifted
     *        reps - the times of lifting
     *        calories - calories to worked out
     * @return
     */
    private void addNewLog(String date, int weight, int reps, int calories) throws NumberFormatException, InvalidInputException
    {
        Log log = new Log(date, weight, reps, calories);
        
        int chosenExercise = exerciseComboBoxEntry.getSelectedIndex();
        account.getExerciseList().get(chosenExercise).add(log);
        
        // create new JSONObject to add into exerciseList
        JSONObject obj = new JSONObject();     
        obj.put("date", date);
        obj.put("weight", weight);
        obj.put("reps", reps);
        obj.put("calories", calories);
        
        JSONArray jsonarray = (JSONArray) exerciseListJson.get(EXERCISE_LIST[chosenExercise]);
        jsonarray.add(obj);
        
        // update JSON
        exerciseListJson.replace(EXERCISE_LIST[chosenExercise], jsonarray);
        accountJson.replace("ExerciseList", exerciseListJson);  
    }

    /**
     * editLog method.
     * It modifies the log from the list
     * and also update the Json file.
     * 
     * @param date - date of the exercise or this log
     *        weight - the weight lifted
     *        reps - the times of lifting
     *        calories - calories to worked out
     * @return
     */
    private void editLog(String date, int weight, int reps, int calories) throws NumberFormatException, InvalidInputException
    {
        int chosenExercise = exerciseComboBoxEntry.getSelectedIndex();
        int logIndex = logsList.getSelectedIndex();
        
        Log log = new Log(date, weight, reps, calories);
        account.getExerciseList().get(chosenExercise).set(logIndex, log);
        
        JSONObject obj = new JSONObject();
        obj.put("date", date);
        obj.put("weight", weight);
        obj.put("reps", reps);
        obj.put("calories", calories);
        
        JSONArray jsonarray = (JSONArray) exerciseListJson.get(EXERCISE_LIST[chosenExercise]);
        jsonarray.remove(logIndex);
        jsonarray.add(logIndex, obj);
        exerciseListJson.replace(EXERCISE_LIST[chosenExercise], jsonarray);
        accountJson.replace("ExerciseList", exerciseListJson);

        logsListModel.remove(logIndex);
        logsListModel.addElement(log);
    }
    
    /**
     * saveFile method.
     * It transfer the data that is stored in an instance of UserAccount 
     * to JsonFile and save it.
     * 
     * @param
     * @return
     */
    private boolean saveFile()
    {
        // if the user try to save before open a file or create an account
        if(file==null || account==null)
        {
            return false;
        }
        
        try(PrintWriter writer = new PrintWriter(file);)
        {
            writer.printf(accountJson.toJSONString());
        } 
        catch (FileNotFoundException ignoreMe)
        {
            return false;
        }
        return true;
    }
    
    // override onChildUpdate method of TrasferData interface in SubGui
    @Override
    public void onChildUpdate(String name, int age, String gender)
    {
        // set the new file's name
        String fileName = name.replace(" ", "_");        
        file = new File(fileName+".json");
        
        // if the same named file already exists
        if(file.exists())
        {
            JOptionPane.showMessageDialog(null, "Same Name is already existing", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // set new account
        account = new UserAccount(name, age, gender.charAt(0));
        
        // set Json to write to the file 
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
        
        // if saving data to a file succeed, 
        if(saveFile())
        {
            JOptionPane.showMessageDialog(null, "Your account created successfully!", "Notify", 
                    JOptionPane.PLAIN_MESSAGE);
            child=null;
            
            // show account owner's personal information
            logName.setText(NAME + " : " + account.getName());
            logAge.setText(AGE + " : " + account.getAge());
            logSex.setText(GENDER + " : " + account.getGender());

            // set logsList and logsListModel to show loaded data
            if(!logsListModel.isEmpty())
            {
                logsListModel.clear();
            }
            logsList.setModel(logsListModel);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error occured in the process of creating an account", "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // override validateInput method of ValidateInput interface
    @Override
    public void validateInput() throws InvalidInputException, NumberFormatException
    {
        if(dateField.getText().equals("") || !dateField.getText().matches("^[A-z]{3}-[0-9]{2}-[0-9]{4}$"))
        {
            throw new InvalidInputException("Date must filled following the format. ex) Apr-22-2015 ");
        }

        if(weightField.getText().equals("") || repsField.getText().equals("") || caloriesField.getText().equals(""))
        {
            throw new InvalidInputException("All fields must be filled.");
        }
        else
        {
            int weight = Integer.parseInt(weightField.getText()); 
            int reps = Integer.parseInt(repsField.getText());
            int calories = Integer.parseInt(caloriesField.getText());

            if(weight < 0 || reps < 0 || calories < 0)
            {
                throw new InvalidInputException("Weight lifted, Reps, and Calories must be positive Integer.");
            }
        }
    }
    
    // override reset method of ValidateInput interface
    @Override
    public void reset()
    {
        if(editMode)
        {
            int exerciseIndex = exerciseComboBoxEntry.getSelectedIndex();
            int logIndex = logsList.getSelectedIndex();
            dateField.setText(account.getExerciseList().get(exerciseIndex).get(logIndex).getDate());
            weightField.setText(String.valueOf(account.getExerciseList().get(exerciseIndex).get(logIndex).getWeight())); 
            repsField.setText(String.valueOf(account.getExerciseList().get(exerciseIndex).get(logIndex).getReps()));;
            caloriesField.setText(String.valueOf(account.getExerciseList().get(exerciseIndex).get(logIndex).getCalories()));
        }
        else
        {
            dateField.setText("");
            weightField.setText(""); 
            repsField.setText("");
            caloriesField.setText("");
            for(int i = 0; i < rmLabels.length; i++)
            {
                rmLabels[i].setText("");
            }
        }
    } 
}

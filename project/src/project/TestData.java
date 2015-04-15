
package project;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 14-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

/*
* I am doing hardcoding here just to see the result on the console.
* These methods' return type and parameters will be changed inside MainGui class.
*/


public class TestData 
{
    
    final static String NAME = "Name";
    final static String AGE = "Age";
    final static String GENDER = "Gender";
    
    static File file;
    
    static JSONObject rootJson;
    static JSONObject exerciseListJson;
    static JSONArray bicepJson;
    
    static UserAccount account;
    static ArrayList<Exercise> exerciseList;
    static Exercise bicep = new Exercise("Bicep");
    
    public static void main(String[] args)
    {
           openFile();
           deleteLog(2);
           
           System.out.println(bicep);
           
           addLog("2015-Apr-14", 100, 12, 300);
           
           System.out.println(bicep);
           
           saveFile();
           
    }
    
    private static void openFile()
    {
        
        file = new File("user.json");
        
        try 
        {
            Scanner fileInput = new Scanner(file);
            
            StringBuilder jsonBuilder = new StringBuilder(); 
            
            // read a file using scanner and store the data to jsonBuilder
            while (fileInput.hasNextLine()) 
            {
                jsonBuilder.append(fileInput.nextLine());
            }
            
            String jsonString = jsonBuilder.toString();
            
            JSONParser parser = new JSONParser();
            
            // root represents one instance of UserAccount 
            rootJson = (JSONObject) parser.parse(jsonString);
            
            
            String name = (String) rootJson.get(NAME);
            int age = (int)((long) rootJson.get(AGE));
            char gender = ((String)rootJson.get(GENDER)).charAt(0);
            
            account = new UserAccount(name, age, gender);
            
            System.out.println(account);
            
            // exerciseListJson contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) rootJson.get("ExerciseList");
            
            
            // bicepLiftJson contains an array of daily logs
            bicepJson = (JSONArray)exerciseListJson.get("Bicep");
                        
            for (int i = 0; i < bicepJson.size(); i++)
            {
                if(!bicepJson.isEmpty())
                {
                    JSONObject obj = (JSONObject)bicepJson.get(i);

                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));


                    Log log = new Log(date, weight, reps, calories);

                    bicep.add(log);

                    System.out.println(log + "\n");

                }
            }
            
            //exerciseList.add(bicepLift);
            //account.setExerciseList(exerciseList);
            
            
            JSONArray tricepJson = (JSONArray)exerciseListJson.get("Tricep");
            
            if(!tricepJson.isEmpty())
            {
                for (int i = 0; i < tricepJson.size(); i++)
                {
                    JSONObject obj = (JSONObject)tricepJson.get(i);

                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));


                    Log log = new Log(date, weight, reps, calories);

                    System.out.println(log + "\n");

                }
            }
            
            
            JSONArray squatJson = (JSONArray)exerciseListJson.get("Squat");
            
            if(!squatJson.isEmpty())
            {
                for (int i = 0; i < squatJson.size(); i++)
                {
                    JSONObject obj = (JSONObject)squatJson.get(i);
                
                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));


                    Log log = new Log(date, weight, reps, calories);

                    System.out.println(log + "\n");

                }
            }
            
            JSONArray deadLiftJson = (JSONArray)exerciseListJson.get("DeadLift");
            
            if(!deadLiftJson.isEmpty())
            {
                for (int i = 0; i < deadLiftJson.size(); i++)
                {
                    JSONObject obj = (JSONObject)deadLiftJson.get(i);

                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));


                    Log log = new Log(date, weight, reps, calories);

                    System.out.println(log + "\n");

                }
            }
            
            JSONArray benchPressJson = (JSONArray)exerciseListJson.get("BenchPress");
            
            if(!benchPressJson.isEmpty())
            {
                for (int i = 0; i < benchPressJson.size(); i++)
                {
                    JSONObject obj = (JSONObject)benchPressJson.get(i);

                    String date = (String) obj.get("date");
                    int weight = (int)((long) obj.get("weight"));
                    int reps = (int)((long) obj.get("reps"));
                    int calories = (int)((long) obj.get("calories"));


                    Log log = new Log(date, weight, reps, calories);

                    System.out.println(log + "\n");

                }
            }     
        }
        catch (FileNotFoundException | ParseException ex) 
        {
            System.out.println(ex.toString());
        }
    }
    
    private static void deleteLog(int index)
    {
        bicep.remove(index);
        bicepJson.remove(index);
    }
    
    private static void addLog(String date, int weight, int reps, int calories)
    {
        Log log = new Log(date, weight, reps, calories);
        bicep.add(log);
        bicepJson.add(log);
    }
    
    
    private static void addLog(String date, int calories)
    {
        Log log = new Log(date, calories);
        bicep.add(log);
        //bicepLiftJson.add(log);
    }
    
    private static void addLog(String date, int weight, int reps)
    {
        Log log = new Log(date, weight, reps);
        bicep.add(log);
        //bicepLiftJson.add(log);
    }
    
    private static void saveFile()
    {
        File file = new File("changeTest.json");
        
        try(PrintWriter pw = new PrintWriter(file);)
        {
            
            JSONObject root = new JSONObject();
            
            root.put(NAME, account.getName());
            root.put(AGE, account.getAge());
            root.put(GENDER, account.getGender());
            
            JSONObject exlist = new JSONObject();
            
            exlist.put("BicepLift", bicepJson);
            //exlist.put("tricepLift", tricepLiftJson);
            //exlist.put("squat", squatJson);
            
            
            root.put("ExerciseList", exlist);
            
            pw.printf(root.toJSONString());
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        
        System.out.println(file.getName() + " has successfully saved!");        
    }    
}

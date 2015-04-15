
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
    

    static File file;
    
    static JSONObject rootJson;
    static JSONObject exerciseListJson;
    static JSONArray bicepLiftJson;
    
    static UserAccount account;
    static ArrayList<Exercise> exerciseList;
    static Exercise bicepLift = new Exercise("bicepLift");
    
    public static void main(String[] args)
    {
           openFile();
           deleteLog(2);
           
           System.out.println(bicepLift);
           
           addLog("2015-Apr-14", 100, 12, 300);
           
           System.out.println(bicepLift);
           
           saveFile();
           
    }
    
    private static void openFile()
    {
        
        file = new File("user.txt");
        
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
            
            
            String name = (String) rootJson.get("name");
            int age = (int)((long) rootJson.get("age"));
            char gender = ((String)rootJson.get("gender")).charAt(0);
            
            account = new UserAccount(name, age, gender);
            
            System.out.println(account);
            
            // exerciseListJson contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) rootJson.get("exerciseList");
            
            
            // bicepLiftJson contains an array of daily logs
            bicepLiftJson = (JSONArray)exerciseListJson.get("bicepLift");
            
            
            for (int i = 0; i < bicepLiftJson.size(); i++)
            {
                JSONObject obj = (JSONObject)bicepLiftJson.get(i);
                
                String date = (String) obj.get("date");
                int weight = (int)((long) obj.get("weight"));
                int reps = (int)((long) obj.get("reps"));
                int calories = (int)((long) obj.get("calories"));
                
                
                Log log = new Log(date, weight, reps, calories);
                
                bicepLift.add(log);
                
                System.out.println(log + "\n");
                
            }
            
            //exerciseList.add(bicepLift);
            //account.setExerciseList(exerciseList);
            
//            
//            JSONArray tricepLift = (JSONArray)exerciseListJson.get("tricepLift");
//            
//            for (int i = 0; i < tricepLift.size(); i++)
//            {
//                JSONObject obj = (JSONObject)tricepLift.get(i);
//                
//                String date = (String) obj.get("date");
//                int weight = (int)((long) obj.get("weight"));
//                int reps = (int)((long) obj.get("reps"));
//                int calories = (int)((long) obj.get("calories"));
//                
//                
//                Log log = new Log(date, weight, reps, calories);
//                
//                System.out.println(log + "\n");
//                
//            }
//            
            
            
//            JSONArray squat = (JSONArray)exerciseList.get("squat");
//            
//            for (int i = 0; i < squat.size(); i++)
//            {
//                JSONObject obj = (JSONObject)squat.get(i);
//                
//                String date = (String) obj.get("date");
//                int weight = (int)((long) obj.get("weight"));
//                int reps = (int)((long) obj.get("reps"));
//                int calories = (int)((long) obj.get("calories"));
//                
//                
//                Log log = new Log(date, weight, reps, calories);
//                
//                System.out.println(log + "\n");
//                
//            }
            
            
//            JSONArray deadLift = (JSONArray)exerciseListJson.get("deadLift");
//            
//            for (int i = 0; i < deadLift.size(); i++)
//            {
//                JSONObject obj = (JSONObject)deadLift.get(i);
//                
//                String date = (String) obj.get("date");
//                int weight = (int)((long) obj.get("weight"));
//                int reps = (int)((long) obj.get("reps"));
//                int calories = (int)((long) obj.get("calories"));
//                
//                
//                Log log = new Log(date, weight, reps, calories);
//                
//                System.out.println(log + "\n");
//                
//            }
//            
//            
//            JSONArray benchPress = (JSONArray)exerciseList.get("benchPress");
//            
//            for (int i = 0; i < benchPress.size(); i++)
//            {
//                JSONObject obj = (JSONObject)benchPress.get(i);
//                
//                String date = (String) obj.get("date");
//                int weight = (int)((long) obj.get("weight"));
//                int reps = (int)((long) obj.get("reps"));
//                int calories = (int)((long) obj.get("calories"));
//                
//                
//                Log log = new Log(date, weight, reps, calories);
//                
//                System.out.println(log + "\n");
//                
//            }
                     
        }
        catch (FileNotFoundException | ParseException ex) 
        {
            System.out.println(ex.toString());
        }
    }
    
    private static void deleteLog(int index)
    {
        bicepLift.remove(index);
        bicepLiftJson.remove(index);
    }
    
    private static void addLog(String date, int weight, int reps, int calories)
    {
        Log log = new Log(date, weight, reps, calories);
        bicepLift.add(log);
        bicepLiftJson.add(log);
    }
    
    
    private static void addLog(String date, int calories)
    {
        Log log = new Log(date, calories);
        bicepLift.add(log);
        //bicepLiftJson.add(log);
    }
    
    private static void addLog(String date, int weight, int reps)
    {
        Log log = new Log(date, weight, reps);
        bicepLift.add(log);
        //bicepLiftJson.add(log);
    }
    
    private static void saveFile()
    {
        File file = new File("after.txt");
        
        try(PrintWriter pw = new PrintWriter(file);)
        {
            // need to add new library " json-lib-2.2.2-jdk15.jar "
            //bicepLiftJson = JSONArray.fromObject(bicepLift);
            
            JSONObject root = new JSONObject();
            
            root.put("Name", account.getName());
            root.put("Age", account.getAge());
            root.put("Gender", account.getGender());
            
            JSONObject exlist = new JSONObject();
            
            exlist.put("bicepLift", bicepLiftJson);
            //exlist.put("tricepLift", tricepLiftJson);
            //exlist.put("squat", squatJson);
            
            
            root.put("ExerciseList", exlist);
            
            pw.printf(root.toJSONString());
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println(ex);
        }
        
        System.out.println(file.getName() + "has successfully saved!");
        
    }
    
}

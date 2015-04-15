
package project;


import java.io.File;
import java.io.FileNotFoundException;
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
    

    File file;
    
    JSONObject rootJson;
    JSONObject exerciseListJson;
    //JSONArray exerciseTypeJson;
    
    Exercise exerciseType = new Exercise("bicepLift");
    
    public static void main(String[] args)
    {
           
    }
    
    private void openFile()
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
            
            System.out.println(name + " " + age  + " " + gender);
            
            // exerciseList contains an array of different kinds of exercise
            exerciseListJson = (JSONObject) rootJson.get("exerciseList");
            
            
            // bicepLift contains an array of daily logs
            JSONArray bicepLift = (JSONArray)exerciseListJson.get("bicepLift");
            
            for (int i = 0; i < bicepLift.size(); i++)
            {
                JSONObject obj = (JSONObject)bicepLift.get(i);
                
                String date = (String) obj.get("date");
                int weight = (int)((long) obj.get("weight"));
                int reps = (int)((long) obj.get("reps"));
                int calories = (int)((long) obj.get("calories"));
                
                
                Log log = new Log(date, weight, reps, calories);
                
                bicepLift.add(log);
                
                System.out.println(log + "\n");
                
            }
            
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
    
    private void delete()
    {
        
    }
    
}

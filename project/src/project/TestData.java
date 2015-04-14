
package project;

/////////////////////////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//
// AUTHOR : Alex Park
// CREATE : 14-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

public class TestData 
{

    public static void main(String[] args)
    {
        File file = new File("user.txt");
        
        try 
        {
            Scanner fileInput = new Scanner(file);
            
            StringBuilder jsonBuilder = new StringBuilder(); 
            
            while (fileInput.hasNextLine()) 
            {
                jsonBuilder.append(fileInput.nextLine());
            }
            
            String jsonString = jsonBuilder.toString();
            
            JSONParser parser = new JSONParser();
            
            JSONObject rootObject = (JSONObject) parser.parse(jsonString);
            
            JSONObject exercise = (JSONObject) rootObject.get("exercise");
            
            JSONArray type = (JSONArray)exercise.get("bicepLift");
            
            for (int i = 0; i < type.size(); i++)
            {
                JSONObject obj = (JSONObject)type.get(i);
                
                String date = (String) obj.get("date");
                long weight = (long) obj.get("weight");
                long reps = (long) obj.get("reps");
                long calories = (long) obj.get("calories");
                
                //Log log = new Log(date, weight, reps, calories);
                
                
                System.out.println(date + weight + reps + calories + "\n");
                
            }
                
                
//                for (int j = 0; j < type.size(); j++)
//                {
//                    JSONObject obj = (JSONObject)type.get(j);
//                    
//                    String date = (String)obj.get("date");
//                    int weight = (int)obj.get("weight");
//                    int reps = (int)obj.get("reps");
//                    int calories = (int)obj.get("calories");
//                    
//                    Log log = new Log(date, weight, reps, calories);
//                    
//                    System.out.println(log + "\n");
//                }
        }
        catch (FileNotFoundException | ParseException ex) 
        {
            System.out.println(ex.toString());
        }
        
        
    }
   

}

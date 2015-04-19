
package project;

import java.util.ArrayList;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE : 14-Apr-2015
//
/////////////////////////////////////////////////////

public class UserAccount 
{
    //////////////////////////// FIELDS /////////////////////////
    
    private String name;
    private int age;
    private char gender;
    
    private ArrayList<Exercise> exerciseList;    
    private Exercise bicep = new Exercise("Bicep");
    private Exercise tricep = new Exercise("Tricep");
    private Exercise deadLift = new Exercise("DeadLift");
    private Exercise backExtension = new Exercise("BackExtension");
    private Exercise squat = new Exercise("Squat");
    private Exercise legPress = new Exercise("LegPress");
    private Exercise benchPress = new Exercise("BenchPress");
    
    ///////////////////////// CONSTRUCTOR //////////////////////

    public UserAccount(String name, int age, char gender) throws IllegalArgumentException
    {
        if(name.equals("") || age < 0)
        {
            throw new IllegalArgumentException("Name should be more than 1 character."
                    + " Age should be more than 0. ");
        }
        this.name = name;
        this.age = age;
        this.gender = gender;
        
        // initiallize the exerciseList
        this.exerciseList = new ArrayList(7);
        exerciseList.add(bicep);
        exerciseList.add(tricep);
        exerciseList.add(deadLift);
        exerciseList.add(backExtension);
        exerciseList.add(squat);
        exerciseList.add(legPress);
        exerciseList.add(benchPress);
    }

    ////////////////////////// METHODS /////////////////////////////////
    
    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public char getGender()
    {
        return gender;
    }

    public ArrayList<Exercise> getExerciseList()
    {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList)
    {
        this.exerciseList = exerciseList;
    }
    
    
    
    @Override
    public String toString()
    {
        return String.format("Name: %s, Age: %d, Gender: %s", name, age, gender);
    }
    
}

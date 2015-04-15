
package project;

import java.util.ArrayList;
import java.util.Date;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

public class UserAccount 
{
    //////////////////////////// FIELDS /////////////////////////
    
    private String name;
    private int age;
    private char gender;
    private ArrayList<ExerciseList> exerciseList;
    private Date createdDate; 
    
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
        createdDate = new Date();
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

    public ArrayList<ExerciseList> getExerciseList()
    {
        return exerciseList;
    }
    
    @Override
    public String toString()
    {
        return String.format("Name: %s, Age: %d, Gender: %s, Created on %s", name, age, gender, createdDate);
    }
    
}

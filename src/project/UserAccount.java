
package project;

import java.util.ArrayList;


/**
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi
 *
 *   Honor: I have completed this assignment on my own.
 *         In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: UserAccount.java 
 *   
 *   Description: This class holds user information such as name, age, gender.
 *              ArrayList called exerciseList contains 7 different exercises. 
 *              Each exercise is an array of logs.
 *
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */

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
    /**
     *
     * @param name - name of the account holder
     * @param age - age of the account holder
     * @param gender - gender of the account holder
     * @throws project.ValidateInput.InvalidInputException
     */
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

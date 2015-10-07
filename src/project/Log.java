package project;


/**
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi
 *   Date: Apr. 15. 2015
 * 
 *   Honor: I have completed this assignment on my own.
 *       In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: Log.java 
 *   
 *   Description: This class has the information of each day's work out 
 *              such as date, weight, reps, calories. Using this class method getRM, 
 *              users can check their one-rep-maximum-weight they can lift. 
 *
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */

public class Log
{
    ///////////////////////// FIELDS ///////////////////////
    
    private String date;
    private int weight;
    private int reps;
    private int calories;
    
    //////////////////////// CONSTRUCTORS ///////////////////////

    /**
     *
     * @param date
     * @param weight
     * @param reps
     * @param calories
     * @throws project.ValidateInput.InvalidInputException
     */
    public Log(String date, int weight, int reps, int calories) throws ValidateInput.InvalidInputException
    {
        if(date.equals("") || weight < 0 || reps < 0 || calories < 0)
        {
             throw new ValidateInput.InvalidInputException("Date cannot be empty character. " 
                     + "Weight, reps, and calories should be more than 0. ");
        }
        
        this.date = date;
        this.weight = weight;
        this.reps = reps;
        this.calories = calories;
    }
    
    
    //////////////////////// METHODS //////////////////////////

    public String getDate()
    {
        return date;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getReps()
    {
        return reps;
    }

    public RM getRm()
    {
        return new RM(weight, reps);
    }

    public int getCalories()
    {
        return calories;
    }
    
    @Override
    public String toString()
    {
        return String.format("%20s %25s lbs %25d cal", date, RM.calculateRM(weight, reps)+"lbs", calories);
    }
}

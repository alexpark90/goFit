package project;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

public class Log 
{
    ///////////////////////// FIELDS ///////////////////////
    
    private String date;
    private int weight;
    private int reps;
    private int calories;
    
    //////////////////////// CONSTRUCTORS ///////////////////////

    public Log(String date, int weight, int reps) throws IllegalArgumentException
    {
        this(date, weight, reps, 0);
    }

    public Log(String date, int calories) throws IllegalArgumentException
    {
        this(date, 0, 0, calories);
    }

    /**
     *
     * @param date
     * @param weight
     * @param reps
     * @param calories
     */
    public Log(String date, int weight, int reps, int calories) throws IllegalArgumentException
    {
        if(date.equals("") || weight < 0 || reps < 0 || calories < 0)
        {
             throw new IllegalArgumentException("Date cannot be empty character. " 
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
        return String.format("%-10s %-10s lbs %-10d", date, getRm(), calories);
    }
    
}

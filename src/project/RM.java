
package project;


/**
   *   Submitted by: Alex Yeji Park && Chris Sarvghadi
   *
   *   Honor: I have completed this assignment on my own.
   *       In researching the assignment I got help/ideas from http://stackoverflow.com/ 
   *
   *   File name: RM.java 
   *   
   *   Description: This class is for calculating one rep maximum weight a person can lift. 
   *
   *   @author Alex Yeji Park && Chris Sarvghadi 
   */

public final class RM 
{
    ////////////////////// FIELDS //////////////////////
    
    private final static double FOMULA = 0.0278;
    private int oneRepMax;
    
    //////////////////// CONSTRUCTOR //////////////////

    public RM(int weight, int reps)
    {
        oneRepMax = calculateRM(weight, reps);
        
    }
    
    /////////////////////////// METHODS //////////////////////////
    
    public static int calculateRM(int weight, int reps)
    {
        return (int)Math.round(weight / (1 + FOMULA - (FOMULA * reps)));
        
    }
    
    public int getRMatPercentages(double percentages)
    {
        return (int)Math.round(oneRepMax * (percentages/100));
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(oneRepMax);
    }
    
     
}

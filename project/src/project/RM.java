
package project;

/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE : 14-Apr-2015
//
/////////////////////////////////////////////////////

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
    
    public int getRMatPercentages(int percentages)
    {
        return (int)Math.round(oneRepMax * (percentages/100));
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(oneRepMax);
    }
    
     
}

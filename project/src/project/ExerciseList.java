
package project;

import java.util.ArrayList;


/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE :
//
/////////////////////////////////////////////////////

public class ExerciseList extends ArrayList<Log> 
{
    ///////////////////////// FIELDS /////////////////////////
    private String type;

    
    /////////////////////////// CONSTRUCTOR ////////////////////////
    public ExerciseList(String type) throws IllegalArgumentException
    {
        if(type.equals(""))
        {
             throw new IllegalArgumentException("Exercise type should be more than 1 character.");
        }
        this.type = type;
    }
    
    
    /////////////////////////////// METHODS /////////////////////////////
    
    @Override
    public String toString()
    {
        return String.format("Exercise type %s has %d logs.", type, this.size());
    }
    
}

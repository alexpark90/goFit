
package project;

import java.util.ArrayList;


/**
 *   Submitted by: Alex Yeji Park && Chris Sarvghadi
 *
 *   Honor: I have completed this assignment on my own.
 *          In researching the assignment I got help/ideas from http://stackoverflow.com/ 
 *
 *   File name: Exercise.java 
 *   
 *   Description: This class extends ArrayList to hold several logs.
 *
 *   @author Alex Yeji Park && Chris Sarvghadi 
 */

public class Exercise extends ArrayList<Log> 
{
    ///////////////////////// FIELDS /////////////////////////
    private String type;

    
    /////////////////////////// CONSTRUCTOR ////////////////////////
    public Exercise(String type) throws IllegalArgumentException
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

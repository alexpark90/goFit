
package project;


/**
   *   Submitted by: Alex Yeji Park && Chris Sarvghadi
   *
   *   Honor: I have completed this assignment on my own.
   *       In researching the assignment I got help/ideas from http://stackoverflow.com/ 
   *
   *   File name: ValidateInput.java 
   *   
   *   Description: This is the interface to be implement to validate user input.
   *                        This interface has 2 abstract methods and one custom exception.
   *
   *   @author Alex Yeji Park && Chris Sarvghadi 
   */

public interface ValidateInput
{
    public abstract void validateInput() throws InvalidInputException;
    public abstract void reset();
    
    public class InvalidInputException extends Exception
    {
        public InvalidInputException(String st)
        {
            super(st);
        }
    }
}

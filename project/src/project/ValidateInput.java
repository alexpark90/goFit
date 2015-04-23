
package project;

/////////////////////////////////////////////////////
//
// AUTHOR : Alex Park & Chris Sarvghadi
// CREATE : 7-Apr-2015
// UPDATE : 22-Apr-2015
//
/////////////////////////////////////////////////////

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

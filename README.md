# goFit

This was my second Java project for shcool, It was team project with a friend of mine, Chris.

My part was designing whole structure of the program, creating UML, json file handling, Exception handling, setting the card layout, and helper methods in mainGui and subGui.

I created custom exception in ValidateInput interface and mainGui and subGui are implementing it.
So, everytime user submit their inputs, mainGui or subGui's overriden methods of ValidateInput class is called,
and throws custom exception depends on the inputs.

This program is designed to track the progress of a user’s strength training fitness regimen.
Users can choose to use this information to modify their training regimen, 
or they may simply use the application to keep track of the work that they have done.

A graphing function which will take the user’s inputs and visually display their progression is not implemented yet,
I am planning to do it in the near futrue. 

Left-hand(West) navigation pane allows switching between various tabs(CardLayout)




![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit1.jpg)

	
Welcome/Main Page:
-	Create button: pops up a new SubGui window to create a new user account.
-	Open button: pops up a JFileChooser window to allow loading of an existing account.




![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit2.jpg)


Create Account Page (SubGui):
-	Name/Age/Gender JTextfields: allow user to input information about themselves for creation of a new account. 




![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit8.jpg)
![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit9.jpg)


Entry Page:
-	Exercise JComboBox: allows the user to choose an exercise (ArrayList) to load into the application.
-	Date/Weight/Repetitions/Calories JTextField: allows user to input data about their exercise.
-	Calculate JButton: uses the previous JTextFields to calculate the user’s One-Rep Maximum, 
                    and how much they can likely lift at various percentages of that maximum.
-	Add/Edit JButtons: allow the user to add their inputs into their log, or to edit/delete previous entries.




![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit4.jpg)
![Image file of the app](https://raw.githubusercontent.com/alexpark90/goFit/master/screenshots/gofit5.jpg)


Log Page: 
-	Exercise JComboBox: allows user to specify an exercise, of which logs will be displayed in a columned JList format. 
-	Details/Edit JButton: will switch back to the Entry page and load the selected log entry. 
-	Save JButton: will save the new entries and/or edits to the user’s account file.
- 	Delete JButton: will delete the selected log.


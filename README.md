# CSCI2020U_Assignment2
The second assignment for 2020U.

Kevin Lounsbury 100654226
Nicholas Juniper 100659791

Project Information:
This project is a File Sharing System created by Kevin and Nicholas. The server and client projects are entirely separated and thus can be put anywhere, does not need to be near the client to function. You can upload and download text-based files of any sort back and forth between the server and your client folder. When you launch the program you simply give the folderpath as the first argument for your program. If you do not pass a folder, the program will assume you want the default '.\data' directory. 

Improvements Made:
We swapped out the simple button header system shown in the assignment pdf with a proper menu bar, complete with menu items for Downloading a selected file, Uploading a selected file, and Refreshing both file lists. The code for which are all self contained within their own functions, calling on the support of existing Client methods in order to complete their tasks.

How to Run (step-by-step):
**************************IMPORTANT**************************
Ensure that your [FolderPath] is organized using '\' not '/'
**************************IMPORTANT**************************
Step 1: Make sure you are using java11 or later
Step 2: Have Gradle properly installed and configured
Step 3: Open into ./assignment2/Server/
Step 4: Run command 'gradle run'
Step 5: Wait for program to start running. Will say what port it's listening to.
Step 6: Open into ./assignment2/Client/
Step 7: Run command 'gradle run --args="[FolderPath]"' (replace [FolderPath] with your chosen path, I recommend .\data specifically.) 
Step 8: Click on a file and use the file buttons in order to perform your tasks. You can only perform download when a Server (right side) file is selected, and you can only perform upload when a Client (left side) file is selected. 
Step 9: You're done!!! 

Referenced Resources:
Referenced Java documentation: 
https://docs.oracle.com/en/java/javase/11/
Referenced JavaFX documentation: 
https://docs.oracle.com/javase/8/javafx/api/toc.htm

All code submitted is original.



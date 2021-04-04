# CSCI2020U_Assignment2
The second assignment for 2020U.

Kevin Lounsbury 100654226
Nicholas Juniper 100659791

Project Information:
This project is a File Sharing System created by Kevin and Nicholas. The server and client projects are entirely separated and thus can be put anywhere, does not need to be near the client to function. You can upload and download text-based files of any sort back and forth between the server and your client folder. When you launch the program you simply give the folderpath as the first argument for your program. If you do not pass a folder, the program will assume you want the default '.\data' directory. 

If you go into HttpServerHandler.java and HttpClient.java file, you can enable Debug mode (set _debugMode variable in both to true), which will print out the packet information to the console for the client, and host information for the server.

Gif of the client running with .\data:
https://i.gyazo.com/acfc4da9dc10260aa67c733b9c7c27a8.mp4
Gif of the client running with random test folder passed as argument:
https://i.gyazo.com/977baf3f3341da15db0bb75019082745.mp4
Gif of the refresh button updating the ./data folder contents after you delete files:
https://i.gyazo.com/21fd6673381b4e12fb2fa1f444dbda52.mp4 
Gif of the refresh button updating a random test folder passed as argument after you delete files: 
https://i.gyazo.com/5f42f920d5cabdf8c5f8aa4f1612b328.mp4

Gif of the project running from gradle with no argument:
https://i.gyazo.com/50a8ade10a72ea9ae43aefb56aedda95.mp4
Gif of the project running from gradle with a folder passed as an argument:
https://i.gyazo.com/d286b93f3a94a622b3b748cd17664e74.mp4

Improvements Made:
If you are not selecting any files, we simply don't contact the server at all by performing a check on the client side. 

We swapped out the simple button header system shown in the assignment pdf with a proper menu bar, complete with menu items for Downloading a selected file, Uploading a selected file, and Refreshing both file lists. The code for which are all self contained within their own functions, calling on the support of existing Client methods in order to complete their tasks.

Due to the way that our networking solution was implemented we are actually able to open up two multiple copies of our Client to different areas allowing us to Transfer files between multiple clients via transfering the file you want to the server, and then having the second client refresh the server's file list and then downloading the file.

Gif of this happening:
https://i.gyazo.com/edca9689455f0eaaabf2a790d2745b67.mp4

How to Run (step-by-step):
Step 1: Make sure you are using java11 or later
Step 2: Have Gradle properly installed and configured
Step 3: Open into ./assignment2/Server/
Step 4: Run command 'gradle run'
Step 5: Wait for program to start running. Will say what port it's listening to.
Step 6: Open into ./assignment2/Client/
Step 7: Run command 'gradle run --args="[FolderPath]"' (replace [FolderPath] with your chosen path, if you leave empty it will just choose .\data folder in the Client) 
Step 8: Click on a file and use the File button at the top in order to perform your tasks. You can only perform download when a Server (right side) file is selected, and you can only perform upload when a Client (left side) file is selected. 
Step 9: You're done!!! 

Referenced Resources:
Referenced Java documentation: 
https://docs.oracle.com/en/java/javase/11/
Referenced JavaFX documentation: 
https://docs.oracle.com/javase/8/javafx/api/toc.htm

All code submitted is original.



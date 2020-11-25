# Project Name: RecipeBook
# Project Lead: Jonas English
# List of Contributors: Jonas English, Rishika Someshwar, Kevin Nguyen and, Travis Sauer

There are two options for testing and running our application. The first is a production method which allows you to run a .jar file (similar to running an executable, 
but with a few more steps) or if you're interested in making any changes to the project: the process of cloning and setting up your environment via Eclipse will be 
included.

Method 1: Running the project with .jar file

The first step is to download the JAR file that is included within this repository. We're going to use a Third-Party Jar Executor to run our application. 
Be advised: this is for the Windows Operating System only. To download the software needed
to run the .jar file, you will need to copy and paste this link into your browser's URL: https://bitstorm.org/jarx/. Next look on the page displayed
and you will see a "clickable" item read as: "Jarx-1.2-installer.exe (74 kB)." Click on this item and save/download it to your local machine. Then navigate to your downloads folder and run this file as administrator; you can leave the default location as it is for installation. Now go to the location where you downloaded your 
JAR file, right-click it, mouse over "open with," and click Execute Java JAR-files. Now you'll be able to use the Recipe Book application.

Method 2: Cloning the repository from GitHub into Eclipse

Assuming EGit is installed and set-up within Eclipse: Select File from the menu at the top of Eclipse, then import, select Git, click "Projects from Git" and hit next.
Select "Clone URI" and click next. Now we need to navigate to the repo's home-page via the link: https://github.com/jonasenglish/RecipeBook. Left-click the green button labeled "Code" and copy the HTTPS URI, which is: https://github.com/jonasenglish/RecipeBook.git. Goto Eclipse and paste the link into the URI field, and it will populate the rest of the fields (you will need to connect to your GitHub account, i.e. enter your login credentials before clicking next). Ensure
that the "master" branch is selected and then hit next again. Choose a local directory where you would like the repository to be stored and click Next. On the next window, the "import existing Eclipse projects" radio button should be selected, then click Next. Ensure that the RecipeBook path is selected and click Finish. The
RecipeBook source-code will now be displayed in your package explorer to the left. Left-click the right arrow to drop down the contents. Finally, we need to
set our JRE system library to the correct version to run the application. Right-click on "JRE System Library" and click properties. In the pop-up window,
select the "Execution environment" button, click the drop-down arrow and select "JavaSE-1.8 (jre1.8.0.261)," and click apply and close. Now you will be able to right-click on the RecipeBook project folder and select run as Java Application.

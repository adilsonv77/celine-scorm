# Introduction #

To illustrate how deploy BRUCE (Basic Reference of Using the CELINE Component).


# Details #

This tutorial demonstrates how deploy a web application using MySQL Database.

First, you must download the database creation SCRIPT. Access the folder [here](https://drive.google.com/folderview?id=0BzNO6qBfK-pGOWY1bV9KYUNvaGc&usp=sharing). The filename is **celine.sql**. Run the script in MySQL.

Download from the same [folder](https://drive.google.com/folderview?id=0BzNO6qBfK-pGOWY1bV9KYUNvaGc&usp=sharing) and extract **bruce.zip**. This file contains an Eclipse project with the web application source. Open in Eclipse.

Afterwards, open the file celine-config.xml. Pay attention in two configurations:
  * **courses-folder**: is the folder where the application will upload and extract the SCORM courses file. To change the content of this element pointing the folder where will you want to store the courses. If you use Linux, try something as file:/home/adilsonv/cursos/;
  * **rdb**: is the Database access configuration. I've experienced with MySQL and Postgres. To change the parameter based on your own configuration.

Now, deploy the application on the server (maybe Apache Tomcat).

Try open the application in Firefox and Chrome. When the application starts, it will show the login form. To change the user and password to admin and adm. Enter in the Manage Courses option and import a SCORM 2004 3rd edition zip file.

Enjoy the application.
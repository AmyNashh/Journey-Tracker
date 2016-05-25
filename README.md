# Journey-Tracker

This android application allows the user to Register and Login with their details and record journeys in distance, time and location while choosing a form of transport. These journeys can later be viewed with the start and stop position on google maps.

- MainActivity: <br />
The user is given the option to register or login to the app.

- Register: <br />
Once they have registered their username and password will be stored in the database 2 table.

- Login:<br />
they will then be asked to login with the details they have just entered. If these details
are not found in the database 2 table, a toast will pop up with "invalid password or username". The username they entered here will be bundled across to the pages for recording and viewing the users journeys. 

- Menu:<br />
The user has the option to: <br />
View their own journeys (on a map).<br />
Record a journey.<br />
View a list of their journeys with all the details of each journey they have made.<br />
Sign out - this will bring them back to the MainActivity.<br />

- Option recording & StartRecording:<br />
The user is asked what method of travel they are using, whether they are cycling, walking, 
driving etc. Once they have selected which mode of transport they are using and hit the
button on that page a timer will start. The users latitude and logitude coordinates will
be saved in to database 1, along with the duration of their journey. The user will not 
be able to stop recording their journey until they have moved location, once they do hit 
the stop recording button they will be brought back to the menu.

- ViewAll:<br />
This will bring back the users information for each journey by calling the getAll method 
in the DBManager class. It will display their mode of travel, the date, duration, distance
at latitude and logitude coordinates.

- View Journeys:<br />
This will display a row of all the users journeys, they can identify the journeys by date.
Here, the getStarts and getStops methods are each called from MyDBManager taking in the
parameters of the username.

- Map:<br />
The start lat, long and stop lat, long are bundled over from the view journeys class and
displayed on the map as indicaters of the stop and start position if the journey.

- MyDBManager:<br />
This class creates the 2 tables, one stores the persons username and password, the other
stores the journeys of that user. 
It contains the methods:<br />
Login - is called from this class when the user initially logs in. <br />
getStops and getStarts - called in ViewJourneys.<br />
getALL - is called in the View all page to display all the users journey information. <br />
insertTask- called in StartRecording. <br />

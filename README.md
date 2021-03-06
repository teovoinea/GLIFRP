# GLIFRP
Live demo can be found here:
<a href="http://ec2-35-166-174-199.us-west-2.compute.amazonaws.com:4567/index.html">Demo</a>

Design doument can be found here:
<a href="https://teovoinea.github.io/v3/2XB3DesignDoc">Doc</a>


Scrum board can be found here: 
<a href="https://trello.com/b/YEKUDQie">GLFRP Scrum Board</a>
To use this sign up for a Trello account

# To run the server
1. ```git clone https://github.com/teovoinea/glifrp```
2. ```cd glifrp```
3. ```bower install```
4. ```cd final```
5. ```rm -r public/```
6. ```cp -r ../Backend/src/main/resources/public/ .```
7. ```java -jar final.jar```

# To run/test the backend
1. <a href="https://eclipse.org/downloads/"> **Install Eclipse (Or Apache Maven)** </a>
2. **Compile main.java**

# To run/test the frontend
1. <a href="https://nodejs.org/en/"> **Install NodeJs and npm** </a>
2. **Install Bower and grunt**
	* ```npm install -g bower grunt-cli```
3. **Run server**
	```
	cd FrontEnd
	grunt server
	```
	
___________________________________________________________________________________________
# Images

![alt tag](https://raw.githubusercontent.com/teovoinea/GLIFRP/master/GLIFRP.png?token=AHxextkSPxcdws0MSVTnbPST6fBJwKSTks5XHmigwA%3D%3D)
![alt tag](https://raw.githubusercontent.com/teovoinea/GLIFRP/master/glifrp_mobile.png?token=AHxexsbhLkd5KMaIUbrlPVkkycpSzJeBks5XHmjHwA%3D%3D)
___________________________________________________________________________________________
# "Major" update history
* Apr 08 - R - Finished JavaDocs & testing for all classes and methods written by Roberto
* Apr 07 - T - Finished JavaDocs for all the classes and methods written by Teo
* Apr 06 - C - Updated scoring algorithm based on jail time weights
* Apr 05 - CRTNP - Caterpillar (Live)
* Apr 05 - P - Updated UI colors to be more consistent
* Apr 04 - T - Hosted GLIFRP live on Amazon Web Services
* Apr 02 - N - Finished UI, connected to spark server using HTTP requests
* Mar 31 - R - Added support for abbreviated state names
* Mar 30 - P - Optimized database querying to only query for names first, THEN additional crime/lat/long information.
* Mar 30 - R - Updated Graph to work with City object instead of Crime
* Mar 29 - C - Fnished Scoring Algorithm 
* Mar 25 - T - Added endpoint to search by latitude & longitude
* Mar 23 - P - Updated Query class to build City objects instead of Crime
* Mar 15 - CRTNP - Baboon (Beta)
* Mar 13 - R - Search by Crime Rate is completed
* Mar 13 - C - Made Simple Scoring Algorithm (for prototype)
* Mar 11 - C - Edited compareTo and Search by Attribute
* Mar 10 - T - Added compareTo and Sort by attribute
* Mar 09 - C - Changed Searching Algorithms to use ArrayList instead of Arrays
* Mar 07 - CRTNP - Alligator (Alpha)
* Mar 07 - C - Basic Searching Algorithms
* Mar 03 - P - Added databases
* Mar 03 - T - Added all the possible sorting algorithms we will use
* Mar 01 - N - Added index.html and style.css files. Will create Sketch of website
* Mar 01 - T - Serves index properly
* Mar 01 - T - Properly linked html using spark
* Mar 01 - R - Front End Setup
* Mar 01 - R - Extended City to Node
* Feb 29 - R - Graph ADT in progress
* Feb 29 - T - Added finding xip code from lat/long and vise versa from openstreetmap
* Feb 29 - T - JSON parsing and creating using GSON from Google
* Feb 28 - T - Added search endpoint with a mirroring response on POST
* Feb 28 - P - Added Spark, update README
* Feb 27 - N - Added location data to the database
* Feb 27 - N - Added the housing database

# Introduction
(50-100 words)
What does this app do? What technoglies you have used? (e.g. Twitter REST API, HTTP client, mvn, Java libs, docker etc..)
The app is responsible for providing interactivity with the Twitter API to perform CRUD operations through the command line interface, allowing for users to post,
update, delete and search tweets. The project is implemented entirely through the use of Java and employs several key Java frameworks/libraries to ensure functionality;
Apache HTTP libraries an OAuth libraries are used to establish an HTTP connection with the Twitter API service and authenticate users, while the Jackson library
is used to interpret JSON strings provided with Http responses. 

The project also employs the Java Spring framework with its component scan implemnentation to use its IoC to aid in building and injecting class dependencies within
the final application. Finally the project is managed using Maven to handle loading dependencies, building the project and running integration and unit tests. The 
project can be ran with greater ease in just a few lines using its dockerized version hosted on Docker Hub.

# Quick Start
## Running with Docker
```
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
eggrolle/twitter post "text to post" "latitude:longitude"
eggrolle/twitter show "id"
eggrolle/twitter delete id1:id2:id3...
```
## Running with Maven
```
#Package app
mvn clean compile package

#Run jar with arguments
java -jar target/twitter1.0.0.jar post|show|delete [arguments]
```

# Design
## UML diagram
UML_Diagram(/core_java/twitter/assets/UMLTwitter.png)
### TwitterCLIApp/Main
The main runnable class responsible for building all related class objects and setting up dependencies between them. Accepts arguments specifying app functionality
in the form of: post|show|delete [arguments].
### TwitterHttpHelper
Responsible for all of the core HTTP fuinctionality with a given URI, executes requests of the type POST, GET and DELETE. Sets up Http requests, specifying tweet data in the body and using OAuthConsumer to set authorization in the header through the use of four mandatory keys, consumerKey, consumerSecret, accessToken and accessSecret. 
### TwitterDAO
Constructs the Twitter REST API URI's and Http bodies required for the Twitter V2 API. After constructing URIs and employing TwitterHttpHelper's functionality to pass
requests to the Twitter API, the class also parses the JSON Object provided in the HTTP response and builds a corresponding Tweet object through a String representation of the JSON Object.
### TwitterService
The service layer of the application, responsible for validating arguments passed into the TwitterController before being sent to TwitterDao.
### TwitterController
The controller layer of the application, used to consumer the user input and calling the corresponding service layer method to either post, show or delete Tweets. 
## Models
The project employs objects/classes to match the internal representation of Tweets within the Twitter API. The Tweet class in this proejct contains a few of the most pertinent fields of information stored for each tweet in the Twitter database. These are fields like text, the main bodt of text, retweeted_count, id, favorite_count, retweeted etc. The model also employs further objects named 'Entities' and 'Coordinates' to store further information Tweets hold, such as hashtags, user mentions and coordinates of the tweet location.
## Spring
- How you managed the dependencies using Spring?
Spring is used to avoid typical linear dependency management problems. Initially the project ran by having all classes/components instantiated through a single main method which is also responsible for dependency injection. The project employs spring to hand IoC, inversion of control, to simplify and clean up this process. The project ueses the component scan method to identify spring beans/components across the project and automatically instantiate these objects and set up dependency injection, simplifying its functionality.

# Test
The application was tested using Junit and Mockito. Junit was used for integration stesting to test CRUD operations in the project from the top down every time a new layer was added to the MVC archetecture. Mockito was used similarily at every stage of development although for the purpose of Unit testing, to check if newer blocks of code implemented ran correctly without necessarily needing to test every layer of the project below it t horugh the use of Mock objects to mimic behaviors and spying to ensure objects were functioning correctly.

## Deployment
The completed project was built into a single Uber Jar fiel cotaning all of our code/classes as well as our various dependencies. This single jar was then uploaded to Docker Hub as an image under the title eggrolle/twitter which can conveniently be pulled and run as a container as specified in the quick start section.

# Improvements
- Add the option for different kinds of searches across Tweets. Instead of searching by ID search by keywords or hashtags etc.
- Experiment with some larger scale use of the Twitter API which looks at more aggregate data across Tweets.
- Improve/add more tests using Mockito

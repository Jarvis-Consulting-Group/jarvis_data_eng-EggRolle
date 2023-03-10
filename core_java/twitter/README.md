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
- how to package your app using mvn?
- how to run your app with docker?
##Running with Docker
```
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
jrvs/twitter_app post "text to post" "latitude:longitude"
jrvs/twitter_app show "id"
jrvs/twitter_app delete id1:id2:id3...
```
##Running with Maven
```
#Package app
mvn clean compile package

#Run jar with arguments
java -jar target/twitter1.0.0.jar post|show|delete [arguments]
```

# Design
## UML diagram
## explain each component(app/main, controller, service, DAO) (30-50 words each)
## Models
Talk about tweet model
## Spring
- How you managed the dependencies using Spring?

# Test
How did you test you app using Junit and mockito?

## Deployment
How did you dockerize your app.

# Improvements
- Imporvement 1
- Imporvement 2
- Imporvement 3

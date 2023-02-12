# Introduction
The project is the first in a series of core Java applications designed to mimic the Linux 'grep' command which allows users to search for matching lines from files
using patttern matching through Regex (Regular Expressions). The application takes three arguments: a pattern, root directory to search from, and an output file for 
matching lines. To function the application relies on core Java fundamentals and employs libraries such as File, BufferedReader/Writer and a few ADTs to process data. Lambda expressions and Streams are also incorporated to improve functionality of the application. The program is deployed as a Docker image, allowing it to easily be run via a few simple Docker commands.

# Quick Start
How to use your apps? 
- Run through docker image
```
#Pull image
docker pull eggrolle/grep

#Run docker image (Fill in commands)
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep (expression) (rootDir) (outputFile)
```
- Run through Java file, requires compiling/building with maven

#Implemenation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
    if containsPattern(line)
      matchedLines.add(line)
writeToFile(matchedLines)
```
## Performance Issue
The memory issue with the application is that Java Virtual Machines require occup certain amounts of memory depending on their application's requirements. When dealing
with large amounts of memory, the aplication can be inefficient and require a large heap size. This could be avoided by streaming memory in a more filtered manner
through the use of data structures like Stream APIs or BufferedReaders.

# Test
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)
Testing for the application was done manually through a series of tests consisting of sample data to test average and niche cases. Basic tests consisted of 
searched for patterns through a typically set up filestructure with multiple files and subdirectories from a root folder. Subsequent testsmeasured cases prone to error,
such as directories with no files, or files without any matching lines, or data at all. The progress of testing is recorded through the use of logging to record successes and 
faulures across the applications runtime.

# Deployment
To avoid requiring the use of compiling and use of maven to avoid errors when dealing with build dependencies the application when finished was dockerized to allow for it to be run in two simple lines of code specified in the quick start. The image simply has to be pulled and then run with the three given inputs.

# Improvement
- Experiment with lambda functions in greater detail
- Provide more thorough logging across sections of the application
- Rewrite program to process large amounts of data with smaller heap memory size

# Introduction
The purpose of this project is to implement a Linux application rresponsible for recording hardware specifications and usage information of a series of multiple server connected internally through a switch and which communicate through IPv4 addresses. 
The program utilizes a monitoring agent which regularly runs bash scripts responsible for querying all of the relevant information from the system, and then inserting it into a Postgres SQL database hosted using a Docker instance. The agent is regulated  using a Crontab instance
which ensure the hardware information is queried and recorded at regular time intervals. The project relies mainly on the uise of bash and PSQL while being managed and deployed through the use of docker and git.


# Quick Start
- Start a psql instance using psql_docker.sh
```
bash ./scripts/psql_docker.sh start|stop|create [username] [password]

#Example
bash ./scripts/psql_docker.sh start
```
- Create tables using ddl.sql
```
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
- Insert hardware specs data into the DB using host_info.sh
```
bash ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

#Example
bash ./scripts/host_info.sh "localhost" 5401 "host_agent" "myDB" "mypass"
```
- Insert hardware usage data into the DB using host_usage.sh
```
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

#Example
bash ./scripts/host_usage.sh "localhost" 5401 "host_agent" "myDB" "mypass"
```
- Crontab setup
```
#Open editor
bash> crontab -e

#Add to beginning
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh localhost port# hostName dbName password > /tmp/host_usage.log

#Check if Crontab instance running
crontab -l
```

# Implemenation
The project is implemented through the use of various interacting bash scripts designed top be ran on a cluster of nodes/servers running the Linux based distribution
CentOS 7. These bash scripts regularly query each machine/server on the network which communicate with eachother and record information regarding hardware
specifications such as resource usage which can be used further for resource planning purposes. The database of the project storing this information is implemented
through the use of a Docker containter running an instance of a Postgres SQL database containing two tables containing information regarding each server, and 
live host usage metrics every minute.
## Architecture
![Architecture Diagram](/assets/LinuxArchitecture.png)

The attatched diagram demonstrates how the project is implemented. Each individual node in the system is responsible fur running its own set of bash scripts
responsible for regularly qurying data from the machine. This information is passed through the network through a switch which is eventually redirected back to
a single machine reponsible for saving the results into the database.
## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
Automates the process of creating, starting or stopping a docker container used to host our PSQL database.
- host_info.sh
Adds information of current machine to host_info table within the PSQL database. Uses Linux CLI commands to query relevant information.
- host_usage.sh
Adds information of current machine's usage metrics to host_usage table within the PSQL database. Uses CLI commands to regularly query relevant information.
- crontab
Regulates host_usage.sh bash script to query the server every minute and add the results to the database.
- queries.sql
Automates DDls by switching to correct database and creating host usage and host info tables if they dont already exists, allowing for the following bash
scripts to run without issue.

## Database Modeling
- `host_info`
| id | hostname | cpu_number | cpu_architecture | cpu_model | cpu_mhz | l2_cache | timestamp | total_mem |
| 1 | jrvs-remote | 1 | x86_64 | Intel Xeon | 2300 | 256 | 2023-01-01 | 64000 |
- `host_usage`
| timestamp | host_id | memory_free | cpu_idel | cpu_kernel | disk_io | disk_available |
| 2023-01-01 | 1 | 30000 | 90 | 4 | 2 | 3 |

# Test
To test the bash script's DDL the scripts were ran on the machine in a test environment. The results of the testing demonstrated that the database was able to be set up
without any issues and the automated updates to host_usage were successful.

# Deployment
The project wad deployed thorugh the use of Docker, Github and Crontab. Github was used for the prupoose of managing individual features added to the project across its development
primarily through the use of various feature brances to assist in ensuring individual parts of the project could function independently without issues. The use
of Docker was essential in deployment due to its ability to create containers responisble for running PSQL database instances responsible for keeping track
of our host info and host usage metrics. Lastly Crontab was used in ensuring that thebash script for querying host usage metrics would run consistently every
minute, fulfilling its purpose in the project.

# Improvements
- Handle hardware updates 
- Larger scale testing/development
- Measure additional metrics, search for changes over time

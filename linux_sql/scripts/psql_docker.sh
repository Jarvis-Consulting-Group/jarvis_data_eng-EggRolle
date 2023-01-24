#!/bin/bash

#Assign arguments from arguments
cmd=$1
db_username=$2
db_password=$3


#Start docker
sudo systemctl status docker || sudo systemctl start docker

#Check if container is running
docker container inspect jrvs-psql
container_status=$?

#Check for start, stop or create psql server option
case $cmd in
  create)

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  #check # of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #Create the container
  docker volume create pgdata
  #Start the container
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password
  exit $?
  ;;

  start|stop)

  #Check container status
  if [ $container_status -eq 1 ]; then
    echo 'Container does not exists'
    exit 1
  fi

  #Start or stop container
  docker container $cmd jrvs-psql
  exit $?
  ;;

  *)
  #If we fail to match case
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac

exit 0``

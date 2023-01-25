#! /bin/bash

#Save CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Check if input is valid
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

#Cpu info
lscpu_out=`lscpu`
hostname=$(hostname -f)

#Get relevant information for database
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "Model name:" | awk '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "L2 cache" | awk '{print $3}' | xargs | rev | cut -c 2- | rev)
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}' )

#Date and Time combined
timestamp1=$(vmstat -t | awk '{print $18}' | tail -n1 | xargs)
timestamp2=$(vmstat -t | awk '{print $19}' | tail -n1 | xargs)
timestamp="$timestamp1 $timestamp2"

#Get incrementing id
id="(SELECT MAX(id) +1 from host_info)"

insert_stmt="INSERT INTO host_info(id,hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,l2_cache,timestamp,total_mem)
VALUES($id,'$hostname','$cpu_number','$cpu_model','$cpu_architecture','$cpu_mhz','$l2_cache','$timestamp','$total_mem');"

#set up environment variables
export PGPASSWORD=$psql_password
#Insert the data into our database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
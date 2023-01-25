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

#Save output of CLU commands related to machine information
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

#Manually get specific lines of hardware specifications from the above commands
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}'| tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $1}'| tail -n1 | xargs)
disk_io=$(vmstat -d | awk '{print $10}' | tail -n1 | xargs)
disk_available=$(df -BM / | awk '{print $4}' | tail -n1 | xargs | rev | cut -c 2- | rev)
#disk_available=echo $disk_available | rev | cut -c 2- | rev

#Current time in YYYY-MM-DD hh:mm:ss format
timestamp1=$(vmstat -t | awk '{print $18}' | tail -n1 | xargs)
timestamp2=$(vmstat -t | awk '{print $19}' | tail -n1 | xargs)
timestamp="$timestamp1 $timestamp2"

#Get host id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#Insert obtained data into table
insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idel, cpu_kernel,
disk_io, disk_available) VALUES('$timestamp',$host_id,'$memory_free','$cpu_idle','$cpu_kernel','$disk_io','$disk_available');"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?

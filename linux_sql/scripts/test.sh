hostname=$(hostname -f)

echo $hostname

lscpu_out=`lscpu`

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

echo $cpu_number

cpu_architecture=$(echo "$lscpu_out" | egrep "Architecture:" | awk '{print $2}' | xargs)

echo $cpu_architecture

cpu_model=$(echo "$lscpu_out" | egrep "Model:" | awk '{print $2}' | xargs)

cpu_mhz=$(echo "$lscpu_out" | egrep "CPU MHz" | awk '{print $2}' | xargs)

l2_cache=$(echo "$lscpu_out" | egrep "L2 cache" | awk '{print $2}' | xargs)

total_mem= $(vmstat --unit M | tail -1 | awk '{print $4}')

timestamp=date +"%Y-%m-%d %H:%M:%S"

memory_free=$(vmstat --unit M | tail -1 | awk -v col="4" '{print $col}')
cpu_idle=
cpu_kernel= uname -r
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=
#hostname =


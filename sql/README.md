# Introduction

# SQL Quries

###### Table Setup (DDL)

CREATE TABLE cd.members (
  memid integer NOT NULL, 
  surname VARCHAR(200) NOT NULL, 
  firstname VARCHAR(200) NOT NULL, 
  address VARCHAR(30) NOT NULL, 
  zipcode integer NOT NULL, 
  telephone VARCHAR(20) NOT NULL, 
  recommendedby integer, 
  joindate TIMESTAMP NULL, 
  CONSTRAINT members_pk PRIMARY KEY (memid), 
  CONSTRAINT members_fk FOREIGN KEY (recommendedby) REFERENCES cd.members(memid) ON DELETE 
  SET 
    NULL
);
CREATE TABLE cd.bookings (
  facid integer NOT NULL, 
  memid integer NOT NULL, 
  starttime TIMESTAMP NULL, 
  slots integer NOT NULL, 
  CONSTRAINT bookings_pk PRIMARY KEY (facid), 
  CONSTRAINT bookings_fk FOREIGN KEY(memid) REFERENCES cd.members(memid)
);
CREATE TABLE cd.facilities (
  facid integer NOT NULL, 
  name VARCHAR(100) NOT NULL, 
  membercost numeric NOT NULL, 
  guestcost numeric NOT NULL, 
  initialoutlay numeric NOT NULL, 
  monthlymaintenance numeric NOT NULL, 
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);


###### Question 1: Show all members 



###### Questions 2: Lorem ipsum...




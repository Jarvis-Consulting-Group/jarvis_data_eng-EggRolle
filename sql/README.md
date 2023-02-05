# Introduction

The purpose of this project is to practice my PostgreSQL skills by implemening a database representing a club with tables responsible for holding member information, facilities that the club offers, and bookings that members made for these facilities. The project consists of the DDL responsible for setting up the tables in the database as well as various queries to test my ability to interact with the database through the use of operations such as inserts, selects, updates and joins.

# SQL Quries

### Modifying Data

###### Question 1: Insert new facility

```
INSERT INTO cd.facilities (facid, name, membercost, guestcost,
						   initialoutlay, monthlymaintenance) VALUES(
							 9,'Spa',20,30,100000,800);
```

###### Question 2: Insrt new facility with generated facid (pk)

```
INSERT INTO cd.facilities (facid,name, membercost, guestcost,
						   initialoutlay, monthlymaintenance) SELECT(
							 SELECT max(facid) FROM cd.facilities)+1,
							 'Spa',20,30,100000,800;
```

###### Question 3: Update existing facility cost

```
UPDATE cd.facilities
SET initialoutlay = 10000
where facid=1
```

###### Question  4: Update a facility cost based on different facility cost 
```
update cd.facilities
set 
	membercost = (select membercost*1.1 from cd.facilities where facid = 0),
	guestcost = (select guestcost*1.1 from cd.facilities where facid = 0)
where facid = 1
```

###### Question 5: Delete all entries

```
delete
from cd.bookings
where facid in (select facid from cd.bookings)
```
###### Question 6: Delete entries by condition

```
delete
from cd.members
where memid=37
```

### Basics

###### Question 7: Select with where clause

```
select facid, name, membercost, monthlymaintenance
from cd.facilities
where membercost > 0 and membercost < monthlymaintenance/50
```
###### Question 8: Select with where clause regular expression

```
select * 
from cd.facilities
where name like '%Tennis%'
```
###### Question 9: Select with where clause in range

```
select *
from cd.facilities
where facid in (1,5);
```
###### Question 10: Select by date

```
select memid, surname, firstname, joindate from cd.members
where joindate >= '2012-09-01'
```

###### Question 11: Union of two rows across tables

```
select name from cd.facilities
union
select surname from cd.members
```
### Join

###### Question 12: Select booking times through bookings/members join

```
select starttime from cd.bookings as b, cd.members as m
where b.memid = m.memid and surname = 'Farrell' and firstname = 'David'
```
###### Question 13: Select booking times through bookings/facilities join

```
select starttime as start, name from cd.facilities as fac, cd.bookings as book
where fac.facid = book.facid and starttime >= '2012-09-21' and starttime <= '2012-09-22' and name like '%Tennis Court%'
```
###### Question 14: Select of all members with recommendations

```
select starttime as start, name from cd.facilities as fac, cd.bookings as book
where fac.facid = book.facid and starttime >= '2012-09-21' and starttime <= '2012-09-22' and name like '%Tennis Court%'
```
###### Question 15: Select all members who have recommended

```
select firstname, surname from cd.members
where memid in (select recommendedby from cd.members)
order by surname
```
### Aggregation

###### Question 16: Count of recommendations grouped by member

```
select recommendedby, count(recommendedby) from cd.members
where recommendedby IS NOT NULL
group by recommendedby
order by recommendedby
```
###### Question 17: Sum of total slots grouped by facid

```
select cd.facilities.facid, sum(slots) from cd.facilities, cd.bookings
where cd.facilities.facid = cd.bookings.facid
group by cd.facilities.facid
order by cd.facilities.facid
```
###### Question 18: Sum of total slots in a specificic month

```
select cd.facilities.facid, sum(slots) as "Total Slots" from cd.facilities, cd.bookings
where cd.facilities.facid = cd.bookings.facid and cd.bookings.starttime >= '2012-09-01' and cd.bookings.starttime < '2012-10-01'
group by cd.facilities.facid
order by sum(slots)
```
###### Question 19: Sum of total slots by month

```
select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
from cd.bookings
where extract(year from starttime) = 2012
group by facid, month
order by facid, month
```
###### Question 20: Count of members with bookings

```
select count(distinct cd.members.memid) from cd.members, cd.bookings
where cd.members.memid = cd.bookings.memid
```
###### Question 21: Min booking by start time

```
select surname, firstname, cd.members.memid, min(starttime) from cd.members, cd.bookings
where starttime >= '2012-09-01' and cd.members.memid = cd.bookings.memid
group by surname,firstname,cd.members.memid
order by memid
```
###### Question 22: Window function for total member count

```
select count(*) over(), firstname, surname
from cd.members
order by joindate 
```
###### Question 23: Window function for numbered list of members

```
select count(*) over(order by joindate) as row_number, firstname, surname from cd.members
order by joindate
```
###### Question 24: Window function for highest number of booked slots

```
select facid, total from(
  select facid, sum(slots) total, rank() over(order by sum(slots) desc) rank
  from cd.bookings
  group by facid) as ranks
  where rank = 1
```
## String

###### Question 25: Format string of member names

```
select concat(surname,', ',firstname)
from cd.members
```
###### Question 26: Match phone numbers with regular expressions

```
select memid, telephone from cd.members
where telephone like '(%)%'
```
###### Question 27: Count members with first name letter

```
select substr(surname,1,1) as letter, count(*) as count
from cd.members
group by letter
order by letter

```

###### Table Setup (DDL)
```
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
```
```
CREATE TABLE cd.bookings (
  facid integer NOT NULL, 
  memid integer NOT NULL, 
  starttime TIMESTAMP NULL, 
  slots integer NOT NULL, 
  CONSTRAINT bookings_pk PRIMARY KEY (facid), 
  CONSTRAINT bookings_fk FOREIGN KEY(memid) REFERENCES cd.members(memid)
);
```
```
CREATE TABLE cd.facilities (
  facid integer NOT NULL, 
  name VARCHAR(100) NOT NULL, 
  membercost numeric NOT NULL, 
  guestcost numeric NOT NULL, 
  initialoutlay numeric NOT NULL, 
  monthlymaintenance numeric NOT NULL, 
  CONSTRAINT facilities_pk PRIMARY KEY (facid)
);
```





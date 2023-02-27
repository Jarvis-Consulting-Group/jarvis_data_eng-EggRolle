-- Q1:

INSERT INTO cd.facilities (facid, name, membercost, guestcost,
						   initialoutlay, monthlymaintenance) VALUES(
							 9,'Spa',20,30,100000,800);

-- Q2:

INSERT INTO cd.facilities (facid,name, membercost, guestcost,
						   initialoutlay, monthlymaintenance) SELECT(
							 SELECT max(facid) FROM cd.facilities)+1,
							 'Spa',20,30,100000,800;
               
-- Q3:

UPDATE cd.facilities
SET initialoutlay = 10000
where facid=1


-- Q4:

update cd.facilities
set 
	membercost = (select membercost*1.1 from cd.facilities where facid = 0),
	guestcost = (select guestcost*1.1 from cd.facilities where facid = 0)
where facid = 1

-- Q5:

delete
from cd.bookings
where facid in (select facid from cd.bookings)

-- Q6:

delete
from cd.members
where memid=37

-- Q7:

select facid, name, membercost, monthlymaintenance
from cd.facilities
where membercost > 0 and membercost < monthlymaintenance/50

-- Q8:

select * 
from cd.facilities
where name like '%Tennis%'

-- Q9:
select *
from cd.facilities
where facid in (1,5);

-- Q10:

select memid, surname, firstname, joindate from cd.members
where joindate >= '2012-09-01'

-- Q11:

select name from cd.facilities
union
select surname from cd.members

-- Q12:

select starttime from cd.bookings as b, cd.members as m
where b.memid = m.memid and surname = 'Farrell' and firstname = 'David'

-- Q13:
select starttime as start, name from cd.facilities as fac, cd.bookings as book
where fac.facid = book.facid and starttime >= '2012-09-21' and starttime <= '2012-09-22' and name like '%Tennis Court%'

-- Q14:
select starttime as start, name from cd.facilities as fac, cd.bookings as book
where fac.facid = book.facid and starttime >= '2012-09-21' and starttime <= '2012-09-22' and name like '%Tennis Court%'

-- Q15:

select firstname, surname from cd.members
where memid in (select recommendedby from cd.members)
order by surname

-- Q16:

select recommendedby, count(recommendedby) from cd.members
where recommendedby IS NOT NULL
group by recommendedby
order by recommendedby

-- Q17:
select cd.facilities.facid, sum(slots) from cd.facilities, cd.bookings
where cd.facilities.facid = cd.bookings.facid
group by cd.facilities.facid
order by cd.facilities.facid

-- Q18:
select cd.facilities.facid, sum(slots) as "Total Slots" from cd.facilities, cd.bookings
where cd.facilities.facid = cd.bookings.facid and cd.bookings.starttime >= '2012-09-01' and cd.bookings.starttime < '2012-10-01'
group by cd.facilities.facid
order by sum(slots)

-- Q19:
select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
from cd.bookings
where extract(year from starttime) = 2012
group by facid, month
order by facid, month

-- Q20:
select count(distinct cd.members.memid) from cd.members, cd.bookings
where cd.members.memid = cd.bookings.memid

-- Q21:
select surname, firstname, cd.members.memid, min(starttime) from cd.members, cd.bookings
where starttime >= '2012-09-01' and cd.members.memid = cd.bookings.memid
group by surname,firstname,cd.members.memid
order by memid

-- Q22:

select count(*) over(), firstname, surname
from cd.members
order by joindate  

-- Q23:
select count(*) over(order by joindate) as row_number, firstname, surname from cd.members
order by joindate

-- Q24:

select facid, total from(
  select facid, sum(slots) total, rank() over(order by sum(slots) desc) rank
  from cd.bookings
  group by facid) as ranks
  where rank = 1

-- Q25:
select concat(surname,', ',firstname)
from cd.members

-- Q26:

select memid, telephone from cd.members
where telephone like '(%)%'

-- Q27:

select substr(surname,1,1) as letter, count(*) as count
from cd.members
group by letter
order by letter


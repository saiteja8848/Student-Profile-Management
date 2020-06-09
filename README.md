# Student-Profile-Management
This is a console based java project,which makes use of oracle database, for performing crud operations,
 there are 3 roles involved in the
application,   "ADMIN", "STAFF" and "STUDENT"


ADMIN OPERATIONS
----------------
1.He/She can add/remove  staff or student accounts.
2.He/she can approve/reject leave requests for students
3.he/she can update a student attendance.
4.Add/Remove/Update New Branch
5.Add/Remove/Update New Subject


STAFF OPERATIONS
----------------
1.A particular proffessor/teacher will teach only 1 subject, in 1 year and in any 1 semester, so he/she can update the marks of the student
whom she/he teaching to/or to registered students to her subjects,once the examination is complted.

STUDENT OPERATIONS
------------------
1.he/she can see his marks
2.he/she can apply for leave
3.he/she can view their attendance
4.he/she can view what are the subjects in their semster


Here is the Database Information
--------------------------------

This Table is for taking registeration details once validated irrespective of the role
CREATE TABLE USERSDATA(NAME CHAR(20),MAILID VARCHAR(30),PHONENUMBER CHAR(16),ROLE CHAR(10),
PASSWORD VARCHAR(100));  



This table takes some extra information ,if the user role is staff
CREATE TABLE STAFF(MAILID VARCHAR(30) REFERENCES USERSDATA(MAILID) PRIMARY KEY,
STAFFID INT,QUALIFICATION VARCHAR(30),DEPARTMENTNAME VARCHAR(30),YEAR INT,SEMESTER INT,
TEACHINGSUBJECT CHAR(30));

This table takes some extra information ,if the user role is student
CREATE TABLE STUDENT(MAILID VARCHAR(30) REFERENCES USERSDATA(MAILID) PRIMARY KEY,ROLLNO VARCHAR(20) NOT NULL,
BRANCHNAME CHAR(30), YEAR INT NOT NULL,SEMESTER INT NOT NULL);


CREATE TABLE DEPARTMENTS(DEPARTMENTID INT PRIMARY KEY, DEPARTMENT NAME CHAR(10));

CREATE TABLE SUBJECTS(YEAR INT NOT NULL,SEMESTER INT NOT NULL,SUBJECTNAME CHAR(20) NOT NULL,STATUS INT DEFAULT 0,
DEPARTMENT REFERENCES DEPARTMENTS(DEPARTMENTNAME));

CREATE TABLE REQUESTS(ROLLNO VARCHAR(10) REFERENCES STUDENT(ROLLNO) PRIMARY KEY ,REASON VARCHAR(200) NOT NULL);

CREATE TABLE ATTENDANCE(ROLLNO VARCHAR(10) REFERENCES STUDENT(ROLLNO) PRIMARY KEY,ATTENDANCE INT);





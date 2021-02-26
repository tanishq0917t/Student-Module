create table student
(
roll int primary key auto_increment,
name char(35) not null,
fname char(35) not null,
mname char(35) not null,
standard char(10) not null,
m_no char(10) not null unique,
age int not null,
annual_income int not null,
gender char(1) not null,
aadhar_card_number char(20) not null unique
);

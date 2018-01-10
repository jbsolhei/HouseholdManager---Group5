DROP TABLE IF EXISTS Finance;
DROP TABLE IF EXISTS Shopping_tour;
DROP TABLE IF EXISTS Shopping_list;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS House_user;
DROP TABLE IF EXISTS Household;
DROP TABLE IF EXISTS Person;

CREATE TABLE Household (
houseId INTEGER AUTO_INCREMENT,
house_name VARCHAR(45) NOT NULL,
house_address VARCHAR(45) NOT NULL,
CONSTRAINT household_pk PRIMARY KEY(houseId));

CREATE TABLE Person (
userId INTEGER AUTO_INCREMENT,
email VARCHAR(45) NOT NULL,
name VARCHAR(45) NOT NULL,
password VARCHAR(45) NOT NULL,
telephone VARCHAR(20),
CONSTRAINT user_pk PRIMARY KEY(userId));

CREATE TABLE House_user (
roomnumber INTEGER,
houseId INTEGER NOT NULL,
userId INTEGER NOT NULL,
isAdmin BOOL,
CONSTRAINT house_user_pk PRIMARY KEY(houseId, userId),
FOREIGN KEY (houseId) REFERENCES Household(houseId),
FOREIGN KEY (userId) REFERENCES Person(userId)
);


CREATE TABLE Task (
date DATE,
time TIME,
description VARCHAR(100),
taskId INTEGER AUTO_INCREMENT,
houseId INTEGER NOT NULL,
userId INTEGER NOT NULL,
CONSTRAINT task_pk PRIMARY KEY(taskId),
FOREIGN KEY (houseId) REFERENCES Household(houseId),
FOREIGN KEY (userId) REFERENCES Person(userId));

CREATE TABLE Message (
text VARCHAR(100),
date DATE,
messageId INTEGER AUTO_INCREMENT,
houseId INTEGER NOT NULL,
userId INTEGER NOT NULL,
CONSTRAINT message_pk PRIMARY KEY(messageId),
FOREIGN KEY (houseId) REFERENCES Household(houseId),
FOREIGN KEY (userId) REFERENCES Person(userId));

CREATE TABLE Shopping_list (
shopping_listId INTEGER AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
houseId INTEGER NOT NULL,
FOREIGN KEY (houseId) REFERENCES Household(houseId),
CONSTRAINT shopping_listId PRIMARY KEY (shopping_listId));

/*CREATE TABLE Item (
itemId INTEGER AUTO_INCREMENT,
name VARCHAR(45),
checked BOOLEAN,
checkedBy INTEGER,
  shopping_listId INTEGER,
FOREIGN KEY (checkedBy) REFERENCES Person(UserId),
FOREIGN KEY (shopping_listId) REFERENCES Shopping_list(shopping_listId),
CONSTRAINT item_pk PRIMARY KEY (itemId));*/

CREATE TABLE Shopping_tour (
expence INTEGER,
comment VARCHAR(100),
shopping_listId INTEGER NOT NULL,
userId INTEGER NOT NULL,
CONSTRAINT shopping_tour_pk PRIMARY KEY(shopping_listId, userId),
FOREIGN KEY (shopping_listId) REFERENCES Shopping_list(shopping_listId),
FOREIGN KEY (userId) REFERENCES Person(userId));

CREATE TABLE Finance(
fromPerson INTEGER NOT NULL,
toPerson INTEGER NOT NULL,
value DOUBLE,
CONSTRAINT finance_pk PRIMARY KEY(fromPerson, toPerson),
FOREIGN KEY (fromPerson) REFERENCES Person(userId),
FOREIGN KEY (toPerson) REFERENCES Person(userId));
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Invite_token;
DROP TABLE IF EXISTS Finance;
DROP TABLE IF EXISTS Shopping_tour;
DROP TABLE IF EXISTS Shopping_list;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Chore;
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
password VARCHAR(100) NOT NULL,
telephone VARCHAR(20),
CONSTRAINT user_pk PRIMARY KEY(userId));

CREATE TABLE House_user (
houseId INTEGER NOT NULL,
userId INTEGER NOT NULL,
isAdmin BOOL,
CONSTRAINT house_user_pk PRIMARY KEY(houseId, userId),
FOREIGN KEY (houseId) REFERENCES Household(houseId),
FOREIGN KEY (userId) REFERENCES Person(userId)
);

CREATE TABLE Invite_token (
  token VARCHAR(64) NOT NULL,
  houseId INTEGER NOT NULL,
  email VARCHAR(45) NOT NULL,
  CONSTRAINT invite_token_pk PRIMARY KEY(token),
  FOREIGN KEY (houseId) REFERENCES Household(houseId)
);

CREATE TABLE Chore (
chore_datetime DATETIME,
time INTEGER,
title VARCHAR(40),
description VARCHAR(280),
choreId INTEGER AUTO_INCREMENT,
houseId INTEGER NOT NULL,
userId INTEGER NOT NULL,
done BOOLEAN NOT NULL DEFAULT 0,
CONSTRAINT chore_pk PRIMARY KEY(choreId),
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
archived INTEGER DEFAULT 0,
FOREIGN KEY (houseId) REFERENCES Household(houseId) ON DELETE CASCADE,
CONSTRAINT shopping_listId PRIMARY KEY (shopping_listId));

CREATE TABLE Item (
itemId INTEGER AUTO_INCREMENT,
name VARCHAR(45),
checked BOOLEAN,
checkedBy INTEGER,
  shopping_listId INTEGER,
FOREIGN KEY (checkedBy) REFERENCES Person(UserId) ON DELETE CASCADE,
FOREIGN KEY (shopping_listId) REFERENCES Shopping_list(shopping_listId) ON DELETE CASCADE,
CONSTRAINT item_pk PRIMARY KEY (itemId));

CREATE TABLE Shopping_trip (
shopping_tripId int(11) NOT NULL AUTO_INCREMENT,
expence varchar(45) NOT NULL,
shopping_tripName varchar(100) NOT NULL,
shopping_tripDate date NOT NULL,
comment varchar(100) NOT NULL,
userId int(11) NOT NULL,
houseId int(11) NOT NULL,
shopping_listId int(11) DEFAULT NULL,
CONSTRAINT shopping_trip_pk PRIMARY KEY(shopping_tripId),
FOREIGN KEY (shopping_listId) REFERENCES Shopping_list(shopping_listId),
FOREIGN KEY (userId) REFERENCES Person(userId),
FOREIGN KEY (houseId) REFERENCES Household(houseId));

CREATE TABLE Finance(
fromPerson INTEGER NOT NULL,
toPerson INTEGER NOT NULL,
value DOUBLE,
CONSTRAINT finance_pk PRIMARY KEY(fromPerson, toPerson),
FOREIGN KEY (fromPerson) REFERENCES Person(userId),
FOREIGN KEY (toPerson) REFERENCES Person(userId));

CREATE TABLE User_Shopping_list(
userId INTEGER NOT NULL,
shopping_listId INTEGER NOT NULL,
CONSTRAINT user_shopping_list_pk PRIMARY KEY(userId, shopping_listId),
FOREIGN KEY (userId) REFERENCES Person(userId),
FOREIGN KEY (shopping_listId) REFERENCES Shopping_list(shopping_listId));

CREATE TABLE User_Shopping_trip(
userId INTEGER NOT NULL,
shopping_tripId INTEGER NOT NULL,
CONSTRAINT user_shopping_trip_pk PRIMARY KEY(userId, shopping_tripId),
FOREIGN KEY (userId) REFERENCES Person(userId),
FOREIGN KEY (shopping_tripId) REFERENCES Shopping_trip(shopping_tripId));
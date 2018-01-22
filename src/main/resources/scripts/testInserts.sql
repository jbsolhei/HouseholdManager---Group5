INSERT INTO Person (userId, email, name, password, telephone) VALUES (1, 'trym@gmail.com', 'Trym', '123456', '11223344');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (2, 'Frank@gmail.com', 'Frank', '123456', '22334455');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (5, 'martin@gmail.com', 'Martin', 'test', '99999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (33,'householdtestuser@household.com', 'Doctor House', 'password', '81549300');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (34,'userhouseholdtest@holdhouse.com', 'John Wick', 'wordpass', '9999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (35,'inviteduser@house.com', 'Corgi Boii', 'ffffffff', '09020222');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (36, 'gubbe@epost.no', 'Gubben Gnu', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 40302010);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (37,'notadminuser@house.com', 'stronk', 'ioooio', '123123550');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (143,'ert@ertyui.no', 'blabla', 'wert', '9373942');


INSERT INTO Household (houseId,house_name,house_address) VALUES (1,'Testhouse','Testaddress 22');
INSERT INTO Household (houseId,house_name,house_address) VALUES (2,'Updatehouse','Updateaddress');
INSERT INTO Household (houseId,house_name,house_address) VALUES (3,'Deletehouse','Deleteaddress');

INSERT INTO House_user (houseId,userId,isAdmin) VALUES (1,33,0);
INSERT INTO House_user (houseId,userId,isAdmin) VALUES (1,34,0);

INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (1, 'Shopping list 1', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (2, 'Shopping list 2', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (3, 'Shopping list 3', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (4, 'Delete me', 1);


INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (1, 'Sjokolade', null, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (2, 'Melk', null, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (3, 'Taco', null, 1);

/*INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Get som milk at the store', 1, 1, 5);
INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Fix the fridge', 2, 1, 5);
INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Masturbate', 3, 1, 1);
*/
INSERT INTO Person (userId, email, name, password, telephone) VALUES (50, 'test@testesen.no', 'Test Testesen', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 90807060);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (51, 'gubbe@epost.no', 'Gubben Gnu', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 40302010);
INSERT INTO Household (houseId, house_name, house_address) VALUES (10, 'Kollektivet', 'Sverres gate 1');
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (10, 50, TRUE);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (10, 51, TRUE);
/*INSERT INTO Task (date, time, description, taskId, houseId, userId) VALUES ('2018-01-20', '10:00:00', 'Vask badet', 100, 10, 50);
INSERT INTO Task (date, time, description, taskId, houseId, userId) VALUES ('2018-01-21', '11:00:00', 'Vask gulvet', 110, 1, 1);
*/INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (10, 'Dagligvarer', 10);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (11, 'Fredagstaco', 1);

INSERT INTO Shopping_trip (expence, shopping_tripName, shopping_tripDate, comment, userId, houseId, shopping_listId) VALUES (100, 'Test', '2014-10-12', '', 50, 1, NULL );
INSERT INTO Shopping_trip (expence, shopping_tripName, shopping_tripDate, comment, userId, houseId, shopping_listId) VALUES (100, 'Test2', '2015-10-12', '', 50, 1, NULL );

INSERT INTO Message (messageId,text, date, houseId, userId) VALUES (1,'Test message',NOW(),1,33);
INSERT INTO Message (messageId,text, date, houseId, userId) VALUES (2,'Another test message',NOW(),10,50);
INSERT INTO Message (messageId,text, date, houseId, userId) VALUES (3,'Do not delete me',NOW(),2,1);
INSERT INTO Message (messageId,text, date, houseId, userId) VALUES (4,'Please delete me',NOW(),2,1);

INSERT INTO Person (userId, email, name, password, telephone) VALUES (100,'userhouseholdtest@holdhouse.com', 'John Wick', 'wordpass', '9999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (101,'inviteduser@house.com', 'Corgi Boii', 'ffffffff', '09020222');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (102, 'gubbe@epost.no', 'Gubben Gnu', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 40302010);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (103,'notadminuser@house.com', 'stronk', 'ioooio', '123123550');

INSERT INTO Finance (fromPerson, toPerson, value) VALUES (101, 100, 100);
INSERT INTO Finance (fromPerson, toPerson, value) VALUES (102, 100, 150);
INSERT INTO Finance (fromPerson, toPerson, value) VALUES (100, 103, 50);

INSERT INTO Person (userId, email, name, password, telephone) VALUES (12, 'test1@ert.no', 'ert', '123456', '4383');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (13, 'Frank1@gmail1.com', 'Frank', '123456', '233');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (14, 'asdf@hj.no', 'Martin', 'test', '4323');
INSERT INTO Finance (fromPerson, toPerson, value) VALUES (12, 13, 25);
INSERT INTO Finance (fromPerson, toPerson, value) VALUES (12, 14, 50);
INSERT INTO Finance (fromPerson, toPerson, value) VALUES (14, 13, 50);

INSERT INTO Chore (description, chore_date, time, choreId, houseId, userId)
VALUES ('Ta ut søpla', '2018-01-20', 0, 1, 10, 51);
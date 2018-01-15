INSERT INTO Person (userId, email, name, password, telephone) VALUES (1, 'trym@gmail.com', 'Trym', '123456', '11223344');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (2, 'Frank@gmail.com', 'Frank', '123456', '22334455');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (5, 'martin@gmail.com', 'Martin', 'test', '99999999');

INSERT INTO Person (userId, email, name, password, telephone) VALUES (33,'householdtestuser@household.com', 'Doctor House', 'password', '81549300');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (34,'userhouseholdtest@holdhouse.com', 'John Wick', 'wordpass', '9999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (35,'inviteduser@house.com', 'Corgi Boii', 'ffffffff', '09020222');
INSERT INTO Household (houseId,house_name,house_address) VALUES (1,'Testhouse','Testaddress 22');
INSERT INTO House_user (houseId,userId) VALUES (1,33);
INSERT INTO House_user (houseId,userId) VALUES (1,34);
INSERT INTO Household (houseId,house_name,house_address) VALUES (2,'Updatehouse','Updateaddress');
INSERT INTO Household (houseId,house_name,house_address) VALUES (3,'Deletehouse','Deleteaddress');

INSERT INTO Person (userId, email, name, password, telephone) VALUES (50, 'test@testesen.no', 'Test Testesen', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 90807060);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (51, 'gubbe@epost.no', 'Gubben Gnu', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 40302010);
INSERT INTO Household (houseId, house_name, house_address) VALUES (10, 'Kollektivet', 'Sverres gate 1');
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (10, 50, TRUE);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (10, 51, TRUE);
INSERT INTO Task (date, time, description, taskId, houseId, userId) VALUES ('2018-01-20', '10:00:00', 'Vask badet', 100, 10, 50);
INSERT INTO Task (date, time, description, taskId, houseId, userId) VALUES ('2018-01-21', '11:00:00', 'Vask gulvet', 110, 1, 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (1, 'Dagligvarer', 10);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (2, 'Fredagstaco', 1);

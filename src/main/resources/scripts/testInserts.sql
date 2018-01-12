INSERT INTO Person (userId, email, name, password, telephone) VALUES (1, 'trym@gmail.com', 'Trym', '123456', '11223344');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (2, 'Frank@gmail.com', 'Frank', '123456', '22334455');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (5, 'martin@gmail.com', 'Martin', 'test', '99999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (33,'householdtestuser@household.com', 'Doctor House', 'password', '81549300');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (34,'userhouseholdtest@holdhouse.com', 'John Wick', 'wordpass', '9999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (35,'inviteduser@house.com', 'Corgi Boii', 'ffffffff', '09020222');

INSERT INTO Household (houseId,house_name,house_address) VALUES (1,'Testhouse','Testaddress 22');
INSERT INTO Household (houseId,house_name,house_address) VALUES (2,'Updatehouse','Updateaddress');
INSERT INTO Household (houseId,house_name,house_address) VALUES (3,'Deletehouse','Deleteaddress');

INSERT INTO House_user (houseId,userId) VALUES (1,33);
INSERT INTO House_user (houseId,userId) VALUES (1,34);

INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Get som milk at the store', 1, 1, 5);
INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Fix the fridge', 2, 1, 5);
INSERT INTO Task (description, taskId, houseId, userId) VALUES ('Masturbate', 3, 1, 1);

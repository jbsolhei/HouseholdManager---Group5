INSERT INTO Person (userId, email, name, password, telephone) VALUES (1, 'trym@gmail.com', 'Trym', '123456', '11223344');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (2, 'Frank@gmail.com', 'Frank', '123456', '22334455');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (5, 'martin@gmail.com', 'Martin', 'test', '99999999');

INSERT INTO Person (userId, email, name, password, telephone) VALUES (33,'householdtestuser@household.com', 'Doctor House', 'password', '81549300');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (34,'userhouseholdtest@holdhouse.com', 'John Wick', 'wordpass', '9999999');
INSERT INTO Person (userId, email, name, password, telephone) VALUES (35,'inviteduser@house.com', 'Corgi Boii', 'ffffffff', '09020222');
INSERT INTO Household (houseId,house_name,house_address) VALUES (1,'Testhouse','Testaddress 22');
INSERT INTO House_user (houseId,userId,isAdmin) VALUES (1,33,0);
INSERT INTO House_user (houseId,userId,isAdmin) VALUES (1,34,0);
INSERT INTO Household (houseId,house_name,house_address) VALUES (2,'Updatehouse','Updateaddress');
INSERT INTO Household (houseId,house_name,house_address) VALUES (3,'Deletehouse','Deleteaddress');

INSERT INTO Person (email, name, password, telephone) VALUES ('test@testesen.no', 'Test Testesen', '$2a$12$ZyzEIbrC.0Q2.SfWhkOIGO36NHzDvUkNzvx8qV.mZFBHyZFrrCnum', 90807060);

INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (1, 'Shopping list 1', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (2, 'Shopping list 2', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (3, 'Shopping list 3', 1);

INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (1, 'Sjokolade', null, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (2, 'Melk', null, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (3, 'Taco', null, 1);

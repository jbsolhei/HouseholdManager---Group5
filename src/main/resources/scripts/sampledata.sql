






DELETE * FROM User_Shopping_trip;
DELETE * FROM Item;
DELETE * FROM Shopping_list;
DELETE * FROM Shopping_trip;
DELETE * FROM House_user;
DELETE * FROM Household;
DELETE * FROM Person;

INSERT INTO Person (userId, email, name, password, telephone) VALUES (1, 'MohammedNormann@gmail.com', 'Mohammed Normann', 1, 44225511);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (2, 'LiseStrøm@gmail.com', 'Lise Strøm', 1, 48230451);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (3, 'Per_Lian@gmail.com', 'Per Lian', 1, 992538551);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (4, 'camhl@live.no', 'Camilla Haaheim Larsen', 1, 48009921);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (5, 'tvgb@outlook.com', 'Trym Vegard Gjelseth-Borgen', 1, 99673821);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (6, 'magnus@hyll.no', 'Magnus C. H.', 1, 45002778);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (7, 'camilve@stud.ntnu.no', 'Camilla Velvin', 1, 99223316);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (8, 'olehenrik3@gmail.com', 'Ole-Henrik Fossland', 1, 48926355);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (9, 'joakim.xamooz@gmail.com', 'Joakim Solheim', 1, 40706612);
INSERT INTO Person (userId, email, name, password, telephone) VALUES (10, 'simen.m.storvik@gmail.no', 'Simen Moen Storvik', 1, 98553276);

INSERT INTO Household (houseId, house_name, house_address) VALUES (1, 'Gruppe 5 - SCRUM', 'Teknologibygget på Kalvskinnet');
INSERT INTO Household (houseId, house_name, house_address) VALUES (2, 'Familien Normann', 'Hyggegata 32, 7048 Trondheim');
INSERT INTO Household (houseId, house_name, house_address) VALUES (3, 'Kollektivet i Trondheim', 'Moholtveien 2, rett ved Moholtsenteret');

INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  4, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  5, 0);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  6, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  7, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  8, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  9, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (1,  10, 1);

INSERT INTO House_user (houseId, userId, isAdmin) VALUES (2,  4, 0);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (2,  1, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (2,  3, 0);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (2,  2, 0);

INSERT INTO House_user (houseId, userId, isAdmin) VALUES (3,  4, 0);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (3,  5, 1);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (3,  6, 0);
INSERT INTO House_user (houseId, userId, isAdmin) VALUES (3,  7, 1);

INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (1, 'Husholdning', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (2, 'Mat', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (3, 'Til badet', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (4, 'Taco-fredag!', 1);
INSERT INTO Shopping_list (shopping_listId, name, houseId) VALUES (5, 'Åre 2018', 1);

INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (1, 'Såpe', 0, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (2, 'Dopapir', 4, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (3, 'Lano', 4, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (4, 'Zalo', 4, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (5, 'Engangvåtmopp', 0, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (6, 'Hundemat', 0, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (7, 'Kluter', 0, 1);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (8, 'Oppvaskbørste', 0, 1);

INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (9, 'Kjøttdeig', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (10, 'Paprika', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (11, 'Sopp', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (12, 'Salsa', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (13, 'Guacamole', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (14, 'Ost', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (15, 'Rømme', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (16, 'Tomat', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (17, 'Sjokolade', 0, 4);
INSERT INTO Item (itemId, name, checkedBy, shopping_listId) VALUES (18, 'Brus', 0, 4);

INSERT INTO Shopping_trip (shopping_tripId, expence, shopping_tripName, shopping_tripDate, comment, userId, houseId, shopping_listId)
VALUES (1, 89, 'Rematur 3.januar', '2018-01-03', 'Kjøpte en haug med dopapir og oppvaskbørste', 4, 1, 1);
INSERT INTO Shopping_trip (shopping_tripId, expence, shopping_tripName, shopping_tripDate, comment, userId, houseId, shopping_listId)
VALUES (2, 149, 'Rematur 10.januar', '2018-01-10', 'Kjøpte lano, zalo og hunsemat', 6, 1, 1);
INSERT INTO Shopping_trip (shopping_tripId, expence, shopping_tripName, shopping_tripDate, comment, userId, houseId, shopping_listId)
VALUES (3, 45, 'Liten handletur 14.januar', '2018-01-14', 'kjøttdeig, paprika, sopp, salsa, guaq', 5, 1, 2);

INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES(5, 1);
INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES(6, 1);
INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES(7, 1);
INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES(4, 1);
INSERT INTO User_Shopping_trip (userId, shopping_tripId) VALUES(4, 2);
package database;

import classes.User;

public class UserDAO {
    public static void addNewUser(User newUser) {

        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getPhone();

        /*
        Fiks passordhasing til passordet
        Hiv koden fra DBConnector sin main-metode inn her.
        Bruk denne klassen i UserService
         */
    }
}

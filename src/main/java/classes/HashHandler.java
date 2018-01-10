package classes;

public class HashHandler {
    import org.mindrot.jbcrypt.BCrypt;
    /**
     * This is the client side hash implementation.
     * Due to the nature of client side hashing and verification, we also need to have server side hashing for added security.
     * In order to achieve client side hash verification, the client must fetch the DB-entry for "username" and do the calculations.
     */
    public class HashHandler {

        public static String makeHashFromPassword(String password){
            //We take the user's password and salt/hash it using jBCrypt.
            //The result might look like this: "$2a$12$J2oXMTxIu3/gJZmjKRecAeL/IN.0XjheOJ6TwWQZEmqO5bom61Z66"
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }

        public static boolean passwordMatchesHash(String potentialPassword, String hashFromDB){
            //We take the password provided by the user and compare it to the hash value found in the database.
            //This returns "true" if the password matches the hash.
            return (BCrypt.checkpw(potentialPassword, hashFromDB));
        }

    }
}

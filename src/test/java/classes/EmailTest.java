package classes;

import org.junit.Before;
import org.junit.Test;

public class EmailTest {

    //private Email email;
    private String recipient;

    @Before
    public void setup() {
        recipient = "joaki.xamooz@gmail.com";
    }

    @Test
    public void send_plain_mail_to_user_test() {
        // Test passes if method doesn't fail.
        Email.sendMail(recipient, "Test", "test \n- Household Manager");
    }
}

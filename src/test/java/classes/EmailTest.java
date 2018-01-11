package classes;

import org.junit.Before;
import org.junit.Test;

public class EmailTest {

    //private Email email;
    private String[] recipients;

    @Before
    public void setup() {
        recipients = new String[1];
        recipients[0] = "joaki.xamooz@gmail.com";
    }

    @Test
    public void send_plain_mail_to_user_test() {
        // Test passes if method doesn't fail.
        Email.sendMail(recipients, "Test", "test \n- Household Manager");
    }
}

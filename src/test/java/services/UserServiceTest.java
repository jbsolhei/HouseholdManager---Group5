package services;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.given;

public class UserServiceTest {
    @BeforeClass
    public static void setUp() throws Exception {

            String port = System.getProperty("server.port");
            if (port == null) {
                RestAssured.port = Integer.valueOf(8080);
            }
            else{
                RestAssured.port = Integer.valueOf(port);
            }


            String basePath = System.getProperty("server.base");
            if(basePath==null){
                basePath = "/hhapp/res/";
            }
            RestAssured.basePath = basePath;

            String baseHost = System.getProperty("server.host");
            if(baseHost==null){
                baseHost = "http://localhost";
            }
            RestAssured.baseURI = baseHost;

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addUser() throws Exception {

    }

    @Test
    public void getUser() throws Exception {
        given().
                expect().
                statusCode(200).
                body("email", equalTo("trym@gmail.com")).
                body("name", equalTo("Trym")).
                body("telephone", equalTo("11223344")).
                body("password", equalTo("123456")).
                when().
                get("/user/1");
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void authenticateLogin() throws Exception {
    }

}
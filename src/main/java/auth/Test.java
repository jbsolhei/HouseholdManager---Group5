package auth;

import javax.inject.Inject;

public class Test {

    @Inject
    @AuthenticatedUser
    private AuthenticatedUserData data;

    public Test() {

    }

    public void doThing() {
        System.out.println("[Test] Data: " + data.getUserId());
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.doThing();
    }
}

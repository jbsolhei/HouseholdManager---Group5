package auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    @Mock
    private ContainerRequestContext mockedRequestContext;

    @Test
    public void testValidSessionToken() {
        Session testSession = Sessions.generateSession(1);
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        when(mockedRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + testSession.getToken());

        try {
            authenticationFilter.filter(mockedRequestContext);
        } catch (IOException ex) {
            fail("AuthenticationFilter threw IOException: " + ex.getMessage());
        }

        verify(mockedRequestContext, never()).abortWith(anyObject());
        verify(mockedRequestContext).setProperty(eq("session.token"), eq(testSession.getToken()));
        verify(mockedRequestContext).setProperty(eq("session.userId"), eq(testSession.getUserId()));
    }

    @Test
    public void testInvalidSessionToken() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        when(mockedRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalidtoken");

        try {
            authenticationFilter.filter(mockedRequestContext);
        } catch (IOException ex) {
            fail("AuthenticationFilter threw IOException: " + ex.getMessage());
        }

        verify(mockedRequestContext).abortWith(anyObject());
        verify(mockedRequestContext, never()).setProperty(anyString(), anyObject());
    }

    @Test
    public void testNoAuthorizationHeader() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        when(mockedRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        try {
            authenticationFilter.filter(mockedRequestContext);
        } catch (IOException ex) {
            fail("AuthenticationFilter threw IOException: " + ex.getMessage());
        }

        verify(mockedRequestContext).abortWith(anyObject());
        verify(mockedRequestContext, never()).setProperty(anyString(), anyObject());
    }
}
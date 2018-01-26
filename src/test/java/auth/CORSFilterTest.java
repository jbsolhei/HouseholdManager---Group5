package auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CORSFilterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ContainerRequestContext mockedRequestContext;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ContainerResponseContext mockedResponseContext;

    @Test
    public void testCORSFilter() {
        CORSFilter filter = new CORSFilter();

        try {
            filter.filter(mockedRequestContext, mockedResponseContext);
        } catch (IOException ex) {
            fail("CORSFilter threw IOException: " + ex.getMessage());
        }

        verify(mockedResponseContext.getHeaders()).add(eq("Access-Control-Allow-Origin"), anyString());
        verify(mockedResponseContext.getHeaders()).add(eq("Access-Control-Allow-Headers"), anyString());
    }
}
package space.rebot.micro.userservice.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import space.rebot.micro.config.PermissionsConfig;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.repository.SessionsRepository;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest {

    @Mock
    private SessionsRepository sessionsRepository;

    @Mock
    private PermissionsConfig permissionsConfig;

    @InjectMocks
    private TokenFilter tokenFilter;


    @Test
    @DisplayName("Unauthorized user tries login/code")
    public void doFilter_shouldDoFilter_whenUnauthorizedUser () throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/auth/login");
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);

        tokenFilter.doFilter(req, res, chain);
        assertNotEquals(res.getErrorMessage(), "Bad request");
        assertNotEquals(res.getErrorMessage(), "The token is not valid.");
    }

    @Test
    @DisplayName("authorized user connects without authorization header")
    public void doFilter_shouldReturnError_whenDontHaveHeader() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/cart.*");
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();


        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);

        tokenFilter.doFilter(req, res, chain);
        assertEquals(res.getErrorMessage(), "Bad request");
        assertNotEquals(res.getErrorMessage(), "The token is not valid.");

    }

    @Test
    @DisplayName("authorized user connects with invalid token")
    public void doFilter_shouldReturnError_whenInvalidToken() throws ServletException, IOException {
        String token = "testToken";
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();


        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };

        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);
        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(null);

        tokenFilter.doFilter(req, res, chain);
        assertEquals(res.getErrorMessage(), "The token is not valid.");
        assertNotEquals(res.getErrorMessage(), "Bad request");
    }

    @Test
    @DisplayName("authorized user connects with valid token and valid headers")
    public void doFilter_shouldDoFilter_whenAuthorizedUser() throws ServletException, IOException {
        String token = "testToken";
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        Session session = Mockito.mock(Session.class);


        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);
        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(session);

        tokenFilter.doFilter(req, res, chain);
        assertNotEquals(res.getErrorMessage(), "Bad request");
        assertNotEquals(res.getErrorMessage(), "The token is not valid.");
    }
}

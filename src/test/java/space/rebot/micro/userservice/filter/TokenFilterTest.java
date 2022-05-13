package space.rebot.micro.userservice.filter;

import org.junit.jupiter.api.BeforeEach;
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
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.repository.SessionsRepository;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest {

    @Mock
    private SessionsRepository sessionsRepository;

    @Mock
    private PermissionsConfig mockPermissionsConfig;

    @InjectMocks
    private TokenFilter tokenFilter;

    private MockHttpServletRequest req;
    private MockHttpServletResponse res;
    private MockFilterChain chain;

    private final PermissionsConfig permissionsConfig = new PermissionsConfig();
    private final String[] unauthorized = permissionsConfig.getAllowedUrls()
            .get(RoleConfig.UNAUTHORIZED.toString());

    @BeforeEach
    public void init() {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        chain = Mockito.mock(MockFilterChain.class);
    }

    @Test
    @DisplayName("Unauthorized user tries login/code")
    public void doFilter_shouldDoFilter_whenUnauthorizedUser () throws ServletException, IOException {
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);

        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(mockPermissionsConfig.getAllowedUrls()).thenReturn(map);

        req.setRequestURI(".*/api/v1/auth/login");

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(1)).doFilter(req,res);
    }

    @Test
    @DisplayName("authorized user connects without authorization header")
    public void doFilter_shouldReturnError_whenDontHaveHeader() throws ServletException, IOException {
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);

        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(mockPermissionsConfig.getAllowedUrls()).thenReturn(map);

        req.setRequestURI(".*/api/v1/cart.*");

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(0)).doFilter(req,res);
        assertNotEquals(res.getErrorMessage(), "The token is not valid.");

    }

    @Test
    @DisplayName("authorized user connects with invalid token")
    public void doFilter_shouldReturnError_whenInvalidToken() throws ServletException, IOException {
        String token = "testToken";
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);

        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(mockPermissionsConfig.getAllowedUrls()).thenReturn(map);
        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(null);

        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(0)).doFilter(req,res);
        assertNotEquals(res.getErrorMessage(), "Bad request");
    }

    @Test
    @DisplayName("authorized user connects with valid token and valid headers")
    public void doFilter_shouldDoFilter_whenAuthorizedUser() throws ServletException, IOException {
        String token = "testToken";
        Session session = Mockito.mock(Session.class);
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);

        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(mockPermissionsConfig.getAllowedUrls()).thenReturn(map);
        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(session);

        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(1)).doFilter(req,res);
    }
}

package space.rebot.micro.userservice.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import space.rebot.micro.config.PermissionsConfig;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.repository.SessionsRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest {
    @Spy
    @Autowired
    private PermissionsConfig permissionsConfig;

    @Mock
    private SessionsRepository sessionsRepository;

    @InjectMocks
    private TokenFilter tokenFilter;

    private MockHttpServletRequest req;
    private MockHttpServletResponse res;
    private MockFilterChain chain;

    @BeforeEach
    public void init() {
        req = new MockHttpServletRequest();
        res = Mockito.mock(MockHttpServletResponse.class);
        chain = Mockito.mock(MockFilterChain.class);
    }

    @Test
    @DisplayName("Unauthorized user tries login/code")
    public void doFilter_shouldDoFilter_whenUnauthorizedUser () throws ServletException, IOException {
        req.setRequestURI(".*/api/v1/auth/login");

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(1)).doFilter(req,res);
    }

    @Test
    @DisplayName("authorized user connects without authorization header")
    public void doFilter_shouldReturnError_whenDontHaveHeader() throws ServletException, IOException {
        req.setRequestURI(".*/api/v1/cart.*");

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.never()).doFilter(req,res);
        Mockito.verify(res, Mockito.times(1))
                .sendError(Mockito.eq(HttpServletResponse.SC_BAD_REQUEST), Mockito.anyString());
    }

    @Test
    @DisplayName("authorized user connects with invalid token")
    public void doFilter_shouldReturnError_whenInvalidToken() throws ServletException, IOException {
        String token = "testToken";

        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(null);

        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.never()).doFilter(req,res);
        Mockito.verify(res, Mockito.times(1))
                .sendError(Mockito.eq(HttpServletResponse.SC_UNAUTHORIZED), Mockito.anyString());
    }

    @Test
    @DisplayName("authorized user connects with valid token and valid headers")
    public void doFilter_shouldCheckValidTokenAndHeaders() throws ServletException, IOException {
        String token = "testToken";
        Session session = Mockito.mock(Session.class);

        Mockito.when(sessionsRepository.getByToken(token)).thenReturn(session);

        req.setRequestURI(".*/api/v1/cart.*");
        req.addHeader("authorization", token);

        tokenFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(1)).doFilter(req,res);
    }
}

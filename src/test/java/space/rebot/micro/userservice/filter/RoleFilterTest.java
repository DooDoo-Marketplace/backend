package space.rebot.micro.userservice.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import space.rebot.micro.config.PermissionsConfig;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoleFilterTest {
    @Spy
    @Autowired
    private PermissionsConfig permissionsConfig;

    @InjectMocks
    private RoleFilter roleFilter;

    private MockHttpServletRequest req;
    private MockHttpServletResponse res;
    private MockFilterChain chain;


    @BeforeEach
    public void init() {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        chain = Mockito.mock(MockFilterChain.class);
    }

    @Test
    @DisplayName("Unauthorized user tries login/code")
    public void doFilter_shouldDoFilter_whenSessionNull() throws ServletException, IOException {
        req.setAttribute(Session.SESSION, null);

        roleFilter.doFilter(req, res, chain);
    }

    @Test
    @DisplayName("Authorized user doesn't have permission")
    public void doFilter_shouldSendError_whenUserDoesntHavePermission() throws ServletException, IOException {
        List<Role> roles = new ArrayList<>();

        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);

        Mockito.when(user.getRoles()).thenReturn(roles);
        Mockito.when(session.getUser()).thenReturn(user);

        req.setRequestURI(".*/api/v1/fake.*");
        req.setAttribute(Session.SESSION, session);

        roleFilter.doFilter(req, res, chain);
        Mockito.verify(chain,Mockito.never()).doFilter(req,res);
    }

    @Test
    @DisplayName("Authorized user has permission")
    public void doFilter_shouldCheckValidUrlAndRole() throws ServletException, IOException {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        role.setName(RoleConfig.ROLE_USER.toString());
        roles.add(role);

        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);

        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getRoles()).thenReturn(roles);

        req.setRequestURI(".*/api/v1/cart.*");
        req.setAttribute(Session.SESSION, session);

        roleFilter.doFilter(req, res, chain);
        Mockito.verify(chain, Mockito.times(1)).doFilter(req,res);
    }

}

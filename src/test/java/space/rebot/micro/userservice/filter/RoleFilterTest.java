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
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class RoleFilterTest {

    @Mock
    private PermissionsConfig permissionsConfig;

    @InjectMocks
    private RoleFilter roleFilter;

    @Test
    @DisplayName("Unauthorized user tries login/code")
    public void doFilter_shouldDoFilter_whenSessionNull() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        req.setAttribute(Session.SESSION, null);

        roleFilter.doFilter(req, res, chain);
    }

    @Test
    @DisplayName("Authorized user doesn't have permission")
    public void doFilter_shouldSendError_whenUserDoesntHavePermission() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/fake.*");
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);

        req.setAttribute(Session.SESSION, session);

        Mockito.when(session.getUser()).thenReturn(user);

        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        role.setName("User");
        roles.add(role);

        Mockito.when(user.getRoles()).thenReturn(roles);

        roleFilter.doFilter(req, res, chain);
        assertEquals(res.getErrorMessage(), "Forbidden");
    }

    @Test
    @DisplayName("Authorized user has permission")
    public void doFilter_shouldDoFilter() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI(".*/api/v1/cart.*");
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        Session session = Mockito.mock(Session.class);
        User user = Mockito.mock(User.class);

        req.setAttribute(Session.SESSION, session);

        Mockito.when(session.getUser()).thenReturn(user);

        String[] unauthorized = {
                ".*/api/v1/auth/login",
                ".*/api/v1/auth/code"
        };
        String[] userPermission = {
                ".*/api/v1/hello.*",
                ".*/api/v1/cart.*",
                ".*/api/v1/order.*",
                ".*/api/v1/group.*",
                ".*/api/v1/user.*",
                ".*/api/v1/auth/logout.*",
                ".*/api/v1/review.*",
                ".*/api/v1/favorite.*"
        };
        HashMap<String, String[]> map = Mockito.mock(HashMap.class);
        Mockito.when(map.get("UNAUTHORIZED")).thenReturn(unauthorized);
        Mockito.when(map.get("User")).thenReturn(userPermission);
        Mockito.when(permissionsConfig.getAllowedUrls()).thenReturn(map);

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        role.setName("User");
        roles.add(role);

        Mockito.when(user.getRoles()).thenReturn(roles);

        roleFilter.doFilter(req, res, chain);
        assertNotEquals(res.getErrorMessage(), "Forbidden");
    }

}

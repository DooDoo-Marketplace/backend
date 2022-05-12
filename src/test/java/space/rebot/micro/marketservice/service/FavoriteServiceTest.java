package space.rebot.micro.marketservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private SkuRepository skuRepository;

    @Mock
    private HttpServletRequest context;


    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    void getUserFavorite_shouldReturnEmptyList() {
        User user = Mockito.mock(User.class);
        Session session = Mockito.mock(Session.class);

        Mockito.when(skuRepository.getFavoriteSkusByUserId(Mockito.any())).thenReturn(null);
        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(Mockito.anyLong());
        assertEquals(favoriteService.getUserFavorite(), Collections.emptyList());
    }

    @Test
    void addFavorite_shouldThrowSkuNotFoundException() {

    }

    @Test
    void deleteFavorite() {
    }
}
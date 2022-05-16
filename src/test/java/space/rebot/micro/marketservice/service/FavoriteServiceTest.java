package space.rebot.micro.marketservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.marketservice.exception.SkuIsAlreadyFavoriteException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

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
    void addFavorite_shouldThrowSkuIsAlreadyFavoriteException() {
        User user = Mockito.mock(User.class);
        Session session = Mockito.mock(Session.class);
        Long userId = 1L;
        Long skuId = 2L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(skuRepository.existsFavoriteBySkuId(userId, skuId)).thenReturn(true);

        assertThrows(SkuIsAlreadyFavoriteException.class, () -> favoriteService.addFavorite(skuId));
    }

    @Test
    void addFavorite_shouldThrowSkuNotFoundException() {
        User user = Mockito.mock(User.class);
        Session session = Mockito.mock(Session.class);
        Long userId = 1L;
        Long skuId = 2L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(skuRepository.existsFavoriteBySkuId(userId, skuId)).thenReturn(false);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(null);

        assertThrows(SkuNotFoundException.class, () -> favoriteService.addFavorite(skuId));
    }

    @Test
    void deleteFavorite_shouldThrowSkuNotFoundException() {
        User user = Mockito.mock(User.class);
        Session session = Mockito.mock(Session.class);
        Long userId = 1L;
        Long skuId = 2L;

        Mockito.when(context.getAttribute(Mockito.any())).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(user);
        Mockito.when(skuRepository.getSkuById(skuId)).thenReturn(null);

        assertThrows(SkuNotFoundException.class, () -> favoriteService.deleteFavorite(skuId));
    }
}
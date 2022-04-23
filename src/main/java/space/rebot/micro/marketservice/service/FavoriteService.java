package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.UsersRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private HttpServletRequest context;

    public List<Sku> getUserFavorite() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        List<Sku> userFavorite = skuRepository.getSkusByIds(usersRepository.getFavoriteIdsByUserId(user.getId()));
        if (userFavorite == null) {
            return Collections.emptyList();
        }
        return userFavorite;
    }

    public void addFavorite(Long skuId) throws SkuNotFoundException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        usersRepository.addFavoriteByUserIdAndSkuId(user.getId(), skuId);
    }

    public void deleteFavorite(Long favoriteId) {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        usersRepository.deleteFavoriteByUserIdAndFavoriteId(user.getId(), favoriteId);
    }
}

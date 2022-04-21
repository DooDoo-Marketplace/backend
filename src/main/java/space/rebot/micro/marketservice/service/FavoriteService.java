package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.UsersRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class FavoriteService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private HttpServletRequest context;
    @Transactional
    public List<Sku> getUserFavorite() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        List<Sku> userFavorite = user.getFavorite();
        if (userFavorite == null) {
            return Collections.emptyList();
        }
        return userFavorite;
    }

}

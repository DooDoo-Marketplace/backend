package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.exception.SkuIsAlreadyFavoriteException;
import space.rebot.micro.marketservice.exception.SkuNotFoundException;
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

@Service
public class FavoriteService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private SkuMapper skuMapper;

    public List<SkuDTO> getUserFavorite() {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        List<Sku> userFavorite = skuRepository.getFavoriteSkusByUserId(user.getId());
        if (userFavorite == null) {
            return Collections.emptyList();
        }
        return userFavorite.stream()
                .map(sku -> skuMapper.mapToSkuDto(sku))
                .collect(Collectors.toList());
    }

    public void addFavorite(Long skuId) throws SkuNotFoundException, SkuIsAlreadyFavoriteException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        if (skuRepository.existsFavoriteBySkuId(user.getId(), skuId)) {
            throw new SkuIsAlreadyFavoriteException();
        }
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        usersRepository.addFavoriteByUserIdAndSkuId(user.getId(), skuId);
    }

    public void deleteFavorite(Long skuId) throws SkuNotFoundException {
        User user = ((Session) context.getAttribute(Session.SESSION)).getUser();
        Sku sku = skuRepository.getSkuById(skuId);
        if (sku == null) {
            throw new SkuNotFoundException();
        }
        usersRepository.deleteFavoriteByUserIdAndFavoriteId(user.getId(), skuId);
    }
}

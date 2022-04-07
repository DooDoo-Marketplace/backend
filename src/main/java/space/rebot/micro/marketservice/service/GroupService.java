package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.marketservice.repository.GroupRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    private final int groupHoursLifeTime = 24;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private PaymentService paymentService;

    // что происходит под капотом после того, как юзер нажал оформить заказ
    //1. проверка соответствий регионов доставки и ску
    //2. проверка наличия количества товара на складе
    //3. оплата
    //4. поиск группы (по ссылке или нет)
    //5. добавление в группу, уменьшение количества товара на складе и увеличение в группе
    // (если юзер уже есть в этой группе, обработать это обновление количества товара)
    //6. проверка, не набралось ли товара в группе
    //7. если набралось, то запустить шедулер через час (в группу же можно будет вступать только по ссылке)
    //8. отработка шедулера (если из группы никто не ушел и товара хватает), создается заказ

    @Transactional
    public Map<Long, Long> findGroups(List<Cart> carts, Map<Long, Long> skuGroup, User user, String region) throws ParseException {
        paymentService.spend();
        //ответ для фронта, группа под каждый товар в корзине
        Map<Long, Long> userSkuGroups = new HashMap<>();
        for (Cart cart : carts) {
            Long skuId = cart.getSku().getId();
            if (skuGroup.containsKey(skuId)) {
                userSkuGroups.put(skuId, joinGroup(cart.getCount(), user, skuGroup.get(skuId)));
            } else {
                userSkuGroups.put(skuId, joinGroup(skuId, cart.getCount(), region, user));
            }
            cartRepository.updateCartStatus(user.getId(), skuId,
                    CartStatusEnum.IN_GROUP.getId(), CartStatusEnum.ACTIVE.getId());
            skuRepository.updateSkuCount(cart.getCount(), skuId);
        }
        return userSkuGroups;
    }

    private Long joinGroup(Long skuId, int cnt, String region, User user) {
        Group group = groupRepository.getGroup(skuId, region);
        if (group == null) {
            group = new Group(skuRepository.getSkuById(skuId), dateService.utcNow(),
                    dateService.addTimeForCurrent(groupHoursLifeTime), cnt, region);
            group.getUsers().add(user);
            groupRepository.save(group);
        } else {
            addUserToGroup(group, user, cnt);
        }
        return group.getId();
    }

    private void addUserToGroup(Group group, User user, int cnt) {
        if (groupRepository.existsUserInGroup(group.getId(), user.getId()) == null) {
            group.getUsers().add(user);
            group.setCount(group.getCount() + cnt);
            groupRepository.save(group);
            cartRepository.updateCartStatus(user.getId(), group.getSku().getId(),
                    CartStatusEnum.IN_GROUP.getId(), CartStatusEnum.ACTIVE.getId());
        } else {
            groupRepository.updateGroupCount(group.getCount() + cnt, group.getId());
            cartRepository.updateSkuCnt(user.getId(), group.getSku().getId(), cnt,
                    CartStatusEnum.IN_GROUP.getId());
        }
    }

    private Long joinGroup(int cnt, User user, Long groupId) {
        Group group = groupRepository.getGroup(groupId);
        addUserToGroup(group, user, cnt);
        return group.getId();
    }

}

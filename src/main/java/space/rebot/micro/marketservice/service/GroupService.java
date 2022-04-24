package space.rebot.micro.marketservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.dto.GroupResponseDTO;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.exception.*;
import space.rebot.micro.marketservice.mapper.GroupMapper;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.marketservice.repository.GroupRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.DateService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Value("${groups.group-life-time:24}")
    private int groupHoursLifeTime;

    private final Logger logger = LogManager.getLogger("MyLogger");

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private GroupMapper groupMapper;

    public List<GroupResponseDTO> findGroups(List<Cart> carts, Map<Long, UUID> skuGroup,
                                             User user, String region) throws GroupSearchException {
        //ответ для фронта, группа под каждый товар в корзине
        List<Group> groups = new ArrayList<>();
        try {
            find(carts, skuGroup, user, region, groups);
        } catch (RuntimeException e) {
            unfreezeSkus(carts);
            logger.error(e.getStackTrace());
            throw new GroupSearchException("Error when trying to find groups");
        }
        return groups.stream().map(groupMapper::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void find(List<Cart> carts, Map<Long, UUID> skuGroup, User user,
                     String region, List<Group> groups) {
        for (Cart cart : carts) {
            // для каждого элемента корзины проверяем, если есть ссылка на вступление в группу, то вступаем, а иначе вступаем в рандомную группу
            Long skuId = cart.getSku().getId();
            if (skuGroup.containsKey(skuId)) {
                groups.add(joinGroup(cart.getCount(), user, skuGroup.get(skuId), cart));
            } else {
                groups.add(joinGroup(skuId, cart.getCount(), region, user, cart));
            }
            // помечаем товар из корзины в группу
            cartRepository.updateCartStatusById(cart.getId(), CartStatusEnum.IN_GROUP.getId());
        }
    }

    private void unfreezeSkus(List<Cart> carts) {
        carts.forEach(cart -> skuRepository.updateSkuCount(cart.getCount(), cart.getSku().getId()));
    }


    private Group joinGroup(Long skuId, int cnt, String region, User user, Cart cart) {
        Group group = groupRepository.getGroup(skuId, region);
        if (group == null) {
            group = new Group(skuRepository.getSkuById(skuId), dateService.utcNow(),
                    dateService.addTimeForCurrent(groupHoursLifeTime), cnt, region);
            group.getUsers().add(user);
            groupRepository.save(group);
        } else {
            addUserToGroup(group, user, cnt, cart);
        }
        return group;
    }

    private Group joinGroup(int cnt, User user, UUID groupId, Cart cart) {
        Group group = groupRepository.getGroup(groupId);
        addUserToGroup(group, user, cnt, cart);
        return group;
    }

    private void addUserToGroup(Group group, User user, int cnt, Cart cart) {
         /* если пользователя нет в этой группе, то добавляем его туда и помечаем его товар из корзины, что он в группе
         увеличиваем количество товара в группе
         если уже есть в этой группе, то в корзине удаляем этот товар и увеличиваем, количество товара в группе у этого пользователя*/
        if (groupRepository.existsUserInGroup(group.getId(), user.getId()) == null) {
            group.getUsers().add(user);
            group.setCount(group.getCount() + cnt);
            groupRepository.save(group);
        } else {
            groupRepository.updateGroupCount(group.getCount() + cnt, group.getId());
            cartRepository.updateCartStatusById(cart.getId(), CartStatusEnum.DELETED.getId());
            cartRepository.updateSkuCnt(user.getId(), group.getSku().getId(), cnt,
                    CartStatusEnum.IN_GROUP.getId(), false);
        }
    }

    @Transactional
    public void leaveGroup(UUID groupId, User user) throws InvalidGroupException {
        Set<Group> userGroups = new HashSet<>(groupRepository.getUserGroups(user.getId()));

        Group group = userGroups.stream().filter(g -> g.getId().equals(groupId)).findFirst()
                .orElseThrow(() -> new InvalidGroupException("INVALID_GROUP"));

        Sku sku = group.getSku();
        Cart cart = cartRepository.getCartIdBySkuCartStatus(user.getId(), sku.getId(),
                CartStatusEnum.IN_GROUP.getId(), false);
        groupRepository.deleteUserFromGroup(group.getId(), user.getId());
        group.setCount(group.getCount() - cart.getCount());
        if (group.getCount() == 0) {
            groupRepository.delete(group);
        } else {
            groupRepository.save(group);
        }
        cartRepository.updateCartStatusById(cart.getId(), CartStatusEnum.DELETED.getId());
        skuRepository.updateSkuCount(cart.getCount(), sku.getId());
    }

    public List<GroupResponseDTO> getUserGroups(User user) {
        return groupRepository.getUserGroups(user.getId())
                .stream().map(groupMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}

package space.rebot.micro.orderservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.orderservice.exception.GroupSearchException;
import space.rebot.micro.orderservice.exception.InvalidGroupException;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.orderservice.model.Group;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.orderservice.repository.GroupRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;
import space.rebot.micro.userservice.model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private SkuRepository skuRepository;

    @InjectMocks
    private GroupService groupService;

    @Test
    public void findGroups_shouldRollbackTransaction() {
        Map skuGroup = Mockito.mock(Map.class);
        Cart cart = Mockito.mock(Cart.class);
        Sku sku = Mockito.mock(Sku.class);
        List<Cart> carts = Stream.of(cart).collect(Collectors.toList());
        Long skuId = 1L;
        int cnt = 1;

        Mockito.when(cart.getSku()).thenReturn(sku);
        Mockito.when(sku.getId()).thenReturn(skuId);
        Mockito.when(cart.getCount()).thenReturn(cnt);
//        Mockito.when(skuRepository.updateSkuCount());
        Mockito.when(skuGroup.containsKey(skuId)).thenThrow(RuntimeException.class);

        //double check
        assertThrows(GroupSearchException.class, () -> {
            groupService.findGroups(carts, skuGroup, null, null);
        });
        Mockito.verify(skuRepository, Mockito.times(1)).updateSkuCount(cnt, skuId);
    }

    @Test
    public void leaveGroup_shouldThrowException() {
        User user = Mockito.mock(User.class);
        Group userGroup = Mockito.mock(Group.class);
        List<Group> groups = Stream.of(userGroup).collect(Collectors.toList());
        Long userId = 1L;
        UUID uuid = UUID.randomUUID();
        UUID falseUuid = UUID.randomUUID();

        Mockito.when(userGroup.getId()).thenReturn(uuid);
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(groupRepository.getUserGroups(userId)).thenReturn(groups);

        assertThrows(InvalidGroupException.class, () -> groupService.leaveGroup(falseUuid, user));
    }
}


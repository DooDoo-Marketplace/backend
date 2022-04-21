package space.rebot.micro.marketservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.marketservice.model.Cart;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.GroupRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PreGroupCheckerServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private PreGroupCheckerService checkerService;

    //checkUserCartSkuCount

    @Test
    public void checkUserCartSkuCount_shouldReturnAnEmptyInvalidList_whenCartIsEmpty() {
        List<Long> invalidIds = checkerService.checkUserCartSkuCount(Collections.emptyList());
        assertTrue(invalidIds.isEmpty());
    }

    @Test
    public void checkUserCartSkuCount_shouldReturnAnEmptyInvalidList() {
        Cart cart = Mockito.mock(Cart.class);
        Sku sku = Mockito.mock(Sku.class);
        List<Cart> carts = Stream.of(cart).collect(Collectors.toList());

        Mockito.lenient().when(sku.getId()).thenReturn(1L);
        Mockito.when(sku.getCount()).thenReturn(5);

        Mockito.when(cart.getSku()).thenReturn(sku);
        Mockito.when(cart.getCount()).thenReturn(5);

        List<Long> invalidIds = checkerService.checkUserCartSkuCount(carts);
        assertTrue(invalidIds.isEmpty());
    }

    @Test
    public void checkUserCartSkuCount_shouldReturnAnInvalidList() {
        Cart cart = Mockito.mock(Cart.class);
        Sku sku = Mockito.mock(Sku.class);
        List<Cart> carts = Stream.of(cart).collect(Collectors.toList());

        Mockito.when(sku.getId()).thenReturn(1L);
        Mockito.when(sku.getCount()).thenReturn(5);

        Mockito.when(cart.getSku()).thenReturn(sku);
        Mockito.when(cart.getCount()).thenReturn(7);

        List<Long> invalidIds = checkerService.checkUserCartSkuCount(carts);
        assertEquals(sku.getId(), invalidIds.get(0));
    }

    //checkUserCartRegions

    @Test
    public void checkUserCartRegions_shouldReturnAnEmptyInvalidList_whenCartIsEmpty() {
        List<Long> invalidIds = checkerService.checkUserCartRegions(Collections.emptyList(), null);
        assertTrue(invalidIds.isEmpty());
    }

    @Test
    public void checkUserCartRegions_shouldReturnAnEmptyInvalidList() {
        Cart cart = Mockito.mock(Cart.class);
        Sku sku = Mockito.mock(Sku.class);
        List<Cart> carts = Stream.of(cart).collect(Collectors.toList());

        Mockito.when(sku.getRegion()).thenReturn("Region");
        Mockito.lenient().when(sku.getId()).thenReturn(1L);

        Mockito.when(cart.getSku()).thenReturn(sku);

        List<Long> invalidIds = checkerService.checkUserCartRegions(carts, "Region");
        assertTrue(invalidIds.isEmpty());
    }

    @Test
    public void checkUserCartRegions_shouldReturnAnInvalidList() {
        Cart cart = Mockito.mock(Cart.class);
        Sku sku = Mockito.mock(Sku.class);
        List<Cart> carts = Stream.of(cart).collect(Collectors.toList());

        Mockito.when(sku.getRegion()).thenReturn("Region");
        Mockito.when(sku.getId()).thenReturn(1L);

        Mockito.when(cart.getSku()).thenReturn(sku);

        List<Long> invalidIds = checkerService.checkUserCartRegions(carts, "Invalid Region");
        assertEquals(sku.getId(), invalidIds.get(0));
    }

    // checkSkuGroups

    @Test
    public void checkSkuGroups_shouldReturnAnEmptyInvalidList_whenGroupsAreEmpty() {
        List<Long> invalidIds = checkerService.checkSkuGroups(Collections.emptyMap());
        assertTrue(invalidIds.isEmpty());
    }

    @Test
    public void checkSkuGroups_shouldReturnInvalidList_whenGroupNotFound() {
        Map<Long, UUID> skuGroups = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        Long skuId = 2048L;
        skuGroups.put(skuId, uuid);

        Mockito.when(groupRepository.getGroup(uuid)).thenReturn(null);

        List<Long> invalidIds = checkerService.checkSkuGroups(skuGroups);
        assertEquals(skuId, invalidIds.get(0));
    }

    @Test
    public void checkSkuGroups_shouldReturnInvalidList_whenGroupForOtherSku() {
        Map<Long, UUID> skuGroups = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        Long skuId1 = 2048L;
        Long skuId2 = 6048L;
        skuGroups.put(skuId1, uuid);
        Group group = Mockito.mock(Group.class);
        Sku sku = Mockito.mock(Sku.class);

        Mockito.when(sku.getId()).thenReturn(skuId2);
        Mockito.when(group.getSku()).thenReturn(sku);
        Mockito.when(groupRepository.getGroup(uuid)).thenReturn(group);

        List<Long> invalidIds = checkerService.checkSkuGroups(skuGroups);
        assertEquals(skuId1, invalidIds.get(0));
    }


}
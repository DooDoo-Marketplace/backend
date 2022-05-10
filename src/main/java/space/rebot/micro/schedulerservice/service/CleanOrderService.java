package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.orderservice.repository.GroupRepository;
import space.rebot.micro.orderservice.enums.OrdersStatusEnum;
import space.rebot.micro.orderservice.model.GroupsOrders;
import space.rebot.micro.orderservice.repository.GroupsOrdersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CleanOrderService {
    @Value("${scheduler.delete.batch}")
    private int BATCH;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupsOrdersRepository groupsOrdersRepository;

    @Transactional
    public void cleanOrder() {
        List<Integer> status = new ArrayList<>();
        status.add(OrdersStatusEnum.CANCELLED.getId(), OrdersStatusEnum.COMPLETED.getId());
        List<UUID> ids;
        List<GroupsOrders> groupsOrders;
        do {
            groupsOrders = groupsOrdersRepository.getOrderStatus(status, BATCH)
                .stream().filter(order -> order.getGroup() != null)
                .collect(Collectors.toList());
            ids = groupsOrders.stream().map(order -> order.getGroup().getId()).collect(Collectors.toList());

            groupsOrders.forEach(order -> {
                order.setGroup(null);
                groupsOrdersRepository.save(order);
            });
            groupRepository.deleteGroupsFromGroupsUsersByIds(ids);
            groupRepository.deleteGroupsFromGroupsByIds(ids);
        } while (!ids.isEmpty());
    }
}

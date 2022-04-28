package space.rebot.micro.schedulerservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.enums.GroupStatusEnum;
import space.rebot.micro.marketservice.model.Group;
import space.rebot.micro.marketservice.repository.GroupRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CleanGroupService {
    @Value("${scheduler.delete.batch}")
    private int BATCH;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void cleanGroup() {
        List<UUID> ids;
        do {
            ids = groupRepository.getGroupByStatus(GroupStatusEnum.CANCELED.getId(), BATCH)
                    .stream().map(Group::getId).collect(Collectors.toList());

            groupRepository.deleteGroupsFromGroupsUsersByIds(ids);
            groupRepository.deleteGroupsFromGroupsByIds(ids);
        } while (!ids.isEmpty());
    }
}

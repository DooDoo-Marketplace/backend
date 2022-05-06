package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.orderservice.enums.GroupStatusEnum;
import space.rebot.micro.orderservice.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class SetStatusGroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void setStatusToGroup(UUID groupId, GroupStatusEnum newStatus, GroupStatusEnum comparedStatus) {
        if (groupRepository.getGroup(groupId).getGroupStatus().getId() == comparedStatus.getId()) {
            groupRepository.updateGroupStatus(groupId, newStatus.getId());
        }
    }
}

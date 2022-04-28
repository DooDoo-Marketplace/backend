package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.GroupStatusEnum;
import space.rebot.micro.marketservice.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class SetCompletedStatusGroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void setCompletedStatusToGroup(UUID groupId) {
        if (groupRepository.getGroup(groupId).getGroupStatus().getId() == GroupStatusEnum.EXTRA.getId()) {
            groupRepository.updateGroupStatus(groupId, GroupStatusEnum.COMPLETED.getId());
        }
    }
}

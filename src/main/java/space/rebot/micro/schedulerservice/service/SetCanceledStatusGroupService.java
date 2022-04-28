package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.GroupStatusEnum;
import space.rebot.micro.marketservice.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class SetCanceledStatusGroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void setCanceledStatusToGroup(UUID groupId) {
        if (groupRepository.getGroup(groupId).getGroupStatus().getId() == GroupStatusEnum.ACTIVE.getId()) {
            groupRepository.updateGroupStatus(groupId, GroupStatusEnum.CANCELED.getId());
        }
    }
}

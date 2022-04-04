package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.model.CartStatus;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.schedulerservice.exceptions.CartCountNotCorrectException;

import java.util.List;

@Service
public class ScheduleService {
    private final int BATCH = 10000;

    @Autowired
    private CartRepository cartRepository;

    public void cleanCart() {
        while (true) {
            List<Long> ids = cartRepository.getCartByDeletedStatus(CartStatusEnum.DELETED.getId(), BATCH);
            if (ids.isEmpty()) {
                break;
            }
            try {
                int deleted = cartRepository.deleteCartByIdList(ids);
                if (ids.size() != deleted) {
                    throw new CartCountNotCorrectException(ids.size(), deleted);
                }
            } catch (CartCountNotCorrectException e) {
                System.out.println("Should be deleted " +
                        e.getCountCorrect() +
                        "cart. But there were deleted " +
                        e.getCountDeleted() + "cart.");
            }

        }
    }
}

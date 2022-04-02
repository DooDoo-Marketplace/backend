package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.repository.CartRepository;
import space.rebot.micro.schedulerservice.exceptions.CartCountNotCorrectException;

import java.util.List;

@Service
public class ScheduleService {
    private final int BATCH = 10000;

    @Autowired
    CartRepository cartRepository;

    public void cleanCart() {
        while (true) {
            List<Long> id = cartRepository.getCartByDeletedStatus(BATCH);
            if (id.isEmpty()) {
                break;
            }
            try {
                int deleted = cartRepository.deleteById(id);
                if (id.size() != deleted) {
                    throw new CartCountNotCorrectException(id.size(), (int) deleted);
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

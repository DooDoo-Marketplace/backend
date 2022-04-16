package space.rebot.micro.schedulerservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.repository.CartRepository;

import java.util.List;

@Service
public class CleanCartService {
    private final Logger logger = LogManager.getLogger("MyLogger");
    private final int BATCH = 10000;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void cleanCart() {
        while (true) {
            List<Long> ids = cartRepository.getCartByDeletedStatus(CartStatusEnum.DELETED.getId(), BATCH);
            if (ids.isEmpty()) {
                break;
            }
            int deleted = cartRepository.deleteCartByIdList(ids);
            if (ids.size() != deleted) {
                logger.error("Should be deleted " +
                        ids.size() +
                        "cart. But there were deleted " +
                        deleted + "cart.");
            }

        }
    }
}

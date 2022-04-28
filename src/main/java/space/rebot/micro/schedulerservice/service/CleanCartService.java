package space.rebot.micro.schedulerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.rebot.micro.marketservice.enums.CartStatusEnum;
import space.rebot.micro.marketservice.repository.CartRepository;

import java.util.List;

@Service
public class CleanCartService {
    @Value("${batch_of_delete}")
    private int BATCH;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void cleanCart() {
        List<Long> ids;
        do {
            ids = cartRepository.getCartByDeletedStatus(CartStatusEnum.DELETED.getId(), BATCH);
            cartRepository.deleteCartByIdList(ids);
        } while (!ids.isEmpty());
    }
}

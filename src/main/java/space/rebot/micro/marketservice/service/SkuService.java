package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;

import java.util.UUID;

@Service
public class SkuService {
    @Autowired
    SkuRepository skuRepository;

    public Sku findById(UUID id) {
        return skuRepository.getSkuById(id);
    }
}

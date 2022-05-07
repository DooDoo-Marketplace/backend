package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.dto.SkuDTO;
import space.rebot.micro.marketservice.mapper.SkuMapper;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuService {
    @Autowired
    SkuRepository skuRepository;

    @Autowired
    private SkuMapper skuMapper;

    public List<SkuDTO> getSku(String name, String region, Double lowPrice, Double upperPrice, Double rating, Integer limit, Integer page) {
        if (region == null) region = "%";
        int offset = (page - 1) * limit;
        List<Sku> skuList = skuRepository.getSkusByNameAndFilters("%" + name + "%", region, lowPrice, upperPrice, rating, limit, offset);
        if (skuList == null) {
            return Collections.emptyList();
        }
        return skuList.stream().map(sku -> skuMapper.mapToSkuDto(sku))
                .collect(Collectors.toList());
    }
}

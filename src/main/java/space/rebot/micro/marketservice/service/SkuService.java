package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.repository.SkuRepository;

import java.util.Collections;
import java.util.List;

@Service
public class SkuService {
    @Autowired
    SkuRepository skuRepository;

    public List<Sku> getSku(String name, String region, String lowPrice, String upperPrice, String rating, String page) throws NumberFormatException{
        name = "%" + name + "%";
        String reg = "%";
        if (region != null) reg = region;
        double lPrice = -1.0;
        if (lowPrice != null) lPrice = Double.parseDouble(lowPrice);
        double uPrice = 1000000.0;
        if (upperPrice != null) uPrice = Double.parseDouble(upperPrice);
        double rate = 0.0;
        if (rating != null) rate = Double.parseDouble(rating);
        int pageNumber = 1;
        if (page != null) pageNumber = Integer.parseInt(page);
        int limit = 20;
        int offset = (pageNumber - 1) * limit;
        List<Sku> skuList = skuRepository.getSkusByNameAndFilters(name, reg, lPrice, uPrice, rate, limit, offset);
        if (skuList == null) {
            return Collections.emptyList();
        }
        return skuList;
    }
}

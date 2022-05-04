package space.rebot.micro.orderservice.exception;

import java.util.List;

public class CartCheckException extends Exception {

    private List<Long> invalidRegionSkuId;

    private List<Long> invalidCountSkuId;

    public CartCheckException(List<Long> invalidRegionSkuId, List<Long> invalidCountSkuId) {
        this.invalidRegionSkuId = invalidRegionSkuId;
        this.invalidCountSkuId = invalidCountSkuId;
    }

    public List<Long> getInvalidRegionSkuId() {
        return invalidRegionSkuId;
    }

    public List<Long> getInvalidCountSkuId() {
        return invalidCountSkuId;
    }
}

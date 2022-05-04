package space.rebot.micro.orderservice.exception;

import java.util.List;

public class SkuGroupMatchException extends Exception {

    private List<Long> invalidGroupSkuId;

    public SkuGroupMatchException(List<Long> invalidGroupSkuId) {
        this.invalidGroupSkuId = invalidGroupSkuId;
    }

    public List<Long> getInvalidGroupSkuId() {
        return invalidGroupSkuId;
    }
}

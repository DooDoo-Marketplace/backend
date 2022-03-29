package space.rebot.micro.marketservice.exception;

public class SkuIsOverException extends Exception {
    private int cnt;

    public int getCnt() {
        return cnt;
    }

    public SkuIsOverException(int cnt) {
        this.cnt = cnt;
    }
}

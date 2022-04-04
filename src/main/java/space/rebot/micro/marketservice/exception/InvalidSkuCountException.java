package space.rebot.micro.marketservice.exception;

public class InvalidSkuCountException extends Exception {
    private int cnt;

    public InvalidSkuCountException(int cnt){
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }
}


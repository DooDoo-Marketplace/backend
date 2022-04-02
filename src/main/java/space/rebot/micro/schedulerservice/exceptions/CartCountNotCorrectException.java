package space.rebot.micro.schedulerservice.exceptions;

public class CartCountNotCorrectException extends Exception{
    private int countCorrect;
    private int countDeleted;

    public CartCountNotCorrectException(int countCorrect, int countDeleted){
        this.countCorrect = countCorrect;
        this.countDeleted = countDeleted;
    }

    public int getCountCorrect() {
        return countCorrect;
    }

    public int getCountDeleted() {
        return countDeleted;
    }
}

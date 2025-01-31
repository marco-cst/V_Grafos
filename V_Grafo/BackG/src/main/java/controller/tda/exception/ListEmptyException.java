package controller.tda.exception;

public class ListEmptyException extends Exception {
    public ListEmptyException(){

    }
    public ListEmptyException(String msg){
        super(msg);
    }
}

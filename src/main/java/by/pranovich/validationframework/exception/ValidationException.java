package by.pranovich.validationframework.exception;

public class ValidationException extends Exception{
    ValidationException(){
        super();
    }

    ValidationException(String message){
        super(message);
    }

    ValidationException(Throwable reason){
        super(reason);
    }

    ValidationException(String message,Throwable reason){
        super(message,reason);
    }
}

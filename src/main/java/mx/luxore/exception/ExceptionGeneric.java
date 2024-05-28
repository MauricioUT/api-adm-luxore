package mx.luxore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionGeneric extends RuntimeException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String sClass;
    public ExceptionGeneric(String msg, Throwable throwable) {
        super(msg, throwable);
        this.sClass = this.getClass().getName();
    }

    public ExceptionGeneric(String msg, Throwable throwable, String sClass) {
        super(msg, throwable);
        this.sClass = sClass;
    }

    public ExceptionGeneric(String msg, Throwable throwable, String sClass, HttpStatus status) {
        super(msg, throwable);
        this.status = status;
        this.sClass = sClass;
    }
}

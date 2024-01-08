package io.revnorth.exception;


import lombok.Data; import lombok.With; import lombok.AllArgsConstructor; import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data @With @NoArgsConstructor @AllArgsConstructor
public class HttpCallException extends Exception {
    private int code;

    public HttpCallException(String message) {
        super(message);
        this.code = 400;
    }

    public HttpCallException(int code, String message) {
        super(message);
        this.code = code;
    }

    public HttpCallException(String message, Exception cause) {
        super(message, cause);
    }

    public HttpCallException(int code, String message, Exception cause) {
        super(message, cause);
        this.code = code;
    }

}
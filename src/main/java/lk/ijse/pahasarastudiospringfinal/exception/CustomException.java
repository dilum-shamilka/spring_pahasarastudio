package lk.ijse.pahasarastudiospringfinal.exception;

public class CustomException extends RuntimeException {

    private final String errorCode;

    public CustomException(String message) {
        super(message);
        this.errorCode = "07";
    }

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
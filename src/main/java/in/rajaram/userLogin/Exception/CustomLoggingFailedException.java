package in.rajaram.userLogin.Exception;

public class CustomLoggingFailedException extends RuntimeException {
    public CustomLoggingFailedException(Throwable e) {
        super("Logging failed", e);
    }
}

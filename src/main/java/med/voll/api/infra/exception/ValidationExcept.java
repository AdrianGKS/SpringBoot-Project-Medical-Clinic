package med.voll.api.infra.exception;

public class ValidationExcept extends RuntimeException {
    public ValidationExcept(String msg) {
        super(msg);
    }
}

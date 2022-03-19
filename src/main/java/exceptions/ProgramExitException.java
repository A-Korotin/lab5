package exceptions;

public class ProgramExitException extends RuntimeException {
    public ProgramExitException(String msg) {
        super(msg);
    }
}

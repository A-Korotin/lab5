package exceptions;

public final class ProgramExitException extends RuntimeException {
    public ProgramExitException(String msg) {
        super(msg);
    }
}

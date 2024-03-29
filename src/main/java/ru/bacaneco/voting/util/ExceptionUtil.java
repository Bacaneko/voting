package ru.bacaneco.voting.util;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (true) {
            cause = result.getCause();
            if ((cause == null) || (result.equals(cause))) {
                break;
            }
            result = cause;
        }
        return result;
    }
}

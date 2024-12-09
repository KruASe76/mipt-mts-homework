package me.kruase.mipt.api.schemas.response;


public record ErrorResponse(String error, String detail) {
    public ErrorResponse(Throwable t) {
        this(t.getMessage(), getInitialCauseMessage(t));
    }

    private static String getInitialCauseMessage(Throwable t) {
        if (t.getCause() == null) {
            return null;
        }

        Throwable currentThrowable = t.getCause();
        while (currentThrowable.getCause() != null) {
            currentThrowable = currentThrowable.getCause();
        }

        return currentThrowable.getMessage();
    }
}

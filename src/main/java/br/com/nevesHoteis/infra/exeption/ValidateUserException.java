package br.com.nevesHoteis.infra.exeption;

public class ValidateUserException extends RuntimeException {
    private final String field;
    public ValidateUserException(String field, String message) {
        super(message);
        this.field=field;
    }

    public String getField() {
        return field;
    }
}

package br.com.api.projetoaudax.controller.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationErro extends StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }

    public ValidationErro() {

    }

    public ValidationErro(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }
}
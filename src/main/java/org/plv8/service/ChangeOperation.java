package org.plv8.service;

public enum ChangeOperation {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");

    private String description;

    ChangeOperation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}

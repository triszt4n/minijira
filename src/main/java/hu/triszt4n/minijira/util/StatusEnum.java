package hu.triszt4n.minijira.util;

public enum StatusEnum {
    TODO("Todo"),
    IN_PROGRESS("In progress"),
    UNDER_REVIEW("Under review"),
    APPROVED("Approved"),
    CLOSED("Closed");

    private final String name;

    public String getName() {
        return this.name;
    }

    StatusEnum(String name) {
        this.name = name;
    }
}

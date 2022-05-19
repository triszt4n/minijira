package hu.triszt4n.minijira.util;

public enum RoleEnum {
    MANAGER("ROLE_MANAGER"), DEVELOPER("ROLE_DEVELOPER");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

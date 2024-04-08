package erp.pcpartsbackend.models;

public enum Role {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    PROVIDER("Provider");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

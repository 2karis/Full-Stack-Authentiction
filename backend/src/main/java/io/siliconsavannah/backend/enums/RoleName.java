package io.siliconsavannah.backend.enums;

public enum RoleName {
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ROLE_OWNER("ROLE_OWNER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_MAINTAINER("ROLE_MAINTAINER"),
    ROLE_CUSTOMER("ROLE_CUSTOMER");

    private final String name;

    RoleName(String name) {
        this.name = name;
    }

}

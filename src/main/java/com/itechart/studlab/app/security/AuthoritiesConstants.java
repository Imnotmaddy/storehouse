package com.itechart.studlab.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String STOREHOUSE_ADMIN = "ROLE_STOREHOUSE_ADMIN";

    public static final String DISPATCHER = "ROLE_DISPATCHER";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String SUPERVISOR = "ROLE_SUPERVISOR";

    public static final String OWNER = "ROLE_OWNER";

    private AuthoritiesConstants() {
    }
}

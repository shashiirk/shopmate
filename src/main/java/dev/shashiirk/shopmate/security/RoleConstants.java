package dev.shashiirk.shopmate.security;

/**
 * Utility class containing constants for role names used in the application.
 * Role names are typically prefixed with "ROLE_" as per Spring Security conventions.
 */
public final class RoleConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    private RoleConstants() {
    }
}

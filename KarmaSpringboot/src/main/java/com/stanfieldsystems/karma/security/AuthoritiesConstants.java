package com.stanfieldsystems.karma.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String OWNER = "ROLE_OWNER";
    
    public static final String EDITOR = "ROLE_EDITOR";

    private AuthoritiesConstants() {
    }
}

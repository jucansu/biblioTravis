package com.bibliotek.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String ADMINISTRATIVO = "ROLE_ADMINISTRATIVO";
    
    public static final String BIBLIOTECARIO = "ROLE_BIBLIOTECARIO";

    private AuthoritiesConstants() {
    }
}

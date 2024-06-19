package org.hospital.management.patients.configs;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Converter to convert user roles from OAuth server to {@link GrantedAuthority}.
 */
public class JwtGrantedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String ROLES_REALM_KEY = "roles";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String REALM_KEY = "realm_access";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        var realmAccess = (Map<String, List<String>>) jwt.getClaim(REALM_KEY);

        return realmAccess.get(ROLES_REALM_KEY).stream()
            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
            .collect(Collectors.toList());
    }
}

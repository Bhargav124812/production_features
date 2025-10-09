package LearningSpring.Security.utils;

import LearningSpring.Security.entity.Enum.Permissions;
import LearningSpring.Security.entity.Enum.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Permission;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static LearningSpring.Security.entity.Enum.Permissions.*;
import static LearningSpring.Security.entity.Enum.Roles.*;

public class PermissionsMapping {
    private static final Map<Roles, Set<Permissions>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(POST_CREATE, USER_UPDATE, POST_UPDATE, USER_DELETE, USER_CREATE, POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Roles role) {
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}


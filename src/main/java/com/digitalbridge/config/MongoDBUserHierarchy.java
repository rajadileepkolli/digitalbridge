package com.digitalbridge.config;

import com.digitalbridge.security.MongoDBAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;

@EnableMongoAuditing(modifyOnCreate = false)
@Configuration(proxyBeanMethods = false)
public class MongoDBUserHierarchy {

    @Lazy
    @Autowired
    private MongoDBAuthenticationProvider authenticationProvider;

    /**
     * Spring Security Hierarchy Roles
     *
     * @return a {@link org.springframework.security.access.vote.RoleVoter} object.
     */
    @Bean
    public RoleVoter roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String roleHierarchyStringRepresentation = "ROLE_SUPERUSER > ROLE_ADMIN ROLE_ADMIN > ROLE_USER ROLE_USER > ROLE_GUEST";
        roleHierarchy.setHierarchy(roleHierarchyStringRepresentation);
        authenticationProvider
                .setAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(roleHierarchy));
        return roleHierarchy;
    }
}

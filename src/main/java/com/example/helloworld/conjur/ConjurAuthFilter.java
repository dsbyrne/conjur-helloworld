package com.example.helloworld.conjur;

import com.google.common.io.BaseEncoding;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.Authenticator;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import net.conjur.api.Conjur;
import net.conjur.api.Endpoints;
import net.conjur.api.authn.Token;
import net.conjur.api.authn.TokenAuthnProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(Priorities.AUTHENTICATION)
public class ConjurAuthFilter<P extends Principal> extends AuthFilter<Conjur, ConjurPrincipal> {

    final static Logger logger = LoggerFactory.getLogger(ConjurAuthFilter.class);
    Endpoints endpoints;

    private ConjurAuthFilter() {
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final String authHeader = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        logger.info(authHeader);
        if(authHeader == null || authHeader.isEmpty()) {
            return;
        }

        final Token token = Token.fromHeaderValue(authHeader);
        final TokenAuthnProvider authn = new TokenAuthnProvider(token);
        final Conjur conjur = new Conjur(authn, endpoints);
        final ConjurPrincipal principal = new ConjurPrincipal(conjur);

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return principal;
            }

            @Override
            public boolean isUserInRole(String role) {
                return authorizer.authorize(principal, requestContext.getMethod());
            }

            @Override
            public boolean isSecure() {
                return requestContext.getSecurityContext().isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return SecurityContext.BASIC_AUTH;
            }
        });
    }

    public void setApplianceUrl(String applianceUrl) {
        endpoints = Endpoints.getApplianceEndpoints(applianceUrl);
    }

    /**
     * Builder for {@link ConjurAuthFilter}.
     * <p>An {@link Authenticator} must be provided during the building process.</p>
     *
     * @param <P> the principal
     */
    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<Conjur, ConjurPrincipal, ConjurAuthFilter<P>> {

        @Override
        protected ConjurAuthFilter<P> newInstance() {
            return new ConjurAuthFilter<>();
        }
    }
}